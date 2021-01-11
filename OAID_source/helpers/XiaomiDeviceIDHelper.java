package com.example.oaid_tool.helpers;

import android.content.Context;

import java.lang.reflect.Method;

/****************************
 * on 2019/10/29
 ****************************
 */
public class XiaomiDeviceIDHelper {
    private Context mContext;

    private Class idProvider;
    private Object idImpl;
    private Method oaid;



    public XiaomiDeviceIDHelper(Context ctx) {
        mContext = ctx;

        try {
            idProvider = Class.forName("com.android.id.impl.IdProviderImpl");
            idImpl = idProvider.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            oaid = idProvider.getMethod("getOAID", new Class[]{Context.class});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String invokeMethod(Context ctx, Method method) {
        String result = null;
        if (idImpl != null && method != null) {
            try {
                result = (String) method.invoke(idImpl, ctx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getOAID() {
        return invokeMethod(mContext, oaid);
    }

}
