package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.grid.Grid;
import com.zzzyt.jade.ui.grid.GridButton;
import com.zzzyt.jade.ui.grid.ScrollingCenteredGrid;
import com.zzzyt.jade.ui.grid.ScrollingGrid;
import com.zzzyt.jade.ui.screen.BasicScreen;
import com.zzzyt.jade.util.U;

public class SettingsScreen extends BasicScreen {

	private ScrollingGrid grid;

	public SettingsScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.grid = new ScrollingGrid(true, new Rectangle(64, 64, 1152, 832));
		st.addActor(grid);

		grid.add(new GridButton("Menu 1", 36, 100, 200, 200, 60, 0, 0, null));
		Grid tmp = new Grid(true);
		tmp.setPosition(300, 200);
		tmp.setGridX(0).setGridY(0);
		tmp.add(new GridButton("TEST", 36, 0, 0, 200, 60, 0, 0, null));
		tmp.add(new GridButton("TEST2", 36, 200, 0, 200, 60, 1, 0, null));
		tmp.selectFirst();
		tmp.updateComponent();
		tmp.update();
		input.addProcessor(tmp);

		grid.add(new GridButton("Menu 2", 36, 100, 100, 200, 60, 0, 1, null));
		ScrollingCenteredGrid tmp2 = new ScrollingCenteredGrid(true,100,0);
		tmp2.setPosition(300, 100);
		tmp2.setGridX(0).setGridY(1);
		tmp2.add(new GridButton("TEST3", 36, 0, 0, 200, 60, 0, 0, null));
		tmp2.add(new GridButton("TEST4", 36, 200, 0, 200, 60, 1, 0, null));
		tmp2.selectFirst();
		tmp2.updateComponent();
		tmp2.update();
		input.addProcessor(tmp2);

		grid.add(tmp);
		grid.add(tmp2);
		grid.selectFirst();
		grid.activate();
		grid.updateComponent();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(-400, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(-400, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		super.onQuit();
		U.switchScreen("start", 0.5f);
	}

	@Override
	public String getName() {
		return "settings";
	}

}