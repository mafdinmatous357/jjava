package pole;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DisplayGraphics extends Canvas {
	public BufferedImage img;

	public void paint(Graphics g) {
		// BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		g.drawImage(img, 10, 10, null);
		g.drawString("Hello", 40, 40);
		setBackground(Color.WHITE);
		g.fillRect(130, 30, 100, 80);
		g.drawOval(30, 130, 50, 60);
		setForeground(Color.RED);
		g.fillOval(130, 130, 50, 60);
		g.drawArc(30, 200, 40, 50, 90, 60);
		g.fillArc(30, 130, 40, 50, 180, 40);
	}
}
