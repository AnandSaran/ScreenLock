package poc.anand.screenlock.activty;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import poc.anand.screenlock.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateNextScreen();

            }


        }, 700);
    }

    private void navigateNextScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
