package com.zzzyt.jade.game;

import java.util.function.IntConsumer;

public class BasicSequence implements Sequence {

	private IntConsumer updateFunc;
	private boolean finished;
	private int firstFrame;

	public BasicSequence() {
		
	}
	
	public BasicSequence(IntConsumer updateFunc) {
		this.updateFunc = updateFunc;
		this.finished = false;
		this.firstFrame = -1;
	}

	public void terminate() {
		this.finished = true;
	}

	public BasicSequence setUpdateFunc(IntConsumer updateFunc) {
		this.updateFunc=updateFunc;
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
		updateFunc.accept(frame - firstFrame);
	}

}
