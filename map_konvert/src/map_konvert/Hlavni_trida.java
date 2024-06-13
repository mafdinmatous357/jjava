package map_konvert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Hlavni_trida {
	
	public static ArrayList<int[]> rearrange(ArrayList<int[]> src) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int point[] = src.remove(0);
		result.add(point);
		int length = 0;
		while ((length = src.size()) > 0) {
			int best_distance = 2147483647;
			int best_index = 0;
			for (int i = 0; i < length; i++) {
				int item[] = src.get(i);
				int distance = (int)Math.pow(point[0] - item[0], 2) + (int)Math.pow(point[1] - item[1], 2);
				if (distance < best_distance) {
					best_distance = distance;
					best_index = i;
				}
			}
			point = src.remove(best_index);
			result.add(point);
		}
		return result;
	}

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
			ArrayList<int[]> loaded_red = new ArrayList<int[]>();
			ArrayList<int[]> loaded_blue = new ArrayList<int[]>();
			// Loop through each pixel
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					// Get color data of the current pixel
					int pixel = image.getRGB(x, y);

					// Extract the color components from the pixel
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = pixel & 0xff;
					if ((red >= 128) && (green <= 128) && (blue <= 128)) {
						int points[] = new int[2];
						points[0] = x;
						points[1] = y;
						loaded_red.add(points);
					}
					else if ((red <= 128) && (green <= 128) && (blue >= 128)) {
						int points[] = new int[2];
						points[0] = x;
						points[1] = y;
						loaded_blue.add(points);
					}
					// Output the color data
					// System.out.println("RGB at (" + x + "," + y + "): (" + red + ", " + green + ", " + blue + ")");
				}
			}
			ArrayList<int[]> ordered_red = rearrange(loaded_red);
			ArrayList<int[]> ordered_blue = rearrange(loaded_blue);
			float ratio = 0;
			int short_count = 0;
			ArrayList<int[]> shorter_edge = null;
			ArrayList<int[]> longer_edge = null;
			if (ordered_red.size() > ordered_blue.size()) {
				ratio = ordered_red.size() / ordered_blue.size();
				longer_edge = ordered_red;
				shorter_edge = ordered_blue;
				short_count = ordered_blue.size();
			}
			else {
				ratio = ordered_blue.size() / ordered_red.size();
				longer_edge = ordered_blue;
				shorter_edge = ordered_red;
				short_count = ordered_red.size();
			}
			ArrayList<int[]> souradnice = new ArrayList<int[]>();
			int coord_count = 0;
			int long_count = 0;
			while (shorter_edge.size() > 0) {
				coord_count ++;
				int long_take = (int)(coord_count * ratio) - long_count;
				long_count += long_take;
				int point[] = shorter_edge.remove(0);
				for (int c = 0; c < long_take; c++) {
					int best_distance = 2147483647;
					int best_index = 0;
					for (int i = 0; i < longer_edge.size(); i++) {
						int item[] = longer_edge.get(i);
						int distance = (int)Math.pow(point[0] - item[0], 2) + (int)Math.pow(point[1] - item[1], 2);
						if (distance < best_distance) {
							best_distance = distance;
							best_index = i;
						}
					}
					int item[] = longer_edge.remove(best_index);
					souradnice.add(item);
				}
				souradnice.add(point);
			}
			for (int i = 0; i < souradnice.size(); i++) {
				if (je_dalsi) {
					souradnice_x += "\n";
					souradnice_y += "\n";
				}
				souradnice_x += souradnice.get(i)[0];
				souradnice_y += souradnice.get(i)[1];
				je_dalsi = true;
			}
			// System.out.println(souradnice_x);
			// System.out.println(souradnice_y);
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
