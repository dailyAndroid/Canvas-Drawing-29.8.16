package com.example.hwhong.canvasdrawing;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Paint paint;

    private MyCanvas myCanvas;

    private Boolean erase = false;

    private Button eraser, newCanvas, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas);

        eraser = (Button) findViewById(R.id.eraser);
        newCanvas = (Button) findViewById(R.id.newCanvas);
        save = (Button) findViewById(R.id.save);

        paint = new Paint();

        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);

        eraser.setOnClickListener(this);
        newCanvas.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    public void setErase(boolean isErase){
        erase = isErase;

        paint.setXfermode(erase ? new PorterDuffXfermode(PorterDuff.Mode.CLEAR) : null);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.eraser:
                setErase(true);
                break;

            case R.id.newCanvas:
                myCanvas.startNew();
                break;

            case R.id.save:
                myCanvas.setDrawingCacheEnabled(true);

                String img = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        myCanvas.getDrawingCache(),
                        UUID.randomUUID().toString() + ".png", "drawing");

                if(img!=null){
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    savedToast.show();
                }
                else{
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                myCanvas.destroyDrawingCache();
                break;
        }
    }
}
