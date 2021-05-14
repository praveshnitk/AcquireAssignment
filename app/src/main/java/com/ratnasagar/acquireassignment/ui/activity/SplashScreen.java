package com.ratnasagar.acquireassignment.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ratnasagar.acquireassignment.R;
import com.ratnasagar.acquireassignment.utils.PermissionHelper;

public class SplashScreen extends AppCompatActivity {
    private static long SPLASH_MILLIS = 1000;
    public static String [] RequiredPermission;
    private PermissionHelper permissionHelper;
    public static int PERMISSION_REQUEST_CODE =  100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        RequiredPermission = new String[]{ Manifest.permission.ACCESS_FINE_LOCATION};
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCheckPermission();//@anurag This method is define for Marshmallow and above required permission
                } else {
                    Intent intent = new Intent(SplashScreen.this,TaskList.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_MILLIS);
    }

    //@pravesh This method is define for Marshmallow and above required permission
    private void mCheckPermission() {
        permissionHelper = new PermissionHelper(this, RequiredPermission, PERMISSION_REQUEST_CODE);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(SplashScreen.this,TaskList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @Override
            public void onPermissionDenied() {
                Toast.makeText(SplashScreen.this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDeniedBySystem() {
                Toast.makeText(SplashScreen.this, "Permission Denied By System.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}