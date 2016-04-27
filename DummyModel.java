import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {


	private static final int NBROFBALLS = 10;
	private final double areaWidth;
	private final double areaHeight;
	private double gravityConstant = 9.82;
	private ArrayList<Ball> myBalls;
	private double speedX = 5.0;
	private static final double maxRadius = 2.0;
	private static final double density = 5.1;
    private boolean[][] collisionMatrix;
	private final Random r;


	public DummyModel(double width, double height) {
		r = new Random();
        collisionMatrix = new boolean[NBROFBALLS][NBROFBALLS];
		this.areaWidth = width;
		this.areaHeight = height;
		this.myBalls = new ArrayList<Ball>();
        for(int i=0;i<NBROFBALLS;i++){
            for(int j=0;j<NBROFBALLS;j++){
                collisionMatrix[i][j]=false;
            }
        }
		initBalls();
    }

	@Override
	public void tick(double deltaT) {
        for (Ball ball : myBalls) {
            ball.setvY(ball.getvY() - gravityConstant * deltaT);
            ball.move(ball.getvX() * deltaT, ball.getvY() * deltaT);
        }
		for (Ball ball : myBalls) {
			checkForCollisions(ball);
		}
	}

	private void checkForCollisions(Ball ball) {
		checkWalls(ball);
        checkOtherBalls(ball);
	}

	private boolean checkOtherBalls(Ball original) {
        boolean colliding = false;
        for(int i=0;i<NBROFBALLS;i++){
            for(int j=0;j<NBROFBALLS;j++){
                collisionMatrix[i][j]=false;
            }
        }
		for (Ball ball: myBalls) {
			if(!ball.equals(original)){
				//check if colliding
				if(colliding(ball, original) && !collisionMatrix[myBalls.indexOf(original)][myBalls.indexOf(ball)] && !collisionMatrix[myBalls.indexOf(ball)][myBalls.indexOf(original)]){
                    colliding = true;
					//do collision
					collide(ball, original);
				}
			}
        }
        if(colliding){
            original.setColor(new Color(0, 255, 0));
        }else{
            original.setColor(new Color(255, 0, 0));
        }
        return colliding;
	}

	private boolean colliding(Ball a, Ball b) {
        double distance = Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY() - a.getY(),2));
        return (a.getRadius() + b.getRadius())
                >=
                distance;
	}

	private void collide(Ball a, Ball b) {
        double I = a.getWeight()*a.getSpeed() + b.getWeight()*b.getSpeed();
        double R = a.getSpeed() - b.getSpeed();
        double originX = a.getX();
        double originY = a.getY();
        double axisDir = (a.getY() - b.getY())/(a.getX() - a.getX());
        double newVelAX = (a.getvX() * (a.getWeight() - b.getWeight()) + (2 * b.getWeight() * b.getvX())) / (a.getWeight() + b.getWeight());
        double newVelAY = (a.getvY() * (a.getWeight() - b.getWeight()) + (2 * b.getWeight() * b.getvY())) / (a.getWeight() + b.getWeight());
        double newVelBX = (b.getvX() * (b.getWeight() - a.getWeight()) + (2 * a.getWeight() * a.getvX())) / (b.getWeight() + a.getWeight());
        double newVelBY = (b.getvY() * (b.getWeight() - a.getWeight()) + (2 * a.getWeight() * a.getvY())) / (b.getWeight() + a.getWeight());

        a.setvX(newVelAX);
        a.setvY(newVelAY);
        b.setvX(newVelBX);
        b.setvY(newVelBY);

        collisionMatrix[myBalls.indexOf(a)][myBalls.indexOf(b)] = true;
        collisionMatrix[myBalls.indexOf(b)][myBalls.indexOf(a)] = true;
    }

	private boolean checkWalls(Ball ball) {
        boolean colliding = false;
		if (ball.getX() <= ball.getRadius()) {
            ball.setvX(ball.getvX()*-1);
			ball.setX(ball.getRadius());
            colliding = true;
        }
		if(ball.getX() >= areaWidth - ball.getRadius()) {
			ball.setvX(ball.getvX()*-1);
			ball.setX(areaWidth-ball.getRadius());
			colliding = true;

		}
		if (ball.getY()-ball.getRadius() <= 0) {
            ball.setvY(ball.getvY()*-1);
			ball.setY(ball.getRadius());
            colliding =true;
        }
		if(ball.getY() + ball.getRadius() >= areaHeight) {
			ball.setvY(ball.getvY()*-1);
			ball.setY(areaHeight-ball.getRadius());
		}
        return colliding;
	}


	/**
	 * Initializes the ball list
	 */
	private void initBalls() {
        boolean colliding=true;
		for (int i = 0; i<NBROFBALLS;i++) {
            double radius = r.nextDouble() * maxRadius + 0.5;
            double startX = r.nextDouble() * (areaWidth - 40) + 20;
            double startY = r.nextDouble() * (areaWidth - 40) + 20;
            double weight = radius * radius * 3.14 * density;
            double vX = r.nextDouble() * speedX;
            Ball ball = new Ball(radius, startX, startY, weight, vX);
            myBalls.add(ball);
		}
	}



	@Override
	public List<Ball> getBalls() {
		return myBalls;
	}
}
