package com.nullptr.one.util;


import android.widget.Toast;
import com.nullptr.one.MyApplication;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/7
 * @DESCRIPTION Toast工具类，用于简化Toast的展示。
 */
public class ToastUtil {

    // Toast对象
    private static Toast toast = null;

    // 构造方法私有化 不允许new对象
    private ToastUtil() {
    }

    /*
     * 显示Toast
     */
    public static void showText(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
