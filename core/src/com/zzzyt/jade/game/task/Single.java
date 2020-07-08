package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;

public class Single implements Task {

	private Updateable updateFunc;
	private boolean finished;
	private int firstFrame;
	private int delay;

	public Single() {

	}

	public Single(Updateable updateFunc) {
		this(0, updateFunc);
	}

	public Single(int delay, Updateable updateFunc) {
		this.updateFunc = updateFunc;
		this.delay = delay;
	}

	public void terminate() {
		this.finished = true;
	}

	public Single setDelay(int delay) {
		this.delay = delay;
		return this;
	}

	public Single setUpdateFunc(Updateable updateFunc) {
		this.updateFunc = updateFunc;
		return this;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void update(int frame) {
		if (firstFrame == -1) {
			firstFrame = frame;
		}
		if (frame - firstFrame >= delay) {
			updateFunc.update(frame - firstFrame - delay);
		}

	}

	@FunctionalInterface
	public static interface Updateable {
		public void update(int frame);
	}

	@Override
	public void init() {
		this.finished = false;
		this.firstFrame = -1;
	}
}
