package com.example.administrator.surfaceview_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            MySurfaceView mySurfaceView = new MySurfaceView(this);
            setContentView(mySurfaceView);
        }
}

    /**
     *
     *
        //内部类
        class MyView extends SurfaceView implements SurfaceHolder.Callback{

            SurfaceHolder holder;
            public MyView(Context context) {
                super(context);
                holder = this.getHolder();//获取holder
                holder.addCallback(this);
                //setFocusable(true);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                new Thread(new MyThread()).start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            //内部类的内部类
            class MyThread implements Runnable{

                @Override
                public void run() {
                    Canvas canvas = holder.lockCanvas(null);//获取画布
                    Paint mPaint = new Paint();
                    mPaint.setColor(Color.YELLOW);

                    canvas.drawRect(new RectF(0,0,1300,1600), mPaint);
                    holder.unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像

                }

            }

        }
    }

 */