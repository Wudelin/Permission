package com.wdl.permission;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.wdl.lib.PermissionUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    String[] permissonList = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.init(this).requestPermission(permissonList, new PermissionUtil.IPermissionCallback()
        {
            @Override
            public void succeed(List<String> permissions)
            {
                Log.e("wdl", "succeed " + permissions.size());
            }

            @Override
            public void succeed()
            {
                Log.e("wdl", "succeed");
            }

            @Override
            public void failure()
            {
                Log.e("wdl", "failure");
            }

            @Override
            public void failure(List<String> permissions)
            {
                Log.e("wdl", "failure" + permissions.size());
                finish();
            }

            @Override
            public void unRequiredApply()
            {
                Log.e("wdl", "unRequiredApply");
            }
        });


    }
}
