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

  public void getOAID(Context context) {

    String oaid = null;
    Log.e("getManufacturer","getManufacturer===> "+getManufacturer());

    if ("ASUS".equals(getManufacturer().toUpperCase())) {
      getIDFromNewThead(context);
    }
    else if ("HUAWEI".equals(getManufacturer().toUpperCase())) {
      getIDFromNewThead(context);
    }
    else if ("LENOVO".equals(getManufacturer().toUpperCase())) {
      new LenovoDeviceIDHelper(context).getIdRun(_listener);
    }
    else if ("MOTOLORA".equals(getManufacturer().toUpperCase())) {
      new LenovoDeviceIDHelper(context).getIdRun(_listener);
    }
    else if ("MEIZU".equals(getManufacturer().toUpperCase())) {
      new MeizuDeviceIDHelper(context).getMeizuID(_listener);
    }
    else if ("NUBIA".equals(getManufacturer().toUpperCase())) {
      oaid = new NubiaDeviceIDHelper(context).getNubiaID();
    }
    else if ("OPPO".equals(getManufacturer().toUpperCase())) {
      getIDFromNewThead(context);
    }
    else if ("SAMSUNG".equals(getManufacturer().toUpperCase())) {
      new SamsungDeviceIDHelper(context).getSumsungID(_listener);
    }
    else if ("VIVO".equals(getManufacturer().toUpperCase())) {
      oaid = new VivoDeviceIDHelper(context).getOaid();
    }
    else if ("XIAOMI".equals(getManufacturer().toUpperCase())) {
      oaid = new XiaomiDeviceIDHelper(context).getOAID();
    }
    else if ("BLACKSHARK".equals(getManufacturer().toUpperCase())) {
      oaid = new XiaomiDeviceIDHelper(context).getOAID();
    }
    else if ("ONEPLUS".equals(getManufacturer().toUpperCase())) {
      getIDFromNewThead(context);
    }
    else if ("ZTE".equals(getManufacturer().toUpperCase())) {
      getIDFromNewThead(context);
    }
    else if ("FERRMEOS".equals(getManufacturer().toUpperCase()) || isFreeMeOS()) {
      getIDFromNewThead(context);
    }
    else if ("SSUI".equals(getManufacturer().toUpperCase()) || isSSUIOS()) {
      getIDFromNewThead(context);
    }


    if (_listener != null) {
      _listener.OnIdsAvalid(oaid);
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
  private void getIDFromNewThead(final Context context) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        if ("ASUS".equals(getManufacturer().toUpperCase())) {
          new ASUSDeviceIDHelper(context).getID(_listener);
        }
        else if ("HUAWEI".equals(getManufacturer().toUpperCase())) {
          new HWDeviceIDHelper(context).getHWID(_listener);
        }
        else if ("OPPO".equals(getManufacturer().toUpperCase())) {
          new OppoDeviceIDHelper(context).getID(_listener);
        }
        else if ("ONEPLUS".equals(getManufacturer().toUpperCase())) {
          new OnePlusDeviceIDHelper(context).getID(_listener);
        }
        else if ("ZTE".equals(getManufacturer().toUpperCase())) {
          new ZTEDeviceIDHelper(context).getID(_listener);
        }
        else if ("FERRMEOS".equals(getManufacturer().toUpperCase()) || isFreeMeOS()) {
          new ZTEDeviceIDHelper(context).getID(_listener);
        }
        else if ("SSUI".equals(getManufacturer().toUpperCase()) || isSSUIOS()) {
          new ZTEDeviceIDHelper(context).getID(_listener);
        }
      }
    }).start();
  }


  public interface AppIdsUpdater {
    void OnIdsAvalid(@NonNull String ids);
  }

}
