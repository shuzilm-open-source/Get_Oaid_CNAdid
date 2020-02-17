package com.example.oaid_tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaid_tool.helpers.DevicesIDsHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DevicesIDsHelper.AppIdsUpdater {

  private String TAG = MainActivity.class.getSimpleName();
  private TextView mTvOaid;
  private TextView mTvAdid;
  private String mOAID;
  private DevicesIDsHelper mDevicesIDsHelper;
  private String mPath = "/sdcard/Android/ZHVzY2Lk";

  private Context mContext;

  private String[] PERMISSIONS_All_NEED = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
  };

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 1:
          mTvOaid.setText(mOAID);
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mContext = this.getApplicationContext();

    checkAllPermissions(this);

    mTvOaid = findViewById(R.id.tv_oaid);
    mTvAdid = findViewById(R.id.tv_adid);
  }

  public void checkAllPermissions(final Context context) {
    AndPermission.with(context)
        .permission(PERMISSIONS_All_NEED)
        .onGranted(new Action() {
          @Override
          public void onAction(List<String> list) {
          }
        })

        .onDenied(new Action() {
          @Override
          public void onAction(List<String> list) {
            // 判断用户是否点击了禁止后不再询问
            if (AndPermission.hasAlwaysDeniedPermission(context, PERMISSIONS_All_NEED)) {
              Toast.makeText(context, "部分功能被禁止，被禁止的功能将无法使用", Toast.LENGTH_SHORT).show();
              Log.e(TAG, "部分功能被禁止");
              showNormalDialog(MainActivity.this);
            }
          }
        })
        .start();
  }


  /**
   * 获取设备当前 OAID
   *
   * @param view
   */
  public void getOAID(View view) {
    Log.i("Wooo", "getOaid in.");
    mDevicesIDsHelper = new DevicesIDsHelper(this);
    mDevicesIDsHelper.getOAID(this);
  }

  /**
   * OAID 回调事件
   *
   * @param ids
   */
  @Override public void OnIdsAvalid(@NonNull String ids) {
    mOAID = ids;
    Message msg = Message.obtain();
    msg.what = 1;
    handler.sendMessage(msg);
    Log.e(TAG, "OnIdsAvalid====>" + ids);
  }

  /**
   * 获取 CN Adid
   *
   * @param view
   */
  public void getCNAdid(View view) {
    Log.i("Wooo", "getCNOaid in.");
    String id = ReadCNAdidN(mContext);
    Log.i("shuzilm", "id -> " + id);
    mTvAdid.setText(id);
  }

  private String ReadCNAdidN(Context ctx) {
    String adid = getCNAdID1(ctx);
    if (adid != null) {
      return adid;
    }
    adid = getCNAdID2(ctx);
    if (adid != null) {
      return adid;
    }
    adid = getCNAdID3(ctx);
    if (adid != null) {
      return adid;
    }
    return null;
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
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  private String getCNAdID1(Context ctx) {
    String result = null;
    result = Settings.System.getString(ctx.getContentResolver(), "ZHVzY2Lk");
    return result;
  }


  private void ReadCNAdid() {
    String content = "";
    File file = new File(mPath);
    if (file.isDirectory() || !file.isFile()) {
      Log.e(TAG, "The File doesn't not exist.");
      mPath = "/sdcard/Android/Data/System/local/.ZHVzY2Lk";
      ReadCNAdid();
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

    mTvAdid.setText(content.split("\n")[0]);
    Log.e(TAG, "本地文件读取 公共 mCNADID==" + content.split("\n")[0]);
  }



  private void showNormalDialog(final Context context) {
    final AlertDialog.Builder normalDialog =
        new AlertDialog.Builder(context);
    normalDialog.setTitle("去申请权限");
    normalDialog.setMessage("部分权限被你禁止了，可能误操作，可能会影响部分功能，是否去要去重新设置？");
    normalDialog.setPositiveButton("是",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            getAppDetailSettingIntent(context);
          }
        });
    normalDialog.setNegativeButton("否",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
          }
        });
    normalDialog.show();
  }

  static private void getAppDetailSettingIntent(Context context) {
    Intent localIntent = new Intent();
    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (Build.VERSION.SDK_INT >= 9) {
      localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
      localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
    }
    else if (Build.VERSION.SDK_INT <= 8) {
      localIntent.setAction(Intent.ACTION_VIEW);
      localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
      localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
    }
    context.startActivity(localIntent);
  }
}
