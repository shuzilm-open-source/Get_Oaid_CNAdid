package com.example.oaid_tool.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;


/****************************
 * * on 2019/10/29
 ****************************
 */
public interface SamsungIDInterface extends IInterface {

  String getID();

  class Proxy implements SamsungIDInterface {

    private IBinder mIBinder;

    public Proxy(IBinder iBinder) {
      super();
      mIBinder = iBinder;
    }

    @Override
    public IBinder asBinder() {
      return mIBinder;
    }

    @Override
    public String getID() {
      String result = null;
      Parcel v1 = Parcel.obtain();
      Parcel v2 = Parcel.obtain();
      try {
        v1.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
        mIBinder.transact(1, v1, v2, 0);
        v2.readException();
        result = v2.readString();
      }
      catch (Throwable t) {
        v2.recycle();
        v1.recycle();
        t.printStackTrace();
      }
      v2.recycle();
      v1.recycle();

      return result;
    }
  }

  public abstract class Stub extends Binder implements SamsungIDInterface {

    public Stub() {
      super();
      this.attachInterface(((IInterface) this), "com.samsung.android.deviceidservice.IDeviceIdService");
    }

    public SamsungIDInterface a(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface iInterface = iBinder.queryLocalInterface("com.samsung.android.deviceidservice.IDeviceIdService");
      if (iInterface == null) {
        return null;
      }
      Proxy proxy = new Proxy(iBinder);
      return (SamsungIDInterface) proxy;
    }
  }
}
