package com.wdl.lib;


import android.app.Activity;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;


@SuppressWarnings("unused")
public class PermissionUtil
{
    private PermissionFragment permissionFragment;
    private static final String TAG = "PermissionFragment";

    public static PermissionUtil init(Activity activity)
    {
        return new PermissionUtil(activity);
    }

    public static PermissionUtil init(Fragment fragment)
    {
        return new PermissionUtil(fragment.getActivity());
    }

    /**
     * 获取对应的Fragment
     *
     * @param activity Activity
     */
    private PermissionUtil(Activity activity)
    {
        this.permissionFragment = getPermissionFragment(activity);
    }

    /**
     * 获取对应的Fragment
     *
     * @param fragment Fragment
     */
    private PermissionUtil(Fragment fragment)
    {
        this.permissionFragment = getPermissionFragment(fragment.getActivity());
    }

    /**
     * 查找PermissionFragment是否存在，不存在则加入当前Activity中
     *
     * @param activity Activity
     * @return PermissionFragment
     */
    private PermissionFragment getPermissionFragment(Activity activity)
    {
        if (activity == null) return null;
        PermissionFragment permissionFragment = findPermissionFragment(activity);
        boolean isNewInstance = permissionFragment == null;
        if (isNewInstance)
        {
            permissionFragment = new PermissionFragment();
            FragmentManager fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(permissionFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionFragment;
    }

    /**
     * 根据TAG查找对应的Fragment
     *
     * @param activity Activity
     * @return PermissionFragment
     */
    private PermissionFragment findPermissionFragment(Activity activity)
    {
        return (PermissionFragment) ((AppCompatActivity) activity).getSupportFragmentManager().findFragmentByTag(TAG);
    }

    /**
     * 权限申请
     *
     * @param permissions permissions
     * @param callback    IPermissionCallback
     */
    public void requestPermission(String[] permissions, IPermissionCallback callback)
    {
        // 无需申请
        if (permissions == null || permissions.length <= 0)
        {
            if (callback != null)
                callback.unRequiredApply();

            return;
        }

        // 无需申请
        if (!isM())
        {
            if (callback != null)
                callback.unRequiredApply();

            return;
        }

        // 真实入口
        if (permissionFragment != null && callback != null)
        {
            permissionFragment.fetchPermissions(permissions, callback);
        }

    }

    /**
     * 判断是否需要进行动态权限申请
     *
     * @return true api大于23
     */
    private boolean isM()
    {
        return Build.VERSION.SDK_INT >= 23;
    }


    /**
     * 回调
     */
    public interface IPermissionCallback
    {
        /**
         * 授权成功
         *
         * @param permissions 成功权限
         */
        void succeed(List<String> permissions);

        /**
         * 成功
         */
        void succeed();

        /**
         * 失败
         */
        void failure();


        /**
         * 授权失败
         *
         * @param permissions 失败权限
         */
        void failure(List<String> permissions);

        /**
         * 无需动态申请权限
         */
        void unRequiredApply();
    }
}
