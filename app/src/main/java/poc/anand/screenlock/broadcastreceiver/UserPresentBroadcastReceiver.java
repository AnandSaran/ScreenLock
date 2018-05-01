package poc.anand.screenlock.broadcastreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import poc.anand.screenlock.service.CamerService;
import poc.anand.screenlock.service.Camera2Service;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = UserPresentBroadcastReceiver.class.getSimpleName();
    private static final int MEDIA_TYPE_IMAGE = 0;
    private Camera camera;
    private Context mContext;

    @Override
    public void onReceive(Context arg0, Intent intent) {
        Log.e(TAG, "onReceive");
        mContext = arg0;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent front_translucent = new Intent(mContext
                        .getApplicationContext(), Camera2Service.class);


                mContext.startService(
                        front_translucent);


            }
        } else {
            Intent front_translucent = new Intent(mContext
                    .getApplicationContext(), CamerService.class);
            front_translucent.putExtra("Front_Request", true);
            front_translucent.putExtra("Quality_Mode",
                    0);


            mContext.startService(
                    front_translucent);

        }
    }


}