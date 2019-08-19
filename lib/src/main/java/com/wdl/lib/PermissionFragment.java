package com.wdl.lib;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionFragment extends Fragment
{

    // 状态码
    private static final int REQUEST_CODE = 0x01;
    private PermissionUtil.IPermissionCallback callback;

    public PermissionFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 设置为 true，表示 configuration change 的时候，fragment 实例不会背重新创建
        setRetainInstance(true);
    }

    public void fetchPermissions(String[] permissions, PermissionUtil.IPermissionCallback callback)
    {
        this.callback = callback;
        // 需要申请的权限列表
        List<String> mPermissionList = new ArrayList<>();
        for (String permission : permissions)
        {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), permission)
                    || shouldShowRequestPermissionRationale(permission))
            {
                mPermissionList.add(permission);
            }
        }

        if (!mPermissionList.isEmpty())
        {
            requestPermissions(mPermissionList.toArray(new String[mPermissionList.size()]), REQUEST_CODE);
        } else
        {
            if (this.callback != null)
            {
                this.callback.unRequiredApply();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE)
        {
            if (grantResults.length > 0)
            {
                boolean isGrant = true;
                List<String> succeedPermission = new ArrayList<>();
                List<String> failurePermission = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    {
                        succeedPermission.add(permissions[i]);
                    } else
                    {
                        failurePermission.add(permissions[i]);
                        isGrant = false;
                    }
                }

                if (this.callback != null)
                {
                    if (isGrant)
                        this.callback.succeed(succeedPermission);
                    else
                        this.callback.failure(failurePermission);
                }
            } else
            {
                if (this.callback != null) this.callback.failure();
            }
        }

    }
}
