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
	private double maxSpeedX = 5.0;
	private static final double maxRadius = 2.0;
	private static final double density = 5.1;
	private final Random r;

  private boolean[][] collisionMatrix;


	public DummyModel(double width, double height) {
		r = new Random();
    collisionMatrix = new boolean[NBROFBALLS][NBROFBALLS];
		this.areaWidth = width;
		this.areaHeight = height;
		this.myBalls = new ArrayList<Ball>();
    for(int i=0; i<NBROFBALLS;i++){
      for(int j=0; j<NBROFBALLS;j++){
        collisionMatrix[i][j] = false;
      }
    }
		initBalls();
  }

	@Override
	public void tick(double deltaT) {
    for(int i=0; i<NBROFBALLS;i++){
      for(int j=0; j<NBROFBALLS;j++){
        collisionMatrix[i][j] = false;
      }
    }

		for (Ball ball : myBalls) {
			checkForCollisions(ball, deltaT);
		}

    for (Ball ball : myBalls) {
      ball.setvY(ball.getvY() - gravityConstant * deltaT);
      ball.move(ball.getvX() * deltaT, ball.getvY() * deltaT);
    }
	}

	private void checkForCollisions(Ball ball, double deltaT) {
    checkOtherBalls(ball, deltaT);
    checkWalls(ball);
  }

  private boolean checkColliding(Ball original){
    boolean colliding = false;
    for (Ball ball: myBalls) {
      if(!ball.equals(original)){
	      //check if colliding
	      if(colliding(ball, original)){
          colliding = true;
	      }
      }
    }
    return colliding;
  }

	private boolean checkOtherBalls(Ball original, double deltaT) {
    boolean colliding = false;
		for (Ball ball: myBalls) {
			if (!ball.equals(original)) {
				//check if colliding
				if(colliding(ball, original) && !collisionMatrix[myBalls.indexOf(ball)][myBalls.indexOf(original)]){
          colliding = true;
					//do collision
					collide(ball, original, deltaT);
          collisionMatrix[myBalls.indexOf(ball)][myBalls.indexOf(original)] = true;
          collisionMatrix[myBalls.indexOf(original)][myBalls.indexOf(ball)] = true;
				}else{
          collisionMatrix[myBalls.indexOf(ball)][myBalls.indexOf(original)] = false;
          collisionMatrix[myBalls.indexOf(original)][myBalls.indexOf(ball)] = false;
        }
			}
    }
		return colliding;
	}

	private boolean colliding(Ball a, Ball b) {
        double distance = Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY() - a.getY(),2));
        return (a.getRadius() + b.getRadius())
                >=
                distance;
	}

	private void collide(Ball a, Ball b, double deltaT) {

    double newVelAX = (a.getvX() * (a.getWeight() - b.getWeight())
											+ (2 * b.getWeight() * b.getvX())) / (a.getWeight() + b.getWeight());
    double newVelAY = (a.getvY() * (a.getWeight() - b.getWeight())
											+ (2 * b.getWeight() * b.getvY())) / (a.getWeight() + b.getWeight());

		double newVelBX = (b.getvX() * (b.getWeight() - a.getWeight())
											+ (2 * a.getWeight() * a.getvX())) / (b.getWeight() + a.getWeight());
    double newVelBY = (b.getvY() * (b.getWeight() - a.getWeight())
											+ (2 * a.getWeight() * a.getvY())) / (b.getWeight() + a.getWeight());

    a.setvX(newVelAX);
    a.setvY(newVelAY);
    b.setvX(newVelBX);
    b.setvY(newVelBY);

    double angleA = Math.atan2(newVelAY, newVelAX);
    double angleB = Math.atan2(newVelBY, newVelBX);
    double distance = Math.abs(a.getRadius() + b.getRadius() - Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY() - a.getY(),2)));
    a.move(Math.cos(angleA)*(distance/2), Math.sin(angleA)*(distance/2));
    b.move(Math.cos(angleB)*(distance/2), Math.sin(angleB)*(distance/2));
    System.out.println(
      "Total distance needed to move: " + distance + "\n "+myBalls.indexOf(a)+" moving in X: " + Math.cos(angleA)*(distance/2) + ", in Y: " + Math.sin(angleA)*(distance/2) +  "\n "+ myBalls.indexOf(b)+" moving in X: " + Math.cos(angleB)*(distance/2) + ", in Y: " + Math.sin(angleB)*(distance/2) + "\n Distance needed to move after: " + (a.getRadius() + b.getRadius() - Math.sqrt(Math.pow(b.getX()-a.getX(),2)+Math.pow(b.getY() - a.getY(),2)))
    );
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
  	double wall = 10;
		for (int i = 0; i<NBROFBALLS;i++) {
      double radius = r.nextDouble() * maxRadius + 0.5;
      double startX = radius + wall;
      wall = startX+radius;
      double startY = radius + (i%5)*5;
      double weight = radius * radius * 3.14 * density;
      double vX = r.nextDouble() * maxSpeedX;
      Ball ball = new Ball(radius, startX, startY, weight, vX);
      myBalls.add(ball);
		}
	}



	@Override
	public List<Ball> getBalls() {
		return myBalls;
	}
}
