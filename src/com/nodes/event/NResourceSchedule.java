package com.nodes.event;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NResourceSchedule
{
	private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
	public static void resourceTimer()
	{
		final Runnable resourceSpawn = new Runnable()
		{
			public void run()
			{
				//TODO: get all resources with periods that overlap with current minute, apply to all nodes with those resources
			}
		};
		schedule.scheduleAtFixedRate(resourceSpawn, 1, 1, TimeUnit.MINUTES);
	}
}
