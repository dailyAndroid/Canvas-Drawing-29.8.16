package com.example.hwhong.canvasdrawing;

//for settingup
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

//for touch setup
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by hwhong on 8/29/16.
 */
public class MyCanvas extends View {

    //drawing path
    private Path path;
    //drawing and canvas paint
    private Paint paint, canvasPaint;
    //initial color
    private int paintColor = 0xFF009900;
    //canvas
    private Canvas canvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        path = new Path();


        paint = new Paint();
        paint.setColor(paintColor);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        //Paint flag that enables dithering when blitting.
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //called when the custom view is assigned a size
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //initializing of drawcanvas
        canvas = new Canvas(canvasBitmap);
    }

    //allow the function class to act as a drawing canvas
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);
    }

    //listens for the user's touch inputs
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        
        return true;
    }

    public void startNew(){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
