package poc.anand.screenlock.activty;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import poc.anand.screenlock.R;
import poc.anand.screenlock.adapter.ImageAdapter;
import poc.anand.screenlock.service.SensorService;

public class MainActivity extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_CAMERA_AND_STORAGE = 1001;
    private boolean isPermissionGranted = true;
    private SensorService mSensorService;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mSensorService = new SensorService(this);
            mServiceIntent = new Intent(this, mSensorService.getClass());
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CAMERA_AND_STORAGE);


            } else {
                isPermissionGranted = true;
                showImages();

            }
        } else {
            showImages();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA_AND_STORAGE:

                for (int i = 0; i < permissions.length; i++) {
                    int grantResult = grantResults[i];

                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Please enable permission", Toast.LENGTH_SHORT).show();
                        isPermissionGranted = false;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 0);
                    }
                }
                if (isPermissionGranted) {
                    showImages();

                }
                break;
        }
    }

    private void showImages() {
        File imageDir = new File(Environment.getExternalStorageDirectory().toString() +
                "/MY_GALLERY");
        if ((imageDir.exists())) {
            File[] mediaFiles = imageDir.listFiles();
            RecyclerView recyclerView = findViewById(R.id.rvImageList);
            int numberOfColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
            ImageAdapter imageAdapter = new ImageAdapter(imageDir.listFiles());
            recyclerView.setAdapter(imageAdapter);

        }
    }

}
