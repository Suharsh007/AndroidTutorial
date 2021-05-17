package in.goodthought.chatheaddemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
public final static int PERMISSION_REQUEST_CODE =1;
    private static final String TAG = "MainActivity";
    private WindowManager windowManager;
    private View chatheadView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this))
        {
            Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:"+getPackageName()));
            startActivityForResult(i,PERMISSION_REQUEST_CODE);
        }
        else
        {
            showChatHead();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PERMISSION_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                showChatHead();
            }
        }
    }

    private void showChatHead() {
       startService(new Intent(MainActivity.this,ChatheadService.class));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Destroyed Chat header");
        if (chatheadView != null)
        {
            showChatHead();
        }
    }

}