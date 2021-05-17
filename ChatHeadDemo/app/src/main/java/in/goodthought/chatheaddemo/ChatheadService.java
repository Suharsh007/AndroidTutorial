package in.goodthought.chatheaddemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

public class ChatheadService extends Service {
    private WindowManager windowManager;
private View chatheadView;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        chatheadView = LayoutInflater.from(this).inflate(R.layout.chathead_bubble,null);

        int layout_params;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

        {
            layout_params = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        }

        else {

            layout_params = WindowManager.LayoutParams.TYPE_PHONE;

        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
              layout_params,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );



        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;



        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;


        windowManager.addView(chatheadView,params);

        ImageView chatheadImage = chatheadView.findViewById(R.id.chatHeadImage);

        chatheadImage.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float touchX;
            private float touchY;
            private int lastAction;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                     initialX = params.x;
                     initialY = params.y;

                     touchX = event.getRawX();
                     touchY = event.getRawY();

                     lastAction = event.getAction();

                     return  true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (params.y > screenHeight * 0.8) {
                       chatheadView.setVisibility(View.GONE);
                        Toast.makeText(getApplication(), "Removed!",
                                Toast.LENGTH_SHORT).show();
                        vibe.vibrate(200);
                        stopSelf();
                    }
                    if(lastAction == MotionEvent.ACTION_DOWN)
                    {
                      Button button = new Button(ChatheadService.this);
                        button.setText("Close");

                        RelativeLayout layout = chatheadView.findViewById(R.id.chatHead);
                        layout.addView(button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                stopSelf();
                            }
                        });
                    }
                    lastAction = event.getAction();
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    params.x = initialX + (int)(event.getRawX() - touchX);
                    params.y = initialY + (int)(event.getRawY() - touchY);

                    windowManager.updateViewLayout(chatheadView,params);
                    lastAction = event.getAction();
                    return true;
                }
                return false;
            }
        }
        );

    }

 @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatheadView != null)
        {
            windowManager.removeView(chatheadView);
        }
    }
   
}
