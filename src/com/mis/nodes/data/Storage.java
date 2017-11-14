package com.mis.nodes.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {
	public static String path;
	public static NDict Players;
	public static NDict Factions;
	
	public static void init_storage(String path) {
		Storage.path = path; try {
			Players = load_target("players");
			Factions = load_target("factions");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void dump_storage() {
		try {
			save_target(Players,"players");
			save_target(Factions,"factions");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public static NDict load_target (String target) throws IOException, ClassNotFoundException {
		File fileObj = new File(path+"/save/"+target+".homo"); fileObj.getParentFile().mkdirs();
		if(!fileObj.exists()) return new NDict();
		FileInputStream fileIn = new FileInputStream(fileObj);
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    NDict Niggers = (NDict) in.readObject();
	    in.close(); fileIn.close(); return Niggers;
	}
	
	public static void save_target (NDict Jigaboos, String target) throws IOException, ClassNotFoundException {
		File fileObj = new File(path+"/save/"+target+".homo"); fileObj.getParentFile().mkdirs();
		if(!fileObj.exists()) fileObj.createNewFile();
		FileOutputStream fileOut = new FileOutputStream(fileObj);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(Jigaboos); out.close(); fileOut.close();
	}
	
}
