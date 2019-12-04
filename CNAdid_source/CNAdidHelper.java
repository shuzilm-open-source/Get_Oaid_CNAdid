package com.example.oaid_tool.helpers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CNAdidHelper {

  private String TAG = "CNAdidHelper";

  private CNAdidHelper() {
  }

  public static CNAdidHelper getInstance() {
    return Inner.instance;
  }

  private static class Inner {
    private static final CNAdidHelper instance = new CNAdidHelper();
  }

  public String readCNAdid(Context ctx) {
    String adid = getCNAdID1(ctx);
    if (adid != null) {
      return adid;
    }
    adid = getCNAdID2(ctx);
    if (adid != null) {
      return adid;
    }
    adid = getCNAdID3();
    if (adid != null) {
      return adid;
    }
    return null;
  }
  
  private String getCNAdID1(Context ctx) {
    String result = null;
    result = Settings.System.getString(ctx.getContentResolver(), "ZHVzY2Lk");
    return result;
  }
  private String getCNAdID2(Context ctx) {
    String result = null;
    String pkgName = ctx.getPackageName();
    SharedPreferences sp = ctx.getSharedPreferences(pkgName + "_dna", 0);
    result = sp.getString("ZHVzY2Lk", "NA");
    if (result.equals("NA")) {
      return null;
    }
    return result;
  }
  private String getCNAdID3(Context ctx) {
    String result = null;
    String path = "/sdcard/Android/ZHVzY2Lk";
    try {
      File file = new File(path);
      if (file.isDirectory() || !file.isFile()) {
        Log.e(TAG, "The File doesn't not exist.");
        return null;
      }
      InputStream instream = new FileInputStream(file);
      InputStreamReader inputreader = new InputStreamReader(instream);
      BufferedReader buffreader = new BufferedReader(inputreader);
      String line = buffreader.readLine();
      if (line != null) {
        result = line;
      }
      instream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

}
