package ionzstudio.ionz;

/**
 * Created by Thomas on 8/10/2017.
 */

public class Particle {
    int x;
    int y;
    float radius;
    float vx;
    float vy;
    int color;

    public Particle(int xx, int yy, float rad){
        x=xx;
        y=yy;
        radius=rad;
    }
    public boolean inParticle(int xx, int yy){
        int dX = xx-x;
        int dY = yy-y;
        return Math.sqrt(dX*dX+dY*dY)<=radius;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public float getRadius() {return radius;}

    public boolean touchesParticle(Particle p){
        int dX = p.getX()-x;
        int dY = p.getY()-y;
        return Math.sqrt(dX*dX+dY*dY)<=radius+p.getRadius();
    }
    public void setVelocity(int velX, int velY){vx=velX;vy=velY;}

}
