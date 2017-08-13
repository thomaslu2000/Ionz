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

import java.util.Random;

/**
 * Created by Thomas on 8/9/2017.
 */

class GameView extends SurfaceView implements Runnable{
    volatile boolean playing; //just to check if playing. volatile means it can be affected by other threads, I think

    private Thread gameThread = null; //This is the thread. I think it does things
    private Context mContext; //This stores the context. It'll be useful when we want shared preferences or to leave the screen


    private Paint paint; //Pretty much your paint brush
    private Canvas canvas; //Your painting canvas
    private SurfaceHolder surfaceHolder;

    public static int max_x;
    public static int max_y;
    public static RectF gameScreen;

    public static float unit10;
    public boolean inGame;

    RectF game1;
    RectF game2;

    int level=-1;

    Atom hydrogen;

    public static Random rand = new Random();

    public GameView(Context context, int x, int y) {
        super(context);
        mContext=context;
        surfaceHolder = getHolder(); //initialize things
        paint = new Paint();

        max_x=x;// record max screen size
        max_y=y;

        gameScreen= new RectF(0,0,max_x,max_y);

        unit10 = 10*(x+y)/2000;
        game1=new RectF(unit10*10, unit10*5, unit10*30, unit10*25);
        game2=new RectF(x-unit10*30, unit10*5, x-unit10*10, unit10*25);

    }
    public void run() {
        while (playing) { //This is the main loop for the game
            switch(level){
                case 1:
                    draw1();
                    update1();
                    sleep();
                    break;
                case 2:
                    draw2();
                    update2();
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
        switch(level){
            case 2: touch2(motionEvent); break;
            default:
                int x = (int) motionEvent.getX(); //These get the touch locations
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) { //a switch block for different ways they can touch
                    case MotionEvent.ACTION_DOWN://just pressing down

                        break;
                    case MotionEvent.ACTION_MOVE://dragging finger
                        break;
                    case MotionEvent.ACTION_UP://letting go
                        if (!inGame) {
                            if (game1.contains(x, y)) {
                                level = 1;
                                hydrogen = new Atom(x, y, 1);
                                inGame=true;
                            }
                            if (game2.contains(x, y)) {
                                level = 2;
                                inGame=true;
                                nucleus=new Nucleus(max_x/2,max_y/4);
                                hydrogen = new Atom(x, y, 1);
                                for(int i = 0; i<60; i++) nucleus.addNucleon(rand.nextInt(2));
                            }
                        }
                        break;
                }
        }
        return true;
    }
    //dd
    //hi

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

            hydrogen.draw(canvas, paint);

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas); //When you finished drawing the frame, you have to do this to save the changes
        }
    }

    private void update1() {
        hydrogen.update();
    }

    //Level2

    Nucleus nucleus;
    private int[] circCoord = new int[2];
    private boolean circOn=false;

    private void draw2() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas(); //You have to do this whenever you want to draw
            canvas.drawColor(Color.WHITE); //Just the background is now white
            nucleus.draw(canvas,paint);
            hydrogen.draw(canvas,paint);
            if (circOn)drawMenu();

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas); //When you finished drawing the frame, you have to do this to save the changes
        }
    }
    private void update2(){
        hydrogen.update();
        nucleus.update();
    }
    private void touch2(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX(); //These get the touch locations
        int y = (int) motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) { //a switch block for different ways they can touch
            case MotionEvent.ACTION_DOWN://just pressing down
                circOn=true;
                circCoord[0]=x;circCoord[1]=y;
                break;
            case MotionEvent.ACTION_MOVE://dragging finger
                break;
            case MotionEvent.ACTION_UP://letting go
                circOn=false;
                int xx = x-circCoord[0]; int yy = y-circCoord[1];
                if (Math.sqrt(xx*xx+yy*yy)>200){
                    if (xx>0){
                        if (yy>0){
                            //e-
                            nucleus.removeNucleon(0,1);
                            nucleus.addNucleon(1);
                        } else{
                            //b+
                            nucleus.bplus();
                        }
                    } else{
                        if (yy>0){
                            //a
                            nucleus.removeNucleon(2,2);
                        }else{
                            //b-
                            nucleus.bminus();
                            /*nucleus.removeNucleon(1,0);
                            nucleus.addNucleon(1);*/
                        }
                    }
                }

                break;
        }
    }
    private void drawMenu(){
        paint.setARGB(25,135,250,255);
        canvas.drawCircle(circCoord[0],circCoord[1],200,paint);
        paint.setARGB(150,255,167,35);
        paint.setTextSize(300);
        canvas.drawText("β-",circCoord[0]-250,circCoord[1]-225,paint);
        canvas.drawText("β+",circCoord[0]+300,circCoord[1]-225,paint);
        canvas.drawText("α",circCoord[0]-300,circCoord[1]+300,paint);
        canvas.drawText("e-",circCoord[0]+300,circCoord[1]+300,paint);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawLine(circCoord[0]-500,circCoord[1],circCoord[0]+500,circCoord[1],paint);
        canvas.drawLine(circCoord[0],circCoord[1]-500,circCoord[0],circCoord[1]+500,paint);
    }
    public static int get1orNeg1(){
        return (rand.nextInt(2)==0?1:-1);
    }

}


