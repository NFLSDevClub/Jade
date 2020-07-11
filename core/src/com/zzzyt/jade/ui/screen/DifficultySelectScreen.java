package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridImage;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class DifficultySelectScreen extends BasicScreen {

	private Grid grid;

	public DifficultySelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0107.ogg", "bg/select.png");

		this.grid = new Grid(true);
		st.addActor(grid);

		final String switchTarget;
		if (J.isGameModeSpellPractice()) {
			switchTarget = "spellSelect";
		} else if (J.isGameModeStagePractice()) {
			switchTarget = "stageSelect";
		} else {
			switchTarget = "playerSelect";
		}
		if (J.isGameModeRegular() || J.isGameModeSpellPractice() || J.isGameModeStagePractice()) {
			((GridImage) grid.add(new GridImage(A.getRegion("diff/easy.png"), 60, 370, 0, 0, () -> {
				Global.put("_difficulty", J.EASY);
				U.switchScreen(switchTarget, 0.5f);
			}))).setScale(0.75f);
			((GridImage) grid.add(new GridImage(A.getRegion("diff/normal.png"), 60, 280, 0, 1, () -> {
				Global.put("_difficulty", J.NORMAL);
				U.switchScreen(switchTarget, 0.5f);
			}))).setScale(0.75f);
			((GridImage) grid.add(new GridImage(A.getRegion("diff/hard.png"), 60, 190, 0, 2, () -> {
				Global.put("_difficulty", J.HARD);
				U.switchScreen(switchTarget, 0.5f);
			}))).setScale(0.75f);
			((GridImage) grid.add(new GridImage(A.getRegion("diff/lunatic.png"), 60, 100, 0, 3, () -> {
				Global.put("_difficulty", J.LUNATIC);
				U.switchScreen(switchTarget, 0.5f);
			}))).setScale(0.75f);
		}

		if (J.isGameModeExtra() || J.isGameModeSpellPractice() || J.isGameModeStagePractice()) {
			((GridImage) grid.add(new GridImage(A.getRegion("diff/extra.png"), 60, 10, 0, 4, () -> {
				Global.put("_difficulty", J.EXTRA);
				U.switchScreen(switchTarget, 0.5f);
			}))).setScale(0.75f);
		}
		grid.activate();
		if (Global.get("_difficulty") == null) {
			grid.selectFirst();
		} else {
			grid.select(0, J.difficulty() - 1);
		}
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
		return "difficultySelect";
	}

}
