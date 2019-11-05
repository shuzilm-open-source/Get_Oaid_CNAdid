package com.example.oaid_tool.helpers;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/****************************
 * Created by lchenglan
 * on 2019/10/29
 * 努比亚 OAID
 ****************************
 */
public class NubiaDeviceIDHelper {

  private Context mConetxt;

  public NubiaDeviceIDHelper(Context ctx) {
    mConetxt = ctx;
  }

  public String getNubiaID() {
    String oaid = null;
    Bundle bundle;

    Uri uri = Uri.parse("content://cn.nubia.identity/identity");
    try {
      if (Build.VERSION.SDK_INT > 17) {
        ContentProviderClient contentProviderClient = mConetxt.getContentResolver().acquireContentProviderClient(uri);
        bundle = contentProviderClient.call("getOAID", null, null);
        if (contentProviderClient != null) {
          if (Build.VERSION.SDK_INT >= 24) {
            contentProviderClient.close();
          }
          else {
            contentProviderClient.release();
          }
        }
      }
      else {
        bundle = mConetxt.getContentResolver().call(uri, "getOAID", null, null);
      }
      int code = bundle.getInt("code", -1);
      if (code == 0) {
        oaid = bundle.getString("id");
        return oaid;
      }
      String faledMsg = bundle.getString("message");
      return oaid;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return oaid;
  }
}
