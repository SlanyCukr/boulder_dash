package boulder_dash;

import java.io.Serializable;

public interface SimulatedEntity extends Serializable {
    public void simulate(int timeStep);
}
