package com.nodes.event;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NResourceSchedule
{
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void resourceTimer()
	{
		final Runnable resourceSpawn = new Runnable()
		{
			public void run()
			{
				//TODO: the minutely function
			}
		};
		
		scheduler.scheduleAtFixedRate(resourceSpawn, 1, 1, TimeUnit.MINUTES);
	}
}
