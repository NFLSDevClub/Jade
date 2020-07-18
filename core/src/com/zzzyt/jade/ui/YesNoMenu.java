package com.zzzyt.jade.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class YesNoMenu extends Grid {

	private Runnable yesRunnable, noRunnable;
	private GridLabel yes, no;

	public YesNoMenu(Runnable yesRunnable, Runnable noRunnable, int gridX, int gridY) {
		super(gridX, gridY, true, () -> Actions.fadeIn(0.3f), () -> Actions.fadeOut(0.3f));
		this.yesRunnable = yesRunnable;
		this.noRunnable = noRunnable;
		this.no = new GridLabel("No!", 18, 0, 30, 200, 20, 0, 0, this.noRunnable);
		this.yes = new GridLabel("Yes!", 18, 0, 0, 200, 20, 0, 1, this.yesRunnable);
		add(no);
		add(yes);
		updateComponent();
		selectFirst();
	}

	public YesNoMenu setYes(Runnable yesRunnable) {
		this.yesRunnable = yesRunnable;
		this.yes.runnable = yesRunnable;
		return this;
	}

	public YesNoMenu setNo(Runnable noRunnable) {
		this.noRunnable = noRunnable;
		this.no.runnable = noRunnable;
		return this;
	}

}
