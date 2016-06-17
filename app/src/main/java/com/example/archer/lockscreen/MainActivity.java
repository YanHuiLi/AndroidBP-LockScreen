package com.example.archer.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //获取设备管理服务
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdminSample = new ComponentName(MainActivity.this,AdminReceiver.class);//设备管理组件

//        mDPM.lockNow();//立即锁屏
//        finish();
    }

    //一键锁屏

    public void LockScreen(View view) {

       if (mDPM.isAdminActive(mDeviceAdminSample)){
           mDPM.lockNow();//立即锁屏
           mDPM.resetPassword("1",0);
       }else {
           Toast.makeText(this,"必须激活设备管理器",Toast.LENGTH_SHORT).show();
       }



    }




    public void activeAdmin(View view) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "哈哈哈, 我们有了超级设备管理器, 好NB!");
        startActivity(intent);
    }

    public void uninstall(View view) {

        //取消激活
        mDPM.removeActiveAdmin(mDeviceAdminSample);


//        // 卸载程序
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivity(intent);

        // 卸载程序
        Intent intent = new Intent(Intent.ACTION_DELETE);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri packageUri=Uri.parse("package:"+getPackageName());
        intent.setData(packageUri);
        startActivity(intent);
    }

    public void ClearData(View view) {
        if (mDPM.isAdminActive(mDeviceAdminSample)){
            mDPM.wipeData(0);//清除数据，恢复出厂设置
        }else {
            Toast.makeText(this,"必须激活设备管理器",Toast.LENGTH_SHORT).show();
        }
    }
}
