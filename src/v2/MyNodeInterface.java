package v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface MyNodeInterface extends ServiceNode, Cloneable, Serializable {
	public void computHash();

	public MyNodeInterface createINode(File f, ServiceNode pere);

	//DebutFiltres
	public String computeFileType();

	public String[] containedTypes();

	public MyNodeInterface clone(MyNodeInterface pere);

	public void effectiveFilter(String[] filtres);

	public boolean containsOneOfThose(String[] filtres);

	public boolean isThatKind(String kind);
	//FinFiltres

	public void addSon(MyNodeInterface node);

	default public void serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("tmp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (IOException i) {
			i.printStackTrace();

		} finally {

		}
	}

	public MyNodeInterface deserialize();

	default public int getNbNode() {
		return 1;
	}

}