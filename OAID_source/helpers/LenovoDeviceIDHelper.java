package com.example.oaid_tool.helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.oaid_tool.interfaces.LenovoIDInterface;

/****************************
 * on 2019/10/29
 * 获取联想 OAID
 ****************************
 */
public class LenovoDeviceIDHelper {

    private Context mContext;
    LenovoIDInterface lenovoIDInterface;

    public LenovoDeviceIDHelper(Context ctx) {
        mContext = ctx;
    }

    public void getIdRun(DevicesIDsHelper.AppIdsUpdater _listener) {
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        boolean seu = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (seu) {
            if (lenovoIDInterface != null) {
                String oaid = lenovoIDInterface.a();
                boolean support = lenovoIDInterface.c();

                if (_listener != null) {
                    _listener.OnIdsAvalid(oaid, support);
                }
            }
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            lenovoIDInterface = new LenovoIDInterface.len_up.len_down(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
