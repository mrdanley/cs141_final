package edu.cpp.cs.cs141.final_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class IO {
	/**
	 * Loads a file from the filesystem
	 * 
	 * @param fileName url of the file
	 * @return serialized file {@link Object}
	 */
	public synchronized static Object load(String fileName){
		ObjectInputStream inp = null;
		final String loadFile= fileName.matches(".*\\.dat$") ? fileName : fileName+".dat";
		
		Object returns = null;
		try {
			final File file = new File(loadFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    returns = inp.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(">File not found ("+ loadFile +")");
		} catch (IOException e) {
			e.printStackTrace();
			returns = null;
			System.err.println(">Data corrupt ("+ loadFile +")");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			returns = null;
		} finally {
			if(inp != null) {
				try {
					inp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returns;
	}
	
	/**
	 * Saves an {@link Object} to the filesystem
	 * 
	 * @param saveData data to save
	 * @param fileName destination filename
	 * @return successful write
	 */
	public synchronized static boolean save(Object saveData, String fileName) {
		final String saveFile= fileName.matches(".*\\.dat$") ? fileName : fileName+".dat";
		
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(saveFile));
			out.writeObject(saveData);
			System.out.println(">Saved "+saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/**
	 * Lists all files in current directory and returns those that end with ".dat"
	 * @return array of file names
	 */
	public synchronized static String[] listFiles() {
		File[] list = new File(".").listFiles();
		String names = "";
		for (File file : list) {
			if (file.getName().matches(".*dat$")) {
				names+=file.getName()+" ";
			}
		}
		return names.trim().split(" ");
	}
}
