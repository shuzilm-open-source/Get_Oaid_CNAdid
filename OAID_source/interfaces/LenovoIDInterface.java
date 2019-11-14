package com.example.oaid_tool.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/****************************
 * Created by lchenglan
 * on 2019/10/29
 * 获取联想手机 OAID
 ****************************
 */
public interface LenovoIDInterface extends IInterface {

  String a();

  String a(String arg1);

  String b();

  String b(String arg1);

  boolean c();

  public abstract class len_up extends Binder implements LenovoIDInterface {

    public static class len_down implements LenovoIDInterface {

      private IBinder iBinder;

      public len_down(IBinder ib) {
        iBinder = ib;
      }

      @Override
      public IBinder asBinder() {
        return null;
      }

      @Override
      public String a() {
        String readString = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
          iBinder.transact(1, obtain, obtain2, 0);
          obtain2.readException();
          readString = obtain2.readString();
          return readString;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          obtain2.recycle();
          obtain.recycle();
        }
        return readString;
      }

      @Override
      public String a(String arg1) {
        String readString = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
          iBinder.transact(4, obtain, obtain2, 0);
          obtain2.readException();
          readString = obtain2.readString();
          return readString;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          obtain2.recycle();
          obtain.recycle();
        }
        return readString;
      }

      @Override
      public String b() {
        String readString = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
          iBinder.transact(2, obtain, obtain2, 0);
          obtain2.readException();
          readString = obtain2.readString();
          return readString;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          obtain2.recycle();
          obtain.recycle();
        }
        return readString;
      }

      @Override
      public String b(String arg1) {
        String readString = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
          iBinder.transact(5, obtain, obtain2, 0);
          obtain2.readException();
          readString = obtain2.readString();
          return readString;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          obtain2.recycle();
          obtain.recycle();
        }
        return readString;
      }

      @Override
      public boolean c() {
        boolean z = false;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
          iBinder.transact(3, obtain, obtain2, 0);
          obtain2.readException();
          if (obtain2.readInt() != 0) {
            z = true;
          }
          obtain2.recycle();
          obtain.recycle();
        }
        catch (Throwable th) {
          obtain2.recycle();
          obtain.recycle();
        }
        return z;
      }
    }


    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable
        Parcel reply, int flags) throws RemoteException {
      String str = "com.zui.deviceidservice.IDeviceidInterface";
      switch (code) {
        case 1:
          data.enforceInterface(str);
          str = a();
          reply.writeNoException();
          reply.writeString(str);
          return true;
        case 2:
          data.enforceInterface(str);
          str = b();
          reply.writeNoException();
          reply.writeString(str);
          return true;
        case 3:
          data.enforceInterface(str);
          boolean c = c();
          reply.writeNoException();
          reply.writeInt(c ? 1 : 0);
          return true;
        case 4:
          data.enforceInterface(str);
          str = a(data.readString());
          reply.writeNoException();
          reply.writeString(str);
          return true;
        case 5:
          data.enforceInterface(str);
          str = b(data.readString());
          reply.writeNoException();
          reply.writeString(str);
          return true;
        case 1598968902:
          reply.writeString(str);
          return true;
        default:
          return super.onTransact(code, data, reply, flags);

      }

    }

    public static LenovoIDInterface getHelper(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface iInterface = iBinder.queryLocalInterface("com.zui.deviceidservice.IDeviceidInterface");
      if (iInterface == null || !(iInterface instanceof LenovoIDInterface)) {
        return new len_up.len_down(iBinder);
      }
      else {
        return (LenovoIDInterface) iInterface;
      }
    }
  }
}
