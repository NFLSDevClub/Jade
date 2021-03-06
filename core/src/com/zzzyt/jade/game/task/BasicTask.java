package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.game.Updatable;

public class BasicTask implements Task {

	private Updatable updateFunc;
	private boolean finished;
	private int firstT;
	private int delay;

	public BasicTask() {

	}

	public BasicTask(Updatable updateFunc) {
		this(0, updateFunc);
	}

	public BasicTask(int delay, Updatable updateFunc) {
		this.updateFunc = updateFunc;
		this.delay = delay;
	}

	public void terminate() {
		this.finished = true;
	}

	public BasicTask setDelay(int delay) {
		this.delay = delay;
		return this;
	}

	public BasicTask setUpdateFunc(Updatable updateFunc) {
		this.updateFunc = updateFunc;
		return this;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void update(int t) {
		if (firstT == -1) {
			firstT = t;
		}
		if (t - firstT >= delay) {
			updateFunc.update(t - firstT - delay);
		}

	}

	@Override
	public void init() {
		this.finished = false;
		this.firstT = -1;
	}
}
