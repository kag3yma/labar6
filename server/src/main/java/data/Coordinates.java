package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x;
    private float y;

    public Coordinates(Float x, float y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){}

    public Float getX(){return x;}
    public void setX(Float x){this.x = x;}
    public float getY(){return y;}
    public void setY(float y){this.y = y;}
    @Override
    public String toString() {
        return "X:" + x + " Y:" + y;
    }
}
