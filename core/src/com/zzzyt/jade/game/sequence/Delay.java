package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;

public class Delay implements Sequence {

	private int frameCount;
	private int firstFrame;
	private boolean finished;

	public Delay(int frameCount) {
		this.frameCount = frameCount;
		this.finished = false;
		this.firstFrame = -1;
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
		
	}

}
