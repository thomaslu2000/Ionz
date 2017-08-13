package ionzstudio.ionz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thomas on 8/10/2017.
 */

public class Subatomic extends Particle{


    //0 = neutron, 1 = proton, 2 = electron, 3 = positron
    float textOffset;
    int type;
    int transformationTimer;
    int transformType;
    final int transformationInt = 20;
    public Subatomic(int xx, int yy, int type) {
        super(xx,yy, type<2?50:25);
        textOffset=0.65f*radius;
        this.type=type;
        setColor();
    }
    public void update(){
        if(vx!=0 || vy!=0){
            x+=vx;
            y+=vy;
        }
        if (transformationTimer>0){
            transformationTimer--;
        }
    }
    public void draw(Canvas canvas, Paint paint){
        if (transformationTimer>0){
            paint.setARGB(100, 255,84,0);
            canvas.drawCircle(x,y,radius+8*(transformationInt/2-Math.abs(transformationInt/2-transformationTimer)),paint);
            paint.setAlpha(255);
            canvas.drawCircle(x,y,radius,paint);

        } else {
            paint.setColor(color);
            canvas.drawCircle(x, y, radius, paint);
            if (type != 0) {
                paint.setColor(Color.BLACK);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(radius + radius);
                canvas.drawText((type == 2 ? "–" : "+"), x, y + textOffset, paint);
            }
        }
    }
    public void setColor(){
        switch(type) {
            case 0: color = Color.GRAY; break;
            case 1: color = Color.RED; break;
            case 2: color = Color.YELLOW; break;
            case 3: color = Color.CYAN; break;
        }
    }
    public void transform(){
        transformationTimer+=transformationInt;
        type=1-type;
        setColor();
    }
    public int getTransformationTimer(){return transformationTimer;}
    public int getType(){return type;}
    public void setTransformType(int n){transformType=n;}
    public int getTransformType(){return transformType;}
}