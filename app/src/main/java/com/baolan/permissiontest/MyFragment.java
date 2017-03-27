package com.baolan.permissiontest;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Fragment测试
 * Created by nhd on 2017/3/27.
 */

public class MyFragment extends Fragment implements PermissionUtils.OnRequestPermissionsListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        PermissionUtils.with(this)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setOnRequestPermissions(this);
        return inflate;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), "fragment_success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "fragment_failure", Toast.LENGTH_SHORT).show();
    }
}
