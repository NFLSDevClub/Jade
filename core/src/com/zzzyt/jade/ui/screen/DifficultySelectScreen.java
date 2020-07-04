package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.ui.widget.GridImage;

public class DifficultySelectScreen extends BasicScreen {

	private Grid grid;

	public DifficultySelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea29.ogg", A.get("bg/select_bg.png"));

		this.grid = new Grid(true);
		st.addActor(grid);

		((GridImage) grid.add(new GridImage(A.get("diff/easy.png"), 60, 340, 0, 0, () -> {
			G.put("_difficulty", "easy");
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.get("diff/normal.png"), 60, 240, 0, 1, () -> {
			G.put("_difficulty", "normal");
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.get("diff/hard.png"), 60, 140, 0, 2, () -> {
			G.put("_difficulty", "hard");
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		((GridImage) grid.add(new GridImage(A.get("diff/lunatic.png"), 60, 40, 0, 3, () -> {
			G.put("_difficulty", "lunatic");
			Game.switchScreen("playerSelect", 0.5f);
		}))).setScale(0.75f);
		
		grid.selectFirst();
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
