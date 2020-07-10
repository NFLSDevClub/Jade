package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Drawable;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

public class AddDrawable implements Task {

	public Drawable drawable;

	public AddDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int t) {
		J.addDrawable(drawable);
	}

	@Override
	public void init() {

	}

}
