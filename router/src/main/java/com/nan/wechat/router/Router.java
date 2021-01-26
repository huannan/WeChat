package com.nan.wechat.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class Router {

    private Map<String, Class<? extends Activity>> mRouteMap;

    private Router() {
        mRouteMap = new HashMap<>();
    }

    private static final class Holder {
        static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Application application) {
        List<String> classNames = getClassNames("com.nan.wechat.router.compile", application.getApplicationContext());
        for (String className : classNames) {
            try {
                Object object = Class.forName(className).newInstance();
                if (object instanceof IRouterInjector) {
                    ((IRouterInjector) object).injectActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addActivity(@NonNull String key, @NonNull Class<? extends Activity> clazz) {
        if (!mRouteMap.containsKey(key)) {
            mRouteMap.put(key, clazz);
        }
    }

    public Class<? extends Activity> getActivity(@NonNull String key) {
        Class<? extends Activity> clazz = null;
        if (mRouteMap.containsKey(key)) {
            clazz = mRouteMap.get(key);
        }
        return clazz;
    }

    private List<String> getClassNames(String packageName, Context context) {
        List<String> classNames = new ArrayList<>();
        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (className.contains(packageName)) {
                    classNames.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }

}
