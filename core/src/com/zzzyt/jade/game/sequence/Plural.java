package com.zzzyt.jade.game.sequence;

import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.Sequence;

public class Plural implements Sequence {

	private Array<Sequence> sequences;
	private int currentSequence;
	private boolean firstTime;

	public Plural() {
		this.sequences = new Array<Sequence>();
		this.currentSequence = 0;
		this.firstTime = true;
	}

	public Array<Sequence> getSequences() {
		return sequences;
	}

	public Sequence getSequence() {
		return sequences.get(currentSequence);
	}

	public Plural add(Sequence sequence) {
		sequences.add(sequence);
		return this;
	}

	public Plural remove(Sequence sequence) {
		sequences.removeValue(sequence, true);
		return this;
	}

	@Override
	public boolean isFinished() {
		return currentSequence >= sequences.size;
	}

	@Override
	public void update(int frame) {
		if (isFinished()) {
			return;
		}
		if (firstTime) {
			getSequence().init();
			firstTime = false;
		}
		while (currentSequence < sequences.size) {
			getSequence().update(frame);
			if (getSequence().isFinished()) {
				currentSequence++;
				getSequence().init();
			} else {
				break;
			}
		}

	}

	@Override
	public void init() {
		
	}
}