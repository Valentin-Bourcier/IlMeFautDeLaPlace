package ihm.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;

public class LaunchModel extends DefaultListModel<String> {

	private static final long serialVersionUID = 1L;

	private static LaunchModel instance;
	
	public static LaunchModel getModel() {
		if(instance == null) {
			instance = new LaunchModel();
		}
		instance.unserialize();
		return instance;
	}
	
	public void add(String aPath) {
		Path vPath = Paths.get(aPath);
		if(!this.contains(aPath) && Files.exists(vPath) && Files.isDirectory(vPath)) {
			this.addElement(aPath);			
		}
	}
	
	public void remove(int[] aList) {
		for (Integer vIndex : aList) {
			this.remove(vIndex);
		}
	}
	
	public void clean()
    {
		File file = new File(Settings.LAUNCH_PATH);
        if (file.exists())
        {
            file.delete();
        }
    }

	public void serialize() {
		try
        {
            FileOutputStream file = new FileOutputStream(Settings.LAUNCH_PATH, false);
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
        if (isEmpty() && Files.exists(Paths.get(Settings.LAUNCH_PATH)))
        {
            try
            {
                FileInputStream file = new FileInputStream(Settings.LAUNCH_PATH);
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
