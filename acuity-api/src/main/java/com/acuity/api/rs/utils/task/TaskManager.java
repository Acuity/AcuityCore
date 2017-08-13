package com.acuity.api.rs.utils.task;

import com.acuity.api.rs.utils.Delay;
import com.acuity.api.rs.utils.task.login.LoginTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Created by Eclipseop.
 * Date: 7/8/2017.
 */
public class TaskManager {

	private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

	private final ArrayList<Task> tasks = new ArrayList<>();
	private Thread thread;
	private volatile boolean running = true;

	public TaskManager() {
		tasks.add(new LoginTask());
	}

	public void start() {
		thread = new Thread(() -> {
			while (running) {
				for (Task task : tasks) {
					try {
						if (task.validate()) {
							final int loop = task.loop();

							if (loop < 0) {
								continue;
							}
							Delay.delay(loop);
						}
					} catch (Exception e) {
						logger.error("Exception thrown during " + task.getClass().getName(), e);
					}
				}
				Delay.delay(100);
			}
		});

		thread.start();
	}

	public void stop() {
		running = false;
	}
}
