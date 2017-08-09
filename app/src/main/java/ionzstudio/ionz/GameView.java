package ionzstudio.ionz;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Thomas on 8/9/2017.
 */

class GameView extends SurfaceView implements Runnable{
    volatile boolean playing; //just to check if playing. volatile means it can be affected by other threads, I think

    private Thread gameThread = null; //This is the thread. I think it does things
    private Context mContext; //This stores the context. It'll be useful when we want shared preferences or to leave the screen


    private Paint paint; //Pretty much your paint brush
    private Canvas canvas; //Your painting canvas
    private SurfaceHolder surfaceHolder; //??? your easel? ??

    private int max_x;
    private int max_y;

    public static float unit10;

    RectF game1;
    RectF game2;

    int level=-1;


    public GameView(Context context, int x, int y) {
        super(context);
        mContext=context;
        surfaceHolder = getHolder(); //initialize things
        paint = new Paint();

        max_x=x;// record max screen size
        max_y=y;

        unit10 = 10*(x+y)/2000;
        game1=new RectF(unit10*10, unit10*5, unit10*30, unit10*25);
        game2=new RectF(x-unit10*10, unit10*5, x-unit10*30, unit10*25);

    }
    public void run() {
        while (playing) { //This is the main loop for the game
            switch(level){
                case 1:
                    draw1();
                    sleep();
                    break;
                case 2:
                    draw2();
                    sleep();
                    break;
                default:
                    update(); //Move all objects

                    draw(); //Actually draw them

                    sleep(); //pause for a few millisecs before starting next frame
                    break;
            }

        }
    }



    private void update(){
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas(); //You have to do this whenever you want to draw
            canvas.drawColor(Color.RED); //Just the background is now white
            paint.setColor(Color.BLACK);
            canvas.drawRect(game1, paint);
            canvas.drawRect(game2, paint);

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas); //When you finished drawing the frame, you have to do this to save the changes
        }
    }

    private void sleep(){
        try {
            gameThread.sleep(17); //This is how long of a wait b/w frames
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) { //These are the touch sensores
        int x = (int) motionEvent.getX(); //These get the touch locations
        int y = (int) motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) { //a switch block for different ways they can touch
            case MotionEvent.ACTION_DOWN://just pressing down

                break;
            case MotionEvent.ACTION_MOVE://dragging finger
                break;
            case MotionEvent.ACTION_UP://letting go
                if (game1.contains(x,y)){
                    level=1;
                }
                if (game2.contains(x,y)){
                    level=2;
                }
                break;
        }
        return true;
    }
    //dd

    public void pause() {
        playing = false; //paused-->not playing
        try {
            //stops the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true; //resumed --> playing
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Level1
    private void draw1() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas(); //You have to do this whenever you want to draw
            canvas.drawColor(Color.WHITE); //Just the background is now white


            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas); //When you finished drawing the frame, you have to do this to save the changes
        }
    }

    //Level2
    private void draw2() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas(); //You have to do this whenever you want to draw
            canvas.drawColor(Color.CYAN); //Just the background is now white


            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas); //When you finished drawing the frame, you have to do this to save the changes
        }
    }

}
