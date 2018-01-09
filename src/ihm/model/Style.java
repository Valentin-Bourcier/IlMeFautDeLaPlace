package ihm.model;

import java.awt.Color;
import java.text.DecimalFormat;

import model.ServiceNode;

public class Style {

	public static final int DEFAULT_MAIN_WIDTH = 1600;
	public static final int DEFAULT_MAIN_HEIGHT = 800;
	public static final int DEFAULT_TREE_WIDTH = 400;
	public static final int DEFAULT_TAB_WIDTH = 800;

	public static String printWeight(ServiceNode aNode) {

		String vPrintWeight = "";
		double vWeight = aNode.weight();
		DecimalFormat vFormatter = new DecimalFormat("0.00");

		if (vWeight > Math.pow(1024, 4))
		{
			vPrintWeight = vFormatter.format(vWeight / Math.pow(1024, 4)) + " TiB";
		}
		else if (vWeight > Math.pow(1024, 3))
		{
			vPrintWeight = vFormatter.format(vWeight / Math.pow(1024, 3)) + " GiB";
		}
		else if (vWeight > Math.pow(1024, 2))
		{
			vPrintWeight = vFormatter.format(vWeight / Math.pow(1024, 2)) + " MiB";
		}
		else if (vWeight > 1024)
		{
			vPrintWeight = vFormatter.format(vWeight / 1024) + " KiB";
		}
		else
		{
			vPrintWeight = (int) vWeight + " B";
		}

		return vPrintWeight;
	}

	public static Color getColor(double aPercent) {
		if (aPercent < 0.1)
		{
			return new Color(236, 240, 241);
		}
		else if (aPercent < 0.2)
		{
			return new Color(52, 152, 219);
		}
		else if (aPercent < 0.3)
		{
			return new Color(0, 150, 136);
		}
		else if (aPercent < 0.4)
		{
			return new Color(139, 195, 74);
		}
		else if (aPercent < 0.5)
		{
			return new Color(255, 235, 59);
		}
		else if (aPercent < 0.6)
		{
			return new Color(230, 126, 34);
		}
		else if (aPercent < 0.7)
		{
			return new Color(211, 84, 0);
		}
		else if (aPercent < 0.85)
		{
			return new Color(231, 76, 60);
		}
		else
		{
			return new Color(192, 57, 43);
		}

	}
}
