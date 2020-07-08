package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;

public class Wait implements Task {

	private int frameCount;
	private int firstFrame;
	private boolean finished;

	public Wait(int frameCount) {
		this.frameCount = frameCount;
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
		if (frame - firstFrame >= frameCount) {
			finished = true;
		}
	}

	@Override
	public void init() {
		this.finished = false;
		this.firstFrame = -1;
	}

}
