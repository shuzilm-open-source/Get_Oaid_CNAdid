package com.example.oaid_tool.helpers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/****************************
 * Created by lchenglan
 * on 2019/11/13
 ****************************
 */
public class CNAdidHelper {

  private String TAG = "CNAdidHelper";
  private String mPath = "/sdcard/Android/ZHVzY2Lk";

  private CNAdidHelper() {
  }

  public static CNAdidHelper getInstance() {
    return Inner.instance;
  }

  private static class Inner {
    private static final CNAdidHelper instance = new CNAdidHelper();
  }

  public String readCNAdid() {
    String content = "";
    File file = new File(mPath);
    if (file.isDirectory() || !file.isFile()) {
      Log.e(TAG, "The File doesn't not exist.");
      mPath = "/sdcard/Android/Data/System/local/.ZHVzY2Lk";
      readCNAdid();
    }
    else {
      try {
        InputStream instream = new FileInputStream(file);
        if (instream != null) {
          InputStreamReader inputreader = new InputStreamReader(instream);
          BufferedReader buffreader = new BufferedReader(inputreader);
          String line;
          //分行读取
          while ((line = buffreader.readLine()) != null) {
            content += line + "\n";
          }
          instream.close();
        }
      }
      catch (java.io.FileNotFoundException e) {
        Log.e(TAG, "The File doesn't not exist.");
      }
      catch (IOException e) {
        Log.e(TAG, e.getMessage());
      }
    }
    Log.e(TAG, "本地文件读取 公共 mCNADID==" + content.split("\n")[0]);
    return content.split("\n")[0];
  }

}
