package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.ui.ScrollingGrid;
import com.zzzyt.jade.util.U;

public class SettingsScreen extends BasicScreen {

	private ScrollingGrid grid;

	public SettingsScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.grid = new ScrollingGrid(true, new Rectangle(32, 32, 576, 416));
		st.addActor(grid);
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 20; j++) {
				grid.add(new GridButton(i + "," + j, 18, j * 64, 416 - i * 32, 64, 32, j, i, null));
			}
		}
		grid.selectFirst();
		grid.activate();
		grid.updateComponent();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(-200, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(-200, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		U.switchScreen("start", 0.5f);
	}

	@Override
	public String getName() {
		return "settings";
	}

}