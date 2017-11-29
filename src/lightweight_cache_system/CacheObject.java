package lightweight_cache_system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 
 * @author valentin Cette porte classe les informations qui seront mises en
 *         cache par chaque node ie la date de modif et le hash
 */

public class CacheObject implements Serializable {
	protected String hash;
	protected long lasteModificationDate;

	public CacheObject(String h, long lmd) {
		hash = h;
		lasteModificationDate = lmd;
	}

	public String getHash() {
		return hash;
	}

	public long getLasteModificationDate() {
		return lasteModificationDate;
	}

	/*public void serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("tmp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (IOException i) {
			i.printStackTrace();
	
		} finally {
			System.out.println("Serialisation");
			System.out.println(this.hash + " ===> " + this.lasteModificationDate);
			System.out.println("Fin Serialization");
	
		}
	}*/

	public CacheObject deserialize() {
		CacheObject cd = null;
		try {
			FileInputStream fileIn = new FileInputStream("tmp.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			cd = (CacheObject) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("CacheObject class not found");
			c.printStackTrace();
			return null;
		}
		return cd;
	}

}
