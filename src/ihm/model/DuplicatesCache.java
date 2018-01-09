package ihm.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class DuplicatesCache implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DuplicatesCache instance;
	private HashMap<String, Integer> duplicates;

	private DuplicatesCache() {
		duplicates = new HashMap<>();
	}

	public static DuplicatesCache getCache() {
		if (instance == null)
		{
			instance = new DuplicatesCache();
		}
		instance.unserialize();
		return instance;
	}

	public void add(String aPath, Integer aNbOfDuplicates) {
		duplicates.put(aPath, aNbOfDuplicates);
	}

	public void remove(String aPath) {
		duplicates.remove(aPath);
	}

	public Integer getDuplicatesNumber(String aPath) {
		return duplicates.get(aPath);
	}

	public void clean() {
		File file = new File(Settings.DUPLICATES_CACHE_PATH);
		if (file.exists())
		{
			file.delete();
		}
	}

	public boolean isEmpty() {
		return duplicates.isEmpty();
	}

	public void serialize() {
		try
		{
			FileOutputStream file = new FileOutputStream(Settings.DUPLICATES_CACHE_PATH, false);
			ObjectOutputStream stream = new ObjectOutputStream(file);
			stream.writeObject(duplicates);
			stream.close();
			file.close();
		}
		catch (IOException error)
		{
			error.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void unserialize() {
		if (isEmpty() && Files.exists(Paths.get(Settings.DUPLICATES_CACHE_PATH)))
		{
			try
			{
				FileInputStream file = new FileInputStream(Settings.DUPLICATES_CACHE_PATH);
				ObjectInputStream stream = new ObjectInputStream(file);
				duplicates = (HashMap<String, Integer>) stream.readObject();
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
