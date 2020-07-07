package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;

public class Single implements Sequence {

	private Updateable updateFunc;
	private boolean finished;
	private int firstFrame;

	public Single() {
		this.finished = false;
		this.firstFrame = -1;
	}

	public Single(Updateable updateFunc) {
		this.updateFunc = updateFunc;
		this.finished = false;
		this.firstFrame = -1;
	}

	public void terminate() {
		this.finished = true;
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
		updateFunc.update(frame - firstFrame);
	}

	@FunctionalInterface
	public static interface Updateable {
		public void update(int frame);
	}

	@Override
	public void init() {
		
	}
}
