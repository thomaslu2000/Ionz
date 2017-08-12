package ionzstudio.ionz;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Nucleus {
    int x;
    int y;
    float radius;
    int n;
    ArrayList<Subatomic> protons = new ArrayList<>();
    ArrayList<Subatomic> neutrons = new ArrayList<>();
    public Nucleus(int xx, int yy){
        x=xx;
        y=yy;
        protons.add(new Subatomic(xx,yy,1));
        n=1;
    }
    public void draw(Canvas canvas, Paint paint){
        for (Subatomic a : neutrons) a.draw(canvas,paint);
        for (Subatomic a : protons) a.draw(canvas,paint);
    }
    public void addNucleon(int typeOfNucleon){ //Takes in 0 or 1. 1.5*Proton radius for this radius multiplier
        n+=1;
        if (n<17) radius = (float) Math.sqrt(n)*75;
        float dis = radius*GameView.rand.nextFloat();
        float angle = 6.284f*GameView.rand.nextFloat();
        (typeOfNucleon==0 ? neutrons : protons).add(new Subatomic((int) (x+dis*Math.cos(angle)),(int) (y+dis*Math.sin(angle)),typeOfNucleon));

    }
}
