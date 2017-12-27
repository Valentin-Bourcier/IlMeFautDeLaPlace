package ihm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;

public class LaunchManager extends DefaultListModel{

	private static final long serialVersionUID = 1L;

	private static LaunchManager instance;
	public static String LOCATION = "launch.cache";
	private Set<String> paths;
	private ArrayList<ListDataListener> listeners;
	
	public static LaunchManager getManager() {
		if(instance == null) {
			instance = new LaunchManager();
		}
		return instance;
	}

	public boolean isSet() {
		return paths != null;
	}
	
	public void addPath(String aPath) {
		if(!isSet()) {
			paths = new HashSet<>();
		}
		paths.add(aPath);
		this.addElement(aPath);
	}
	
	public void removePaths(List<String> aPaths) {
		if(!isSet()) {
			for (String vPath : aPaths) {
				paths.remove(vPath);
			}
		}
	}
	
	public String[] getPaths() {
		if(!isSet()) {
			return new String[0];
		}
		return (String[]) this.paths.toArray();			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void unserialize()
    {
        if (isSet())
        {
            try
            {
                FileInputStream file = new FileInputStream(LOCATION);
                ObjectInputStream stream = new ObjectInputStream(file);
                paths = (Set) stream.readObject();
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
	
	public void clean()
    {
        if (isSet())
        {
            new File(LOCATION).delete();
        }
    }

	@Override
	public void addListDataListener(ListDataListener l) {
		if(listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(l);
	}

	@Override
	public Object getElementAt(int index) {
		return getPaths()[index];
	}

	@Override
	public int getSize() {
		if(!isSet()) {
			return 0;
		}
		return paths.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		if(listeners != null) {
			listeners.remove(l);			
		}
	}
}
