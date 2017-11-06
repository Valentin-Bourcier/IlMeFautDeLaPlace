package v2;

import java.io.File;

public interface MyNodeInterface extends ServiceNode {
	public void computHash();

	public String computeExtension();

	public String[] extension();

	public MyNodeInterface createINode(File f);

	public boolean containsOneOfThose(String[] filtres);

	public boolean isThatKind(String kind);

	public void addSon(MyNodeInterface node);

	public MyNodeInterface clone();

	public void effectiveFilter(String[] filtres);
}
