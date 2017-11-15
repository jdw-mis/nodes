package com.mis.nodes.data;

import java.util.HashMap;
import java.util.UUID;

import com.mis.nodes.Nodes;

public class NDict extends HashMap<UUID, NData>
{
	private static final long serialVersionUID = 514582635581890154L;
	
	public static class BackgroundTask extends Thread {
		public BackgroundTask(int s) {t=s;}
		private int t;
		public void run() {
			while(true) {
		        try {Thread.sleep(1000*t);} 
		        catch (InterruptedException e) {
		        	Nodes.log.info("Terminated Background Thread...");
		        	} Storage.dump_storage();
		        Nodes.log.info("Saved NiggerCraft Status...");
		    }
		}
	}
}
