package ihm.model;

public class CheckableItem {
	public final String text;
	public boolean selected;

	protected CheckableItem(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
	}

	@Override
	public String toString() {
		return text;
	}
}