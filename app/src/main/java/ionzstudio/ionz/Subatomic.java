package ionzstudio.ionz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thomas on 8/10/2017.
 */

public class Subatomic{


    //0 = neutron, 1 = proton
    int x;
    int y;
    float radius;
    float textOffset;
    int color;
    int type;
    public Subatomic(int xx, int yy, int type) {
        x=xx;
        y=yy;
        radius= 50;
        textOffset=0.7f*radius;
        this.type=type;
        switch(type) {
            case 0: color = Color.GRAY; break;
            case 1: color = Color.RED; break;
        }
    }
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(color);
        canvas.drawCircle(x,y,radius,paint);
        if (type==1) {
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(radius + radius);
            canvas.drawText("+", x, y + textOffset, paint);
        }
    }
}