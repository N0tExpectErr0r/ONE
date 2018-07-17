package com.nullptr.one.util;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/26
 * @DESCRIPTION Activity管理类
 */
@Deprecated
public class ActivityCollector {

    public static List<Activity> mActivityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mActivityList.clear();
    }
}
