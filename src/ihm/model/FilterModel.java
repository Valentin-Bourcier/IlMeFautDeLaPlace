package ihm.model;

import java.util.ArrayList;

public class FilterModel {

	private ArrayList<CheckableItem> model;

	public FilterModel() {
		model = new ArrayList<>();
		initModel();
	}

	public void initModel() {

		model.add(new CheckableItem("avi", false));
		model.add(new CheckableItem("bak", false));
		model.add(new CheckableItem("bat", false));
		model.add(new CheckableItem("bin", false));
		model.add(new CheckableItem("bmp", false));
		model.add(new CheckableItem("class", false));
		model.add(new CheckableItem("com", false));
		model.add(new CheckableItem("cpp", false));
		model.add(new CheckableItem("css", false));
		model.add(new CheckableItem("dat", false));
		model.add(new CheckableItem("dbf", false));
		model.add(new CheckableItem("dll", false));
		model.add(new CheckableItem("exe", false));
		model.add(new CheckableItem("flv", false));
		model.add(new CheckableItem("gif", false));
		model.add(new CheckableItem("htm", false));
		model.add(new CheckableItem("html", false));
		model.add(new CheckableItem("ico", false));
		model.add(new CheckableItem("img", false));
		model.add(new CheckableItem("ini", false));
		model.add(new CheckableItem("iso", false));
		model.add(new CheckableItem("java", false));
		model.add(new CheckableItem("jpe", false));
		model.add(new CheckableItem("jpeg", false));
		model.add(new CheckableItem("jpg", false));
		model.add(new CheckableItem("log", false));
		model.add(new CheckableItem("mov", false));
		model.add(new CheckableItem("m3u", false));
		model.add(new CheckableItem("mp2", false));
		model.add(new CheckableItem("mp3", false));
		model.add(new CheckableItem("mp4", false));
		model.add(new CheckableItem("mpg", false));
		model.add(new CheckableItem("msi", false));
		model.add(new CheckableItem("old", false));
		model.add(new CheckableItem("pdf", false));
		model.add(new CheckableItem("php", false));
		model.add(new CheckableItem("png", false));
		model.add(new CheckableItem("ppt", false));
		model.add(new CheckableItem("psf", false));
		model.add(new CheckableItem("rar", false));
		model.add(new CheckableItem("reg", false));
		model.add(new CheckableItem("rtf", false));
		model.add(new CheckableItem("shc", false));
		model.add(new CheckableItem("sql", false));
		model.add(new CheckableItem("swf", false));
		model.add(new CheckableItem("swp", false));
		model.add(new CheckableItem("sys", false));
		model.add(new CheckableItem("tar", false));
		model.add(new CheckableItem("tgz", false));
		model.add(new CheckableItem("theme", false));
		model.add(new CheckableItem("thm", false));
		model.add(new CheckableItem("tmp", false));
		model.add(new CheckableItem("ttf", false));
		model.add(new CheckableItem("txt", false));
		model.add(new CheckableItem("url", false));
		model.add(new CheckableItem("vbs", false));
		model.add(new CheckableItem("wav", false));
		model.add(new CheckableItem("wma", false));
		model.add(new CheckableItem("xls", false));
	}

	public CheckableItem[] getModel() {
		CheckableItem[] vModel = new CheckableItem[model.size()];
		for (int i = 0; i < model.size(); i++)
		{
			vModel[i] = model.get(i);
		}
		return vModel;
	}
}
