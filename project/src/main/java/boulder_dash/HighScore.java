package boulder_dash;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class HighScore {
    public final SimpleIntegerProperty value;
    public final SimpleStringProperty player;

    public HighScore(int value, String player){
        this.value = new SimpleIntegerProperty();
        this.value.setValue(value);

        this.player = new SimpleStringProperty();
        this.player.setValue(player);
    }

    public String getPlayer(){
        return player.get();
    }

    public Integer getValue(){
        return value.get();
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getValue());
    }
}
