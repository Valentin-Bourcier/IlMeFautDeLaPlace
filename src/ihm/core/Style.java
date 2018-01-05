package ihm.core;

import java.text.DecimalFormat;

import model.ServiceNode;

public class Style {

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
}
