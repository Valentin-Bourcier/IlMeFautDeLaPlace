package convert_to_default_mutable_tree_node;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import model.ServiceNode;

public abstract class AbstractStrategyConvert implements Serializable {
	ServiceNode node;

	/**
	 * 
	 * @param pere
	 * @return
	 */
	public abstract DefaultMutableTreeNode convertToDefaultMutableTreeNode(DefaultMutableTreeNode pere);

	public void serialize() {
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

}
