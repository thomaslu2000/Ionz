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
    ArrayList<Subatomic> transforming = new ArrayList<>();
    ArrayList<Subatomic> moving = new ArrayList<>();
    public Nucleus(int xx, int yy){
        x=xx;
        y=yy;
        protons.add(new Subatomic(xx,yy,1));
        n=1;
    }
    public void update(){
        for (int i=0;i<transforming.size();i++){
            transforming.get(i).update();
            if (transforming.get(i).getTransformationTimer()==0){
                (transforming.get(i).getType()==0?neutrons:protons).add(transforming.get(i));
                moving.add(new Subatomic(transforming.get(i).getX(),transforming.get(i).getY(),transforming.get(i).getTransformType()));
                moving.get(moving.size()-1).setVelocity((1+GameView.rand.nextInt(10))*GameView.get1orNeg1(),(1+GameView.rand.nextInt(10))*GameView.get1orNeg1());
                transforming.remove(transforming.get(i));
            }
        }
        for (int i = 0; i<moving.size();i++){
            moving.get(i).update();
            if (!GameView.gameScreen.contains(moving.get(i).getX(),moving.get(i).getY())) moving.remove(i);
        }
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
        for (Subatomic a: transforming) a.draw(canvas,paint);
        for (Subatomic a: moving) a.draw(canvas,paint);
    }

    public void addNucleon(int typeOfNucleon){ //Takes in 0 or 1. 1.5*Proton radius for this radius multiplier
        n++;
        if (n<26) radius = (float) Math.sqrt(n)*50;
        float dis = radius*GameView.rand.nextFloat();
        float angle = 6.284f*GameView.rand.nextFloat();
        (typeOfNucleon==0 ? neutrons : protons).add(new Subatomic((int) (x+dis*Math.cos(angle)),(int) (y+dis*Math.sin(angle)),typeOfNucleon));
    }
    public void removeNucleon(int numOfNeutronsToRemove, int numOfProtonsToRemove){
        for (int i = 0;i<numOfNeutronsToRemove;i++) if(neutrons.size()>0) neutrons.remove(neutrons.size()-1);
        for (int i = 0;i<numOfProtonsToRemove;i++) if(protons.size()>0) protons.remove(protons.size()-1);
        n=neutrons.size()+protons.size();
        if (n<26) radius = (float) Math.sqrt(n)*50;
        System.out.println(n);
    }
    public void bGeneric(ArrayList<Subatomic> a, int extraParticleType){ //a = neutrons: bminus, extraParticleType: 2=bminus,3=bplus
        transforming.add(a.get(a.size()-1));
        a.get(a.size()-1).setTransformType(extraParticleType);
        a.remove(a.size()-1);
        transforming.get(transforming.size()-1).transform();
    }
    public void bminus(){
        bGeneric(neutrons,2);
    }
    public void bplus() {
        bGeneric(protons,3);
        System.out.println(protons.size()+"  "+neutrons.size()+"  "+transforming.size()+"  "+moving.size());
    }
}
