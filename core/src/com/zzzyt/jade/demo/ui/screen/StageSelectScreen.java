package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.demo.stage1.Stage1;
import com.zzzyt.jade.demo.stageextra.StageExtra;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.ui.screen.BasicScreen;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class StageSelectScreen extends BasicScreen {

	private Grid grid;

	private Array<String> names;
	private Array<Task> stages;

	public StageSelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.grid = new Grid(true);
		st.addActor(grid);

		this.names = new Array<String>();
		this.stages = new Array<Task>();

		if (J.difficulty() == J.EXTRA) {
			names.add("Extra Stage");
			stages.add(new StageExtra());
		} else {
			names.add("Stage 1");
			stages.add(new Stage1());
		}
		for (int i = 0; i < names.size; i++) {
			final Task tmp = stages.get(i);
			grid.add(new GridButton(names.get(i), 48, 120, 720 - i * 60, 600, 60, 0, i, () -> {
				Global.put("_practice", tmp);
				U.switchScreen("playerSelect", 0.5f);
			}));
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
		U.switchScreen("difficultySelect", 0.5f);
	}

	@Override
	public String getName() {
		return "stageSelect";
	}

}
