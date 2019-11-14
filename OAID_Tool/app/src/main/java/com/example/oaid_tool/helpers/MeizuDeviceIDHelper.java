package com.example.oaid_tool.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/****************************
 * Created by lchenglan
 * on 2019/10/29
 ****************************
 */
public class MeizuDeviceIDHelper {

  private Context mContext;

  public MeizuDeviceIDHelper(Context ctx) {
    mContext = ctx;
  }


  public void getMeizuID(DevicesIDsHelper.AppIdsUpdater _listener) {
    try {
      mContext.getPackageManager().getPackageInfo("com.meizu.flyme.openidsdk", 0);
    }
    catch (Exception e) {
      Log.i("Wooo", "intentForID getMEIZID service not found;");
      e.printStackTrace();
    }
    Uri uri = Uri.parse("content://com.meizu.flyme.openidsdk/");

    Cursor cursor;
    ContentResolver contentResolver = mContext.getContentResolver();
    try {
      cursor = contentResolver.query(uri, null, null, new String[]{"oaid"}, null);
      String oaid = getOaid(cursor);

      if (_listener != null) {
        _listener.OnIdsAvalid(oaid);
      }
      cursor.close();
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }

  /**
   * 获取 OAID
   *
   * @param cursor
   * @return
   */
  private String getOaid(Cursor cursor) {
    String oaid = null;
    if (cursor == null) {
      return null;
    }
    if (cursor.isClosed()) {
      return null;
    }
    cursor.moveToFirst();
    int valueIdx = cursor.getColumnIndex("value");
    if (valueIdx > 0) {
      oaid = cursor.getString(valueIdx);
    }
    valueIdx = cursor.getColumnIndex("code");
    if (valueIdx > 0) {
      int codeID = cursor.getInt(valueIdx);
    }
    valueIdx = cursor.getColumnIndex("expired");
    if (valueIdx > 0) {
      long timeC = cursor.getLong(valueIdx);
    }
    return oaid;
  }
}
