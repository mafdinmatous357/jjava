package map_konvert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Hlavni_trida {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Read the image file
			BufferedImage image = ImageIO.read(new File("/home/nxa17274/Pictures/trat.jpg"));

			String souradnice_x = "";
			String souradnice_y = "";
			Boolean je_dalsi = false;
			// Get image dimensions
			int width = image.getWidth();
			int height = image.getHeight();

			// Loop through each pixel
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					// Get color data of the current pixel
					int pixel = image.getRGB(x, y);

					// Extract the color components from the pixel
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = pixel & 0xff;
					if ((red != 255) || (green != 255) | (blue != 255)) {
						if (je_dalsi) {
							souradnice_x += ",";
							souradnice_y += ",";
						}
						souradnice_x += x;
						souradnice_y += y;
						je_dalsi=true;
					}
					// Output the color data
					// System.out.println("RGB at (" + x + "," + y + "): (" + red + ", " + green + ", " + blue + ")");
				}
			}
			System.out.println(souradnice_x);
			System.out.println(souradnice_y);
			FileWriter pos_x = new FileWriter("/home/nxa17274/Pictures/trat_x.txt");
			FileWriter pos_y = new FileWriter("/home/nxa17274/Pictures/trat_y.txt");
			pos_x.write(souradnice_x);
			pos_y.write(souradnice_y);
			pos_x.close();
			pos_y.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
