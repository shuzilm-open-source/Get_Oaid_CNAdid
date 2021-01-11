package com.example.oaid_tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaid_tool.helpers.DevicesIDsHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DevicesIDsHelper.AppIdsUpdater {

    private String TAG = "OAID_TOOL";
    private TextView mTvOaid;
    private Button btnOaid;

    private String mOAID;
    private boolean isSupported = false;
    private DevicesIDsHelper mDevicesIDsHelper;
    private MyHandler myhandler;

    private Context mContext;

    private String[] PERMISSIONS_All_NEED = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplicationContext();

        checkAllPermissions(this);
        myhandler = new MyHandler();
        mTvOaid = findViewById(R.id.tv_oaid);
        btnOaid = findViewById(R.id.btn_oaid);
        btnOaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOAID(view);
            }
        });

    }

    @Override
    protected void onDestroy() {
        myhandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    private class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            StringBuilder builder = new StringBuilder();
            builder.append("support: ").append(isSupported?"true":"false").append("\n");
            builder.append("OAID: ").append(mOAID).append("\n");

            switch (msg.what) {
                case 1:
                    mTvOaid.setText(builder);
                    break;
                default:
                    break;
            }
        }
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
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }


    /**
     * 获取设备当前 OAID
     *
     * @param view
     */
    public void getOAID(View view) {
        Log.d(TAG, "GetOaid in.");
        mDevicesIDsHelper = new DevicesIDsHelper(this);
        mDevicesIDsHelper.getOAID(this);
    }

    /**
     * OAID 回调事件
     *
     * @param ids
     */
    public void OnIdsAvalid(@NonNull String ids, boolean support) {
        if (ids == null || support == false) {
            mOAID = "null";
            isSupported = support;
        } else {
            isSupported = support;
            mOAID = ids;
        }

        Message msg = Message.obtain();
        msg.what = 1;
        myhandler.sendMessage(msg);

        Log.d(TAG, "OnIdsAvalid====> " + ids);

    }


}



