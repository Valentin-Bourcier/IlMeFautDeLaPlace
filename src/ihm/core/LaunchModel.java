package ihm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;

public class LaunchModel extends DefaultListModel<String> {

	private static final long serialVersionUID = 1L;

	private static LaunchModel instance;
	private String rootPath;
	public static String LOCATION = "launch.cache";
	
	public static LaunchModel getModel() {
		if(instance == null) {
			instance = new LaunchModel();
		}
		return instance;
	}
	
	public void add(String aPath) {
		if(!this.contains(aPath) && Files.isDirectory(Paths.get(aPath))) {
			this.addElement(aPath);			
		}
	}
	
	public void setRootPath(String aPath) {
		if (Files.isDirectory(Paths.get(aPath))) {
			rootPath = aPath;
		}
	}
	
	public String getRootPath() {
		return this.rootPath;
	}
	
	public void remove(int[] aList) {
		for (Integer vIndex : aList) {
			this.remove(vIndex);
		}
	}
	
	public void clean()
    {
		File file = new File(LOCATION);
        if (file.exists())
        {
            file.delete();
        }
    }

	public void serialize() {
		try
        {
            FileOutputStream file = new FileOutputStream(LOCATION, false);
            ObjectOutputStream stream = new ObjectOutputStream(file);
            stream.writeObject(instance);
            stream.close();
            file.close();
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
	}
	
	public void unserialize()
    {
        if (!isEmpty())
        {
            try
            {
                FileInputStream file = new FileInputStream(LOCATION);
                ObjectInputStream stream = new ObjectInputStream(file);
                instance = (LaunchModel) stream.readObject();
                stream.close();
                file.close();
            }
            catch (IOException error)
            {
                error.printStackTrace();
            }
            catch (ClassNotFoundException error)
            {
                error.printStackTrace();
            }
        }
    }
}
