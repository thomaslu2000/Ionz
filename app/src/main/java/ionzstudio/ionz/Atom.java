package ionzstudio.ionz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Toby on 8/9/17.
 */

public class Atom extends Particle{

    //Changes: Atom is now a part of the Particle class which has some other nice functions we can use in other classes

    private int atomicNum;

    public Atom(int xx, int yy, int num) {
        super(xx,yy,35); //xx is x coordinate, yy, is y coordinate, 35 is radius
        color = colors[num - 1];
        atomicNum = num;
        vx=4;
        vy=4;
    }
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.RED);
        canvas.drawCircle(x,y,radius,paint);
    }
    public void update(){
        x += vx;
        y += vy;

        if (x <= radius || x >= GameView.max_x - radius) {
            vx = -vx;
        }
        if (y <= radius || y >= GameView.max_y -40 - radius) {
            vy = -vy;
        }
    }
    public void bounce(double angle){
        double a = 2*(vx*Math.cos(angle)+vy*Math.sin(angle));
        vx= (float) (a*Math.cos(angle)-vx);
        vy= (float) (a*Math.sin(angle)-vy);
    }
    public static double rotateAngle(double angle, double angShift){
        angle+=angShift;
        if (angle>Math.PI) angle-=2*Math.PI;
        if (angle<-Math.PI) angle+=2*Math.PI;
        return angle;
    }
    int[] colors={Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

}
