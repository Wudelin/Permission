# Permission
利用Fragment封装权限请求


Libcore
============

How to use?
--------
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




Download
--------

```allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
dependencies {
	        implementation 'com.github.Wudelin:Permission:1.0.0'
}
```


