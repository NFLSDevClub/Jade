package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.ui.widget.GridImage;

public class DifficultySelectScreen extends BasicScreen {

	private Grid grid;

	public DifficultySelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea29.ogg", "bg/select.png");

		this.grid = new Grid(true);
		st.addActor(grid);

		((GridImage) grid.add(new GridImage(A.getRegion("diff/easy.png"), 60, 340, 0, 0, () -> {
			Global.put("_difficulty", J.EASY);
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.getRegion("diff/normal.png"), 60, 240, 0, 1, () -> {
			Global.put("_difficulty", J.NORMAL);
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.getRegion("diff/hard.png"), 60, 140, 0, 2, () -> {
			Global.put("_difficulty", J.HARD);
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.getRegion("diff/lunatic.png"), 60, 40, 0, 3, () -> {
			Global.put("_difficulty", J.LUNATIC);
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);

		if (Global.get("_difficulty") == null) {
			grid.selectFirst();
		} else {
			grid.select(0, J.difficulty() - 1);
		}
		grid.updateAll();
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
		Game.switchScreen("start", 0.5f);
	}

	@Override
	public String getName() {
		return "difficultySelect";
	}

}
