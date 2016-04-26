import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {


	private static final int NBROFBALLS = 4;
	private final double areaWidth;
	private final double areaHeight;
	private double gravityConstant = 9.82;
	private List<Ball> myBalls;
	private double speedX = 5.0;



	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		this.myBalls = new List<>();
		initBalls();
	}

	@Override
	public void tick(double deltaT) {
		if (x < r || x > areaWidth - r) {
			vx *= -1;
		}
		if (y < r || y > areaHeight - r) {
			vy *= -1;
		}
		x += vx * deltaT;
		y += vy * deltaT;

	}


	/**
	 * Initializes the ball list
	 */
	private void initBalls() {
		for (int i = 0; i<NBROFBALLS; i++) {
			double startX = Random.rand()*areaWidth;
			double startY = Random.rand()*areaHeight;
			double vX = Random.rand()*speedX;

		}
	}


	@Override
	public List<Ellipse2D> getBalls() {
		return myBalls;
	}
}
