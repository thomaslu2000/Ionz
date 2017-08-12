package ionzstudio.ionz;

import android.graphics.Canvas;
import android.graphics.Color;
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
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x,y,radius,paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x,y,radius*0.9f,paint);
        for (int i=0; i<Math.max(protons.size(),neutrons.size());i++){
            if (neutrons.size()>i){ neutrons.get(i).draw(canvas,paint);}
            if (protons.size()>i){ protons.get(i).draw(canvas,paint);}
        }
    }

    public void addNucleon(int typeOfNucleon){ //Takes in 0 or 1. 1.5*Proton radius for this radius multiplier
        n+=1;
        if (n<17) radius = (float) Math.sqrt(n)*75;
        float dis = radius*GameView.rand.nextFloat();
        float angle = 6.284f*GameView.rand.nextFloat();
        (typeOfNucleon==0 ? neutrons : protons).add(new Subatomic((int) (x+dis*Math.cos(angle)),(int) (y+dis*Math.sin(angle)),typeOfNucleon));
    }
    public void removeNucleon(int numOfNeutronsToRemove, int numOfProtonsToRemove){
        for (int i = 0;i<numOfNeutronsToRemove;i++) if(neutrons.size()>0) neutrons.remove(neutrons.size()-1);
        for (int i = 0;i<numOfProtonsToRemove;i++) if(protons.size()>0) protons.remove(protons.size()-1);
    }
}
