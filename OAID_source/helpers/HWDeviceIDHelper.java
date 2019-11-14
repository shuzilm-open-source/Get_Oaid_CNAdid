package com.example.oaid_tool.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.oaid_tool.interfaces.HWIDInterface;

import java.util.concurrent.LinkedBlockingQueue;

/****************************
 * Created by lchenglan
 * on 2019/10/29
 * 获取华为 OAID
 ****************************
 */
public class HWDeviceIDHelper {

  private Context mContext;
  public final LinkedBlockingQueue<IBinder> linkedBlockingQueue = new LinkedBlockingQueue(1);

  public HWDeviceIDHelper(Context ctx) {
    mContext = ctx;
  }

  public void getHWID(DevicesIDsHelper.AppIdsUpdater _listener) {
    Log.i("Wooo", "getHWID IN ");
    try {
      mContext.getPackageManager().getPackageInfo("com.huawei.hwid", 0);
    }
    catch (Exception e) {
      Log.i("Wooo", "getHWID hw service not found;");
      e.printStackTrace();
    }

    Intent bindIntent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
    bindIntent.setPackage("com.huawei.hwid");
    boolean isBin = mContext.bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    Log.i("Wooo", "intentForID bindService. isBin -> " + isBin);
    if (isBin) {
      try {
        IBinder iBinder = linkedBlockingQueue.take();
        Log.i("Wooo", "getHWID bindService. binder -> " + iBinder);
        HWIDInterface.HWID hwID = new HWIDInterface.HWID(iBinder);
        String ids = hwID.getIDs();
        boolean boos = hwID.getBoos();
        Log.i("Wooo", "getHWID OUT ids -> " + ids + " , boos -> " + boos);

        if (_listener != null) {
          _listener.OnIdsAvalid(ids);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      finally {
        mContext.unbindService(serviceConnection);
      }
    }
    Log.i("Wooo", "getHWID OUT isBin -> " + isBin);
  }


  ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      try {
        linkedBlockingQueue.put(service);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

}
