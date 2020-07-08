package com.zzzyt.jade.game.task;

import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.Task;

public class Plural implements Task {

	private Array<Task> tasks;
	private int currentTask;
	private int loopCount;
	private int loopCounter;
	private boolean firstTime;

	public Plural() {
		this(1);
	}

	public Plural(int loopCount) {
		this.loopCount = loopCount;
	}

	public Array<Task> getTasks() {
		return tasks;
	}

	public Task getCurrentTask() {
		return tasks.get(currentTask);
	}

	public Plural add(Task task) {
		tasks.add(task);
		return this;
	}

	public Plural remove(Task task) {
		tasks.removeValue(task, true);
		return this;
	}

	public Plural setLoopCount(int loopCount) {
		this.loopCount = loopCount;
		return this;
	}

	public int getLoopCount() {
		return loopCount;
	}

	@Override
	public boolean isFinished() {
		return loopCounter >= loopCount;
	}

	@Override
	public void update(int frame) {
		if (!isFinished()) {
			if (firstTime) {
				getCurrentTask().init();
				firstTime = false;
			}
			while (true) {
				getCurrentTask().update(frame);
				if (getCurrentTask().isFinished()) {
					currentTask++;
					System.out.println(getClass().getName() + ":" + currentTask);
					if (currentTask >= tasks.size) {
						loopCounter++;
						System.out.println(getClass().getName() + ":" + loopCounter);
						if (loopCounter >= loopCount) {
							break;
						}
						currentTask = 0;
					}
					getCurrentTask().init();
				} else {
					break;
				}
			}
		}
	}

	@Override
	public void init() {
		this.tasks = new Array<Task>();
		this.currentTask = 0;
		this.loopCounter = 0;
		this.firstTime = true;
	}
}