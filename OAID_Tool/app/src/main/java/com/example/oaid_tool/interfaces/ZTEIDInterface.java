package com.example.oaid_tool.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;


public interface ZTEIDInterface extends IInterface {
  boolean c();
  String getOAID();
  boolean isSupported();
  void shutDown();

  public static abstract class up extends Binder implements ZTEIDInterface {
    public static class down implements ZTEIDInterface {
      private IBinder binder;

      public down(IBinder b) {
        binder = b;
      }

      @Override
      public IBinder asBinder() {
        return binder;
      }

      @Override
      public boolean c() {
        boolean v0 = false;
        Parcel v1 = Parcel.obtain();
        Parcel v2 = Parcel.obtain();
        try {
          v1.writeInterfaceToken("com.bun.lib.MsaIdInterface");
          binder.transact(2, v1, v2, 0);
          v2.readException();
          if(v2.readInt() == 0) {
            v2.recycle();
            v1.recycle();
            v0 = true;
          }
        }
        catch(Throwable v0_1) {
          v2.recycle();
          v1.recycle();
          v0_1.printStackTrace();
        }
        return v0;
      }

      @Override
      public String getOAID() {
        String v0_1 = null;
        Parcel v1 = Parcel.obtain();
        Parcel v2 = Parcel.obtain();
        try {
          v1.writeInterfaceToken("com.bun.lib.MsaIdInterface");
          binder.transact(3, v1, v2, 0);
          v2.readException();
          v0_1 = v2.readString();
        }
        catch(Throwable v0) {
          v2.recycle();
          v1.recycle();
        }

        v2.recycle();
        v1.recycle();
        return v0_1;
      }

      @Override
      public boolean isSupported() {
        return false;
      }

      @Override
      public void shutDown() {
        Parcel v1 = Parcel.obtain();
        Parcel v2 = Parcel.obtain();
        try {
          v1.writeInterfaceToken("com.bun.lib.MsaIdInterface");
          binder.transact(6, v1, v2, 0);
          v2.readException();
        }
        catch(Throwable v0) {
          v2.recycle();
          v1.recycle();
        }
        v2.recycle();
        v1.recycle();
      }
    }
  }
}
