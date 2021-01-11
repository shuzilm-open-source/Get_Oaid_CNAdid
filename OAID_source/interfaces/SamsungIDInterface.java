package com.example.oaid_tool.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

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
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
                mIBinder.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                result = obtain2.readString();
            } catch (Throwable t) {
                obtain2.recycle();
                obtain.recycle();
                t.printStackTrace();
            }
            obtain2.recycle();
            obtain.recycle();

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
