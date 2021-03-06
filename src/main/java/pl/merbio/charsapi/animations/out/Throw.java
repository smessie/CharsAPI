package pl.merbio.charsapi.animations.out;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.OutputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;

/**
 * @author Merbio
 */
public class Throw extends OutputAnimation {

    private CharsFallingBlockContainer cfbc;
    private ArrayList<Point> list;
    private int iterator;

    public Throw() {
        super(4L);
    }

    @Override
    protected void onPrepare() {
        this.list = new ArrayList<>();
        cfbc = new CharsFallingBlockContainer();

        for (int h = 0; h < cs.getHeight(); h++) {
            for (int w = 0; w < cs.getWidth(); w++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                if (cb != null) {
                    list.add(new Point(w, h, cb));
                }
            }
        }

        iterator = 0;
    }

    @Override
    protected void onCancel() {
        cfbc.clearBlocks();
    }

    @Override
    public void run() {
        if (iterator >= list.size()) {
            stopTask();
            return;
        }
        Point p = list.get(iterator);
        if (csbb.front[p.w][p.h] != null) {
            cfbc.throwFallingBlockFromBlock(csbb.front[p.w][p.h].getLocation());
            if (csbb.over[p.w][p.h] != null) {
                cfbc.throwFallingBlockFromBlock(csbb.over[p.w][p.h].getLocation());
            }
            setBeforeBlock(p.w, p.h);
        }

        iterator++;
    }

}
