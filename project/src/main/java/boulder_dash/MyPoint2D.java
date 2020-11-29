package boulder_dash;

import javafx.geometry.Point2D;

import java.io.IOException;
import java.io.Serializable;

public class MyPoint2D  implements Serializable {
    private Point2D myPoint;

    public MyPoint2D(double x, double y){
        myPoint = new Point2D(x,y);
    }

    public MyPoint2D(Point2D point){
        myPoint = point;
    }

    public Point2D getPoint(){
        return myPoint;
    }

    public void setPoint(Point2D point){
        myPoint = point;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeDouble(myPoint.getX());
        out.writeDouble(myPoint.getY());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        myPoint = new Point2D(in.readDouble(), in.readDouble()) ;
    }
}
