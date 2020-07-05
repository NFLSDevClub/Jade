package com.zzzyt.jade.game;

import com.badlogic.gdx.utils.Array;

public class MultiSequence implements Sequence {
	private Array<Sequence> sequences;
	private int currentSequence;

	public MultiSequence() {
		this.sequences = new Array<Sequence>();
		this.currentSequence = 0;
	}

	public Array<Sequence> getSequences(){
		return sequences;
	}
	
	public MultiSequence add(Sequence sequence) {
		sequences.add(sequence);
		return this;
	}

	public MultiSequence remove(Sequence sequence) {
		sequences.removeValue(sequence, true);
		return this;
	}

	@Override
	public boolean isFinished() {
		return currentSequence >= sequences.size;
	}

	@Override
	public void update(int frame) {
		sequences.get(currentSequence).update(frame);
	}
}