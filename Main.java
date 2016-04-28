import java.awt.*;
import javax.swing.*;


public class Main {
	public static void main(String[] args){
    BouncingBalls appletClass = new BouncingBalls();

    JFrame frame = new JFrame();
    frame.setLayout(new GridLayout(1, 1));
    frame.add(appletClass);
    frame.setMinimumSize(new Dimension(1200, 1600));

    frame.setVisible(true);

    appletClass.init();
    appletClass.start();

	}
}
