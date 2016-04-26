package im.brianoneill.chatmap.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import im.brianoneill.chatmap.R;

/**
 * Created by brianoneill on 15/04/16.
 */
public class DragDropUserIconView extends SurfaceView implements Runnable, View.OnTouchListener, SurfaceHolder.Callback, IdCreatorActivity.PauseThread{

    //userIconImage will be set to the photo take with the camera in IDCreatorActivity.java
    public static Bitmap userIconImage;
    public Bitmap maskImage = BitmapFactory.decodeResource(getResources(), R.drawable.chesse_mask);
    public Bitmap result;
    public Paint maskPaint;

    //debug var
    int runcount = 0;


    public float imageX = 0;
    public float imageY = 0;

    public float maskX = 0;
    public float maskY = 0;

    Thread thread = null;

    //SurfaceHolder allows change of dimensions etc
    SurfaceHolder surfaceHolder;
    boolean isRunnable = false;

    Canvas canvas;
    Canvas tempCanvas;

    //I've added a Bitmap to the three required constructors to get the userIconImage taken with the camera.
    public DragDropUserIconView(Context context) {
        super(context);
        getHolder().addCallback(this);
        init();
    }

    public DragDropUserIconView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        getHolder().addCallback(this);
        init();
    }

    public DragDropUserIconView(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        getHolder().addCallback(this);
        init();
    }

    public void setUserIconImage(Bitmap userIconImage){
        this.userIconImage = userIconImage;
    }


    public void init(){
        surfaceHolder = getHolder();
        setOnTouchListener(this);
    }


    @Override
    public void run() {



        while(isRunnable){
            // draw to the canvas
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //if the surface isn't available go back to the top of the loop
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }

            result = Bitmap.createBitmap(maskImage.getWidth(), maskImage.getHeight(), Bitmap.Config.ARGB_8888);

            //lock the canvas before trying to draw to it
            canvas = surfaceHolder.lockCanvas();
            maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            tempCanvas = new Canvas(result);
            //draw the bitmaps to the canvas
            canvas.drawARGB(255,255,255,255);
            tempCanvas.drawBitmap(userIconImage, imageX - userIconImage.getWidth()/2,
                    imageY - userIconImage.getHeight()/2, null);
            tempCanvas.drawBitmap(maskImage, maskX, maskY, maskPaint);
            maskPaint.setXfermode(null);
            //draw in the centre of the canvas
            canvas.drawBitmap(result, this.getWidth()/2 - maskImage.getWidth()/2,
                    this.getHeight()/2 - maskImage.getHeight()/2, new Paint());

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
        result = null;
    }// run()



    // handles life cycle events
    // to test if the thread can be run - no
    public void pause(){
        //should be set to false
        isRunnable = false;

        while(true){
            try{
                //finish off the thread by joining back to the main thread
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            break;
        }//while
        thread = null;
    }//onPause()


    // handles life cycle events
    // to test if the thread can be run - yes
    public void resume(){

        isRunnable = true;
        //this checks for a runnable in the class - the run method
        thread = new Thread(this);
        thread.start();

    }// resume()


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_MOVE:
                // translate the touch event to give the appearance of the event
                // being in the centre of image rather than top left corner
                imageX = motionEvent.getX();
                imageY = motionEvent.getY();
//                imageX = motionEvent.getX() - userIconImage.getWidth()/2;
//                imageY = motionEvent.getY() - userIconImage.getHeight()/2;
                break;
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.resume();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.pause();
    }

   // this method will be called be the interface in IdCreator
    @Override
    public void pauseThread() {
        this.pause();
    }
}

