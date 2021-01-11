package com.example.oaid_tool.helpers;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import java.lang.reflect.Method;

import androidx.annotation.NonNull;

/****************************
 * on 2019/10/28
 ****************************
 */
public class DevicesIDsHelper {

    private AppIdsUpdater _listener;

    public DevicesIDsHelper(AppIdsUpdater callback) {
        _listener = callback;
    }

    private String getBrand() {
        return android.os.Build.BRAND.toUpperCase();
    }

    private String getManufacturer() {
        return Build.MANUFACTURER.toUpperCase();
    }

    public void getOAID (Context mcontext) {

        String oaid = null;
        boolean isSupport = false;

        Log.d("OAID_TOOL", "getManufacturer ===> " + getManufacturer());

        String manufacturer =  getManufacturer().toUpperCase();

        if ("ASUS".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);
        } else if ("HUAWEI".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("LENOVO".equals(manufacturer)) {
            new LenovoDeviceIDHelper(mcontext).getIdRun(_listener);

        } else if ("MOTOLORA".equals(manufacturer)) {
            new LenovoDeviceIDHelper(mcontext).getIdRun(_listener);

        } else if ("MEIZU".equals(manufacturer)) {
            new MeizuDeviceIDHelper(mcontext).getMeizuID(_listener);

        } else if ("NUBIA".equals(manufacturer)) {
            oaid = new NubiaDeviceIDHelper(mcontext).getNubiaID();

        } else if ("OPPO".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("SAMSUNG".equals(manufacturer)) {
            new SamsungDeviceIDHelper(mcontext).getSumsungID(_listener);

        } else if ("VIVO".equals(manufacturer)) {
            oaid = new VivoDeviceIDHelper(mcontext).getOaid();

        } else if ("XIAOMI".equals(manufacturer)) {

            oaid = new XiaomiDeviceIDHelper(mcontext).getOAID();

        } else if ("BLACKSHARK".equals(manufacturer)) {
            oaid = new XiaomiDeviceIDHelper(mcontext).getOAID();

        } else if ("ONEPLUS".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("ZTE".equals(manufacturer)) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("FERRMEOS".equals(manufacturer) || isFreeMeOS()) {
            getIDFromNewThead(mcontext, manufacturer);

        } else if ("SSUI".equals(manufacturer) || isSSUIOS()) {
            getIDFromNewThead(mcontext, manufacturer);
        }

        if (oaid != null) {
            isSupport = true;
        }

        if (_listener != null) {
            _listener.OnIdsAvalid(oaid, isSupport);
        }
    }

    private String getProperty(String property) {
        String res = null;
        if (property == null) {
            return null;
        }
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", new Class[]{String.class, String.class});
            res = (String) method.invoke(clazz, new Object[]{property, "unknown"});
        } catch (Exception e) {
            // ignore
        }
        return res;
    }


    public boolean isFreeMeOS() {
        String pro = getProperty("ro.build.freeme.label");      // "ro.build.freeme.label"
        if ((!TextUtils.isEmpty(pro)) && pro.equalsIgnoreCase("FREEMEOS")) {      // "FreemeOS"  FREEMEOS
            return true;
        }
        return false;
    }

    public boolean isSSUIOS() {
        String pro = getProperty("ro.ssui.product");    // "ro.ssui.product"
        if ((!TextUtils.isEmpty(pro)) && (!pro.equalsIgnoreCase("unknown"))) {
            return true;
        }
        return false;
    }


    /**
     * 启动子线程获取
     *
     * @param context
     */
    private void getIDFromNewThead(final Context contextm, final String manufacturerm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ("ASUS".equals(manufacturerm)) {
                    new ASUSDeviceIDHelper(contextm).getID(_listener);

                } else if ("HUAWEI".equals(manufacturerm)) {
                    new HWDeviceIDHelper(contextm).getHWID(_listener);

                } else if ("OPPO".equals(manufacturerm)) {
                    new OppoDeviceIDHelper(contextm).getID(_listener);

                } else if ("ONEPLUS".equals(manufacturerm)) {
                    new OnePlusDeviceIDHelper(contextm).getID(_listener);

                } else if ("ZTE".equals(manufacturerm)) {
                    new ZTEDeviceIDHelper(contextm).getID(_listener);

                } else if ("FERRMEOS".equals(manufacturerm) || isFreeMeOS()) {
                    new ZTEDeviceIDHelper(contextm).getID(_listener);

                } else if ("SSUI".equals(manufacturerm) || isSSUIOS()) {
                    new ZTEDeviceIDHelper(contextm).getID(_listener);
                }
            }
        }).start();
    }


    public interface AppIdsUpdater {
        void OnIdsAvalid(@NonNull String ids, boolean support);
    }

}
