package com.example.oaid_tool.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;


/****************************
 * on 2019/10/29
 ****************************
 */
public interface HWIDInterface extends IInterface {

    String getIDs();

    boolean getBoos();

    public final class HWID implements HWIDInterface {
        private IBinder iBinder;
        private Context context;

        public HWID(IBinder ib, Context ct) {
            iBinder = ib;
            context = ct;
        }

        @Override
        public IBinder asBinder() {
            return iBinder;
        }

        @Override
        public String getIDs() {
            String result = null;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();

            try {
                obtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                iBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                result = obtain2.readString();
            } catch (Throwable e) {
                obtain.recycle();
                obtain2.recycle();
                e.printStackTrace();
            }

            obtain.recycle();
            obtain2.recycle();
            return result;
        }

        @Override
        public boolean getBoos() {
            boolean result = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();

            try {
                obtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                iBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                int read = obtain2.readInt();
                if (read == 0) {
                    result = false;
                }
            } catch (Throwable e) {
                obtain.recycle();
                obtain2.recycle();
            }
            obtain.recycle();
            obtain2.recycle();
            return result;
        }

        public String  getPPS_oaid() {
            String result = null;

            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    result = Settings.Global.getString(context.getContentResolver(), "pps_oaid");
                    String z = Settings.Global.getString(context.getContentResolver(), "pps_track_limit");

                    if (!TextUtils.isEmpty(result) && !TextUtils.isEmpty(z)) {
                        result = "get oaid failed";
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                    result = "get oaid failed";

                }
            }

            return result;
        }



    }
}


