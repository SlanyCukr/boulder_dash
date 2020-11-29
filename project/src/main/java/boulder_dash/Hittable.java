package boulder_dash;

import java.io.Serializable;

public interface Hittable extends Serializable {
    void hitBy(VisibleEntity visibleEntity);
    void addHitListener(HitListener listener);
    void removeHitListener(HitListener listener);
}