package ionzstudio.ionz;

import android.graphics.Color;

/**
 * Created by Thomas on 8/10/2017.
 */

public class Subatomic extends Particle {


    //type: 0 = electron, 1 = neutron, 2 = proton
    private int[] colors = {Color.YELLOW,Color.GRAY,Color.RED};
    public Subatomic(int xx, int yy, int type) {
        super(xx, yy, (type==0?5:10));
        color=colors[type];
    }
}
