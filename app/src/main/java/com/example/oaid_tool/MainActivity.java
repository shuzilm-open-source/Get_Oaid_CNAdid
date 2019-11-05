package com.example.oaid_tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.oaid_tool.helpers.DevicesIDsHelper;

public class MainActivity extends AppCompatActivity implements DevicesIDsHelper.AppIdsUpdater {

  private TextView mTextView;
  private String mOAID;
  private DevicesIDsHelper mDevicesIDsHelper;

  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 1:
          mTextView.setText(mOAID);
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTextView = findViewById(R.id.tv);
  }

  /**
   * 获取设备当前 OAID
   *
   * @param view
   */
  public void getOAID(View view) {
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
    Log.e("MainActivity", "OnIdsAvalid====>" + ids);
  }

}
