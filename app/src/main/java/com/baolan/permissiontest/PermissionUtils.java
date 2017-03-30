package com.baolan.permissiontest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * 权限判断
 * Created by nhd on 2017/3/23.
 */

public class PermissionUtils {
    private static final int REQUEST_CODE = 1001;
    private Object object;
    private static OnRequestPermissionsListener listener;

    private PermissionUtils(Object object) {
        this.object = object;
    }

    public static PermissionUtils with(@NonNull Object object) {
        return new PermissionUtils(object);
    }

    public PermissionUtils setPermissions(String... permissions) {
        if (isAndroidM()) {
            if (hasPermission(permissions)) {
                if (null != listener) {
                    listener.onSuccess();
                }
            } else {
                if (object instanceof Activity) {
                    getActivity(object).requestPermissions(permissions, REQUEST_CODE);
                } else if (object instanceof Fragment) {
                    getFragment(object).requestPermissions(permissions, REQUEST_CODE);
                }
            }
        } else {
            if (null != listener) {
                listener.onSuccess();
            }
        }
        return this;
    }

    private Fragment getFragment(Object object) {
        return (Fragment) object;
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (hasPermission(grantResults)) {
                if (null != listener) {
                    listener.onSuccess();
                }
            } else {
                if (null != listener) {
                    listener.onFailure();
                }
            }
        }
    }

    private static boolean hasPermission(int... grantResults) {
        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean hasPermission(String... permissions) {
        for (String s : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(object), s) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface OnRequestPermissionsListener {
        void onSuccess();

        void onFailure();
    }

    public void setOnRequestPermissions(OnRequestPermissionsListener listener) {
        PermissionUtils.listener = listener;
    }

    private static Activity getActivity(Object object) {
        if (null != object) {
            if (object instanceof Activity) {
                return (Activity) object;
            } else if (object instanceof Fragment) {
                return ((Fragment) object).getActivity();
            }
        }
        return null;
    }

    private boolean isAndroidM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
