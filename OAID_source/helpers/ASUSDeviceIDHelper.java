package com.example.oaid_tool.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.oaid_tool.interfaces.ASUSIDInterface;

import java.util.concurrent.LinkedBlockingQueue;

/****************************
 * on 2019/10/29
 * 华硕手机获取 OAID
 ****************************
 */
public class ASUSDeviceIDHelper {

    private Context mContext;
    public final LinkedBlockingQueue<IBinder> linkedBlockingQueue = new LinkedBlockingQueue(1);

    public ASUSDeviceIDHelper(Context ctx) {
        mContext = ctx;
    }

    /**
     * 获取 OAID 并回调
     *
     * @param _listener
     */
    public void getID(DevicesIDsHelper.AppIdsUpdater _listener) {
        try {
            mContext.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();

        intent.setAction("com.asus.msa.action.ACCESS_DID");
        ComponentName componentName = new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService");
        intent.setComponent(componentName);


        boolean isBin = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (isBin) {
            try {
                IBinder iBinder = linkedBlockingQueue.take();
                ASUSIDInterface.ASUSID asusID = new ASUSIDInterface.ASUSID(iBinder);
                String asusOAID = asusID.getID();
                boolean support = asusID.isSupport();

                if (_listener != null) {
                    _listener.OnIdsAvalid(asusOAID, support);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                linkedBlockingQueue.put(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}
