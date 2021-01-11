package com.example.oaid_tool.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/****************************
 * on 2019/10/29
 ****************************
 */
public class MeizuDeviceIDHelper {

    private Context mContext;

    public MeizuDeviceIDHelper(Context ctx) {
        mContext = ctx;
    }


    public boolean isMeizuSupport() {
        try {
            PackageManager pm = mContext.getPackageManager();
            if (pm != null) {
                ProviderInfo pi = pm.resolveContentProvider("com.meizu.flyme.openidsdk", 0);        // "com.meizu.flyme.openidsdk"
                if (pi != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getMeizuID(DevicesIDsHelper.AppIdsUpdater _listener) {
        try {
            mContext.getPackageManager().getPackageInfo("com.meizu.flyme.openidsdk", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("content://com.meizu.flyme.openidsdk/");
        Cursor cursor;
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            cursor = contentResolver.query(uri, null, null, new String[]{"oaid"}, null);
            String oaid = getOaid(cursor);
            boolean support = isMeizuSupport();


            if (_listener != null) {
                _listener.OnIdsAvalid(oaid, support);
            }
            cursor.close();
        } catch (Throwable t) {
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
        if (cursor == null || cursor.isClosed()) {
            Log.d("MEIZU :", "oaid null");
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
