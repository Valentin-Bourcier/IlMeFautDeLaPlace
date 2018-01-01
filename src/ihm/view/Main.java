package ihm.view;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static BufferedImage scale(BufferedImage source, double ratio) {
		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		return scale(source, w, h);
	}
	
	public static BufferedImage scale(BufferedImage source, int w, int h) {
		BufferedImage bi = getCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = source != null ? (double) w / source.getWidth(): w ;
		double yScale = source != null ? (double) h / source.getHeight() : h;
		AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}
	
	public static BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}
	
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		  File f = new File("/home/valentin/IMFDLP/Duplicates/duplicated-file1");
//		  BufferedImage img = ImageIO.read(f); // load image

		  //Quality indicate that the scaling implementation should do everything
		   // create as nice of a result as possible , other options like speed
		   // will return result as fast as possible
		//Automatic mode will calculate the resultant dimensions according
		//to image orientation .so resultant image may be size of 50*36.if you want
		//fixed size like 50*50 then use FIT_EXACT
		//other modes like FIT_TO_WIDTH..etc also available.

		   //convert bufferedImage to outpurstream 
//		  ByteArrayOutputStream os = new ByteArrayOutputStream();
//		  ImageIO.write(img,"jpg",os);
		  
		  
		  //or wrtite to a file
		  
		  File f2 = new File("/home/valentin/IMFDLP/background-thumb.png");
		   
		  
		  System.out.println("time is : " +(System.currentTimeMillis()-startTime));
	}
	
}
