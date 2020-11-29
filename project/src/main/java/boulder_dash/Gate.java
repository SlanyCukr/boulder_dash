package boulder_dash;

import java.util.ArrayList;
import java.util.List;

public class Gate implements Hittable{
    private List<HitListener> hitListenerList;

    public Gate(){
        this.hitListenerList = new ArrayList<HitListener>();
    }

    @Override
    public void hitBy(VisibleEntity visibleEntity) {
        for(HitListener hl : hitListenerList){
            hl.Hitted(visibleEntity.getPosition());
        }
    }

    @Override
    public void addHitListener(HitListener listener) {
        this.hitListenerList.add(listener);
    }

    @Override
    public void removeHitListener(HitListener listener) {
        this.hitListenerList.remove(listener);
    }
}
