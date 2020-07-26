package com.zzzyt.jade.ui;

import java.util.concurrent.Callable;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class YesNoMenu extends Grid {

	private Runnable yesRunnable, noRunnable;
	private GridLabel label;
	private GridButton yes, no;

	public YesNoMenu(float x, float y) {
		this(null, null, 0, 0, null, null);
		setNo(() -> {
			exit();
		});
		this.activeAction = () -> Actions.parallel(Actions.fadeIn(0.1f),
				Actions.moveTo(x, y, 0.1f, Interpolation.sine));
		this.inactiveAction = () -> Actions.parallel(Actions.fadeOut(0.1f),
				Actions.moveTo(x - 30, y, 0.1f, Interpolation.sine));
		setPosition(x, y);
	}

	public YesNoMenu(Runnable yesRunnable, Runnable noRunnable, int gridX, int gridY,
			Callable<? extends Action> activeAction, Callable<? extends Action> inactiveAction) {
		super(gridX, gridY, true, activeAction, inactiveAction);
		this.yesRunnable = yesRunnable;
		this.noRunnable = noRunnable;
		this.label = new GridLabel("Really?", 48, 0, 140, 400, 60, 0, 0);
		this.yes = new GridButton("Yes!", 36, 10, 60, 400, 40, 0, 1, this.yesRunnable);
		this.no = new GridButton("No!", 36, 20, 0, 400, 40, 0, 2, this.noRunnable);
		add(no);
		add(yes);
		add(label);
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
