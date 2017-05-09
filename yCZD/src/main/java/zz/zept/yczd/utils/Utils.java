package zz.zept.yczd.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import zz.zept.yczd.R;
import zz.zept.yczd.view.AlertDialog;

/**
 * 通用工具类
 *
 * @author dewyze
 */
public class Utils {

    private static final String TAG = "CommonUtils";

    static AlertDialog dialog;
    static ProgressDialog progressDialog;

    /**
     * 开启activity
     */
    public static void launchActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    public static void launchActivityForResult(Activity context,
                                               Class<?> activity, int requestCode) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeybord(Activity activity) {

        if (null == activity) {
            return;
        }
        try {
            final View v = activity.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 判断是否为合法的json
     *
     * @param jsonContent 需判断的字串
     */
    public static boolean isJsonFormat(String jsonContent) {
        try {
            new JsonParser().parse(jsonContent);
            return true;
        } catch (JsonParseException e) {
            LogUtils.i(TAG, "bad json: " + jsonContent);
            return false;
        }
    }


    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 显示进度
     *
     * @param context
     * @param title
     * @param message
     * @param indeterminate
     * @param cancelable
     * @return
     */
    public static ProgressDialog showProgress(Context context,
                                              CharSequence title, CharSequence message, boolean indeterminate,
                                              boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.setDefaultButton(false);
        dialog.show();
        return dialog;
    }

    public static String softVersion(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    /**
     * 显示等待对话框，一般用于网络请求的时候
     *
     * @param context context
     */

    public static void showWaiting(Context context) {
//        dialog = new AlertDialog(context, R.style.dialogstyle, false);
//        dialog.setContentView(R.layout.alert_dialog);
//        dialog.show();
        progressDialog = ProgressDialog.show(context,"提示","正在加载...");
    }

    /**
     * 显示等待对话框，一般用于网络请求的时候
     *
     * @param context context
     * @param cancel  setCanceledOnTouchOutside
     */
    public static void showWaiting(Context context, boolean cancel) {
        dialog = new AlertDialog(context, R.style.dialogstyle, cancel);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.show();
    }

    /**
     * 关闭等待对话框
     */
    public static void closeWaiting() {
//        if (dialog != null) {
//            dialog.dismiss();
//            dialog = null;
//        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
