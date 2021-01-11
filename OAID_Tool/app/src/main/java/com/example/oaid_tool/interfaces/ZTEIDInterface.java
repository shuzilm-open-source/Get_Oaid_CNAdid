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
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    binder.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        obtain2.recycle();
                        obtain.recycle();
                        v0 = true;
                    }
                } catch (Throwable v0_1) {
                    obtain2.recycle();
                    obtain.recycle();
                    v0_1.printStackTrace();
                }
                return v0;
            }

            @Override
            public String getOAID() {
                String result = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    binder.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    result = obtain2.readString();
                } catch (Throwable v0) {
                    obtain.recycle();
                    obtain2.recycle();
                }

                obtain.recycle();
                obtain2.recycle();
                return result;
            }


            @Override
            public boolean isSupported() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                boolean result = false;

                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    binder.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        result = true;
                    }

                } catch (Exception e){
                    obtain2.recycle();
                    obtain.recycle();
                }
                return result;
            }

            @Override
            public void shutDown() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    binder.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } catch (Throwable v0) {
                    obtain2.recycle();
                    obtain.recycle();
                }
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }
}
