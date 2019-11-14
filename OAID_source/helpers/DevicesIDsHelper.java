package com.example.oaid_tool.helpers;

import android.content.Context;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;

/****************************
 * Created by lchenglan
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

    if ("ASUS".equals(getManufacturer())) {
      getIDFromNewThead(context);
    }
    else if ("HUAWEI".equals(getManufacturer())) {
      getIDFromNewThead(context);
    }
    else if ("LENOVO".equals(getManufacturer())) {
      new LenovoDeviceIDHelper(context).getIdRun(_listener);
    }
    else if ("MEIZU".equals(getManufacturer())) {
      new MeizuDeviceIDHelper(context).getMeizuID(_listener);
    }
    else if ("NUBIA".equals(getManufacturer())) {
      oaid = new NubiaDeviceIDHelper(context).getNubiaID();
    }
    else if ("OPPO".equals(getManufacturer())) {
      getIDFromNewThead(context);
    }
    else if ("SAMSUNG".equals(getManufacturer())) {
      new SamsungDeviceIDHelper(context).getSumsungID(_listener);
    }
    else if ("VIVO".equals(getManufacturer())) {
      oaid = new VivoDeviceIDHelper(context).getOaid();
    }
    else if ("XIAOMI".equals(getManufacturer())) {
      oaid = new XiaomiDeviceIDHelper(context).getOAID();
    }

    if (_listener != null) {
      _listener.OnIdsAvalid(oaid);
    }
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
        if ("ASUS".equals(getManufacturer())) {
          new ASUSDeviceIDHelper(context).getID(_listener);
        }
        else if ("HUAWEI".equals(getManufacturer())) {
          new HWDeviceIDHelper(context).getHWID(_listener);
        }
        else if ("OPPO".equals(getManufacturer())) {
          new OppoDeviceIDHelper(context).getID(_listener);
        }
      }
    }).start();
  }


  public interface AppIdsUpdater {
    void OnIdsAvalid(@NonNull String ids);
  }

}
