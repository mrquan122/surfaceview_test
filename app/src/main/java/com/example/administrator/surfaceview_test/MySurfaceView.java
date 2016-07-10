package com.example.administrator.surfaceview_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private LoopThread thread;
    private String[]strs=new String[]{"相机","IPAD","IPHONE","服装","恭喜发财","美女"};
    private int[]images =new int[]{R.drawable.danfan,R.drawable.f015,R.drawable.f040,
            R.drawable.ipad,R.drawable.iphone,R.drawable.meizi};
    private int[]colors=new int[]{0xffffc300,0xfff17e01,0xffffc300,0xfff17e01,0xffffc300,0xfff17e01};

    private Bitmap[]bitmaps;
    private int ItemCount=6;
    private RectF range= new RectF();
    private int mRadius;
    private double mSpeed=10;
    private volatile  int startAngle=0;
    private boolean isShoudEnd;
    private int mCenter;
    private int mPadding;
    private Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg2);
    private float mTextSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN,20,getResources().getDisplayMetrics());

    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        thread = new LoopThread(context,surfaceHolder);
    }
   @Override
   protected void onMeasure(int widthMeasureSpec,int heighMeasureSpec){
       super.onMeasure(widthMeasureSpec,heighMeasureSpec);
       int width=Math.min(getMeasuredWidth(),getMeasuredHeight());
       mPadding=getPaddingLeft();
       mRadius=width-mPadding*2;
       mCenter=mRadius/2;
       setMeasuredDimension(width,width);
   }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.isRunning=true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

     class LoopThread extends Thread {
        SurfaceHolder surfaceHolder;
        Context context;
        boolean isRunning;
        Canvas mCanvas;
        Paint Arcpaint;

        public LoopThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            this.context = context;
            isRunning = false;
            Arcpaint = new Paint();
            Arcpaint.setDither(true);
            Arcpaint.setAntiAlias(true);
            range = new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);
            bitmaps =new Bitmap[ItemCount];
            for(int i =0;i<ItemCount;i++){
                bitmaps[i]=BitmapFactory.decodeResource(getResources(),images[i]);
            }


        }

        @Override
        public void run() {


            while (isRunning) {


                        long start=System.currentTimeMillis();
                          doDraw();                                  //通过它来控制帧数执行一次绘制后休息50ms
                        long end=System.currentTimeMillis();
                        if(end-start<50){
                            try {
                                Thread.sleep(50-(end-start));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }



            }

            }

        public void doDraw() {
            try{
                mCanvas=surfaceHolder.lockCanvas();
                if(mCanvas!=null){
                    drawBg();

                    float temAngle=startAngle;
                    float sweepAngle=360/ItemCount;
                    for(int i=0;i<ItemCount;i++){
                        Arcpaint.setColor(colors[i]);
                        mCanvas.drawArc(range,temAngle,sweepAngle,true,Arcpaint);
                     //   mCanvas.drawText(temAngle,sweepAngle,strs[i]);
                        drawIcon(temAngle,bitmaps[i]);
                                                                    Log.i("画圆弧", String.valueOf(colors[i]));
                        temAngle+=sweepAngle;
                    }
                 startAngle+=mSpeed;
                  mSpeed--;

                }
            }catch (Exception e){

            }finally {
                if(mCanvas!=null){
                    surfaceHolder.unlockCanvasAndPost(mCanvas);
                }
            }


        }

         private void drawBg() {
             Log.i("画背景","背景");
             mCanvas.drawColor(Color.WHITE);
             mCanvas.drawBitmap(bgBitmap,null,new RectF(mPadding/2,mPadding/2,getMeasuredWidth()-mPadding/2,
                     getMeasuredHeight()-mPadding/2),null);

         }

         private void drawIcon(float temAngle,Bitmap bitmap){
             int imgWidth=mRadius/8;
             float angle =(float)((temAngle+360/ItemCount/2)*Math.PI/180);
             int x =(int)(mCenter+mRadius/2/2*Math.cos(angle));
             int y=(int)(mCenter+mRadius/2/2*Math.sin(angle));
             RectF rect= new RectF(x-imgWidth/2,y-imgWidth/2,x+imgWidth/2,y+imgWidth/2);
             mCanvas.drawBitmap(bitmap,null,rect,null);
         }


     }


 }

   
