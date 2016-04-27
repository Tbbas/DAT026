import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Created by vilddjur on 26/04/16.
 */
public class Ball {
    private final double radius;
    private final double weight;
    private double x;
    private double y;
    private double vX;
    private double vY;
    private Color color;
    private static final Random rand = new Random();

    public Ball(double radius, double startX, double startY, double weight, double vX) {
        this.radius = radius;
        this.x = startX;
        this.y = startY;
        this.weight = weight;
        this.vX = vX;
        this.vY = 0;

        color = new Color(255,0,0);//new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public double getWeight() {
        return weight;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getvX() {
        return vX;
    }
    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }
    public void move(double x, double y){
        this.x += x;
        this.y += y;
    }

    public Ellipse2D getDrawable() {
        return new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    public Vector getVect(){
        return new Vector(vX,vY,x,y);
    }

    public double getSpeed() {
        return Math.sqrt(Math.pow(vX, 2) + Math.pow(vY,2));
    }
}
