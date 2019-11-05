package com.example.oaid_tool.interfaces;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;


/****************************
 * Created by lchenglan
 * on 2019/10/29
 ****************************
 */
public interface HWIDInterface extends IInterface {

  String getIDs();

  boolean getBoos();

  public final class HWID implements HWIDInterface {
    private IBinder iBinder;

    public HWID(IBinder ib) {
      iBinder = ib;
    }

    @Override
    public IBinder asBinder() {
      return iBinder;
    }

    @Override
    public String getIDs() {
      Log.i("Wooo", "HWIDInterface getIDs IN ");
      String result = null;
      Parcel v1 = Parcel.obtain();
      Parcel v2 = Parcel.obtain();

      try {
        v1.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
        iBinder.transact(1, v1, v2, 0);
        v2.readException();
        result = v2.readString();
        Log.i("Wooo", "HWDeviceIDHelper getIDs IN res -> " + result);
      }
      catch (Throwable e) {
        v1.recycle();
        v2.recycle();
        e.printStackTrace();
      }

      v1.recycle();
      v2.recycle();
      Log.i("Wooo", "HWDeviceIDHelper getIDs IN res2 -> " + result);
      return result;
    }

    @Override
    public boolean getBoos() {
      Log.i("Wooo", "HWDeviceIDHelper getBoos IN ");
      boolean result = false;
      Parcel v1 = Parcel.obtain();
      Parcel v2 = Parcel.obtain();
      try {
        v1.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
        iBinder.transact(1, v1, v2, 0);
        v2.readException();
        int read = v2.readInt();
        Log.i("Wooo", "HWDeviceIDHelper getBoos IN read -> " + read);
        if (read == 0) {
          result = true;
        }
      }
      catch (Throwable e) {
        v1.recycle();
        v2.recycle();
      }
      v1.recycle();
      v2.recycle();
      Log.i("Wooo", "HWDeviceIDHelper getBoos IN read2 -> " + result);
      return result;
    }
  }
}
