import java.io.BufferedInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Main {
	
	/** 
	 * This method put all files in a list from a root directory of a file tree
	 * @param filename, the root directory/file of the arborescence to explore
	 * @return ArrayList containing every file in the arborescence since filename
	 */
	
	public static ArrayList<File> listAllFiles(String filename){
		ArrayList<File> allFiles = new ArrayList<File>();
		File startingDirectory = new File (filename);
		
		//Queue to explore every discovered directory, kind of FIFO
		LinkedList<File> filesToExplore = new LinkedList<File>();
		//temporary array to store current directory content
		File[] filesOfCurrentDirectory ;
		
		if(startingDirectory.isDirectory()){
			//System.out.println("starting file is directory");
			filesToExplore.add(startingDirectory);
			while (!filesToExplore.isEmpty()) {
				File currentDirectory = filesToExplore.removeFirst();				
				filesOfCurrentDirectory = currentDirectory.listFiles();

				for (File currentFile : filesOfCurrentDirectory){									
					if(currentFile.isDirectory())
						filesToExplore.add(currentFile);
					
					allFiles.add(currentFile);	
				}
			}
		}
		
		else 
			allFiles.add(startingDirectory);
		
		return allFiles;
	}
	
	/**
	 * quick and dirty way to print an ArrayList<File> only used for debug purpose
	 * @param files the ArrayList<File> to print
	 * 
	 */
	public static String printArrayOfFile(ArrayList<File> files) {
		//System.out.println(files.size());
		for (File currentFile : files) {
			System.out.println("filename: "+currentFile.getName()+" path: "+currentFile.getAbsolutePath());
		}
		
		return null;
	}
	
	
	public static HashMap<String, File> hashFiles2(ArrayList<File> files){
		HashMap<String,File> hashedFiles = new HashMap<String,File>();
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("MD5");
			for(File currentFile : files) {
				//Path path = Paths.get(currentFile.getAbsolutePath());
				if (currentFile.isFile()){
					try {
						DigestInputStream dis = new DigestInputStream(new FileInputStream(currentFile),md);
						while(dis.read(new byte[8192])>0);
						byte[] hash = md.digest();
						//System.out.println(hash.toString());
						dis.close();
						hashedFiles.put(new String(hash), currentFile);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
						
				}
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hashedFiles;
	}
	
	
	public static HashMap<byte[], File> hashFiles(ArrayList<File> files){
		HashMap<byte[],File> hashedFiles = new HashMap<byte[],File>();
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[8192];
			int byteLus;		
			
			for(File currentFile : files) {
				//System.out.println(currentFile.getName());
				if(currentFile.isFile()) {
					BufferedInputStream bis;
					try {
						bis = new BufferedInputStream(new FileInputStream(currentFile));
						while((byteLus = bis.read(buffer))> 0) {
							md.update(buffer, 0, byteLus);
						}
						
						byte[] hash = md.digest();
						hashedFiles.put(hash, currentFile);
				//		System.out.println(currentFile.getName()+" "+hash);
						bis.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedFiles;
	}
	
	public static void printHashMap(HashMap<byte[], File> map) {
		for(Map.Entry<byte[],File> entry : map.entrySet()) {
			System.out.println(new String(entry.getKey())+" "+entry.getValue().getName());
		}
	}
	
	/*
	public static void main(String[] args) {
		ArrayList<File> allFiles = listAllFiles("/home/valentin/Documents");
		//printArrayOfFile(allFiles);
		long start = System.nanoTime();
		HashMap<byte[], File> hash = hashFiles(allFiles);
		long end = System.nanoTime();
		System.out.println(hash.size()+" "+(end-start));
		
		start = System.nanoTime();
		HashMap<String, File>hash2 = hashFiles2(allFiles);
		end = System.nanoTime();
		System.out.println(hash2.size()+" "+(end-start));
		//printHashMap(hash);
		
	}*/
	

}
