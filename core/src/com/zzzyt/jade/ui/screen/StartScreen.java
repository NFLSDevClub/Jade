package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.widget.GridLabel;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.Game;

public class StartScreen extends BasicScreen {

	private Grid grid;
	private Label title, subtitle;
	private Group titles;

	public StartScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0107.ogg", "bg/start.png");

		this.title = new Label("Jade Demo Game", new LabelStyle(A.getFont("font/LBRITE.ttf", 60), Color.BLACK));
		title.setPosition(90, 390);
		this.subtitle = new Label("by Zzzyt", new LabelStyle(A.getFont("font/LBRITEI.ttf", 30), Color.BLACK));
		subtitle.setPosition(480, 360);
		this.titles = new Group();
		titles.addActor(title);
		titles.addActor(subtitle);
		st.addActor(titles);

		this.grid = new Grid(true);
		st.addActor(grid);
		grid.add(new GridLabel("Game Start", 24, 430, 290, 200, 30, 0, 1, () -> {
			Global.put("_gameMode","regular");
			Game.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridLabel("Extra Start", 24, 420, 260, 200, 30, 0, 2, () -> {
			Global.put("_gameMode","extra");
			Game.switchScreen("playerSelect", 0.5f);
		}));
		grid.add(new GridLabel("Stage Practice", 24, 410, 230, 2001, 30, 0, 3, () -> {
//			Global.put("_gameMode","stagePractice");
//			Game.switchScreen("difficultySelect", 0.5f);
		})).disable();
		grid.add(new GridLabel("Spell Practice", 24, 400, 200, 200, 30, 0, 4, () -> {
			Global.put("_gameMode","spellPractice");
			Game.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridLabel("Replay", 24, 390, 170, 200, 30, 0, 5, () -> {
			
		})).disable();
		grid.add(new GridLabel("Player Data", 24, 380, 140, 200, 30, 0, 6, () -> {

		})).disable();
		grid.add(new GridLabel("Music Room", 24, 370, 110, 200, 30, 0, 7, () -> {

		})).disable();
		grid.add(new GridLabel("Settings", 24, 360, 80, 200, 30, 0, 8, () -> {

		})).disable();
		grid.add(new GridLabel("Quit", 24, 350, 50, 200, 30, 0, 9, () -> {
			Game.quit();
		}));
		grid.activate();
		grid.selectFirst();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	public void render(float delta) {
		st.act();
		st.draw();
		A.update(2);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(200, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));

		titles.clearActions();
		titles.setPosition(0, 200);
		titles.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.addAction(Actions.moveTo(200, 0, duration, Interpolation.sineOut));

		titles.clearActions();
		titles.addAction(Actions.moveTo(0, 200, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		Game.quit();
	}

	@Override
	public String getName() {
		return "start";
	}

}
