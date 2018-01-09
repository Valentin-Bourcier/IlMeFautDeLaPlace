package ihm.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ihm.model.Settings;
import ihm.view.main.View;
import model.NodeFile;

public class NodeIcon extends JLabel implements View {

	private static final long serialVersionUID = 1L;

	private Dimension dimension;

	public NodeIcon(Dimension aDimension) {
		dimension = aDimension;
		render();
	}

	@Override
	public void setLayout() {
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public void load() {
		File vFile = new File(Settings.service.absolutePath());
		Icon vCurrentIcon = null;
		if (!Settings.service.isDirectory())
		{
			NodeFile vNodeFile = (NodeFile) Settings.service;
			if (vNodeFile.isThatKind("image"))
			{
				BufferedImage img;
				try
				{
					img = ImageIO.read(vFile);
					img = scale(img, dimension.width, dimension.height);
					vCurrentIcon = new ImageIcon(img);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else if (vNodeFile.isThatKind("video"))
			{
				vCurrentIcon = new ImageIcon("resources/video.png");
			}
			else if (vNodeFile.isThatKind("audio"))
			{
				vCurrentIcon = new ImageIcon("resources/audio.png");
			}
			else
			{
				vCurrentIcon = new ImageIcon("resources/file.png");
			}
		}
		else
		{
			vCurrentIcon = new ImageIcon("resources/folder.png");
		}
		setIcon(vCurrentIcon);

	}

	public static BufferedImage scale(BufferedImage source, double ratio) {
		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		return scale(source, w, h);
	}

	public static BufferedImage scale(BufferedImage source, int w, int h) {
		BufferedImage bi = getCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = source != null ? (double) w / source.getWidth() : w;
		double yScale = source != null ? (double) h / source.getHeight() : h;
		AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
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

}
