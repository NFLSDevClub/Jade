package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.U;

public class StartScreen extends BasicScreen {

	private Grid grid;
	private Label title, subtitle;
	private Group titles;

	public StartScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/start.png");

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
		grid.add(new GridButton("Game Start", 24, 430, 290, 200, 30, 0, 0, () -> {
			A.finishLoading();
			Global.put("_gameMode", "regular");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Extra Start", 24, 420, 260, 200, 30, 0, 1, () -> {
			A.finishLoading();
			Global.put("_gameMode", "extra");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Stage Practice", 24, 410, 230, 200, 30, 0, 2, () -> {
			A.finishLoading();
			Global.put("_gameMode", "stagePractice");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Spell Practice", 24, 400, 200, 200, 30, 0, 3, () -> {
			A.finishLoading();
			Global.put("_gameMode", "spellPractice");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Replay", 24, 390, 170, 200, 30, 0, 4, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Player Data", 24, 380, 140, 200, 30, 0, 5, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Music Room", 24, 370, 110, 200, 30, 0, 6, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Settings", 24, 360, 80, 200, 30, 0, 7, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Quit", 24, 350, 50, 200, 30, 0, 8, () -> {
			U.quit();
		}));
		grid.activate();
		grid.selectFirst();
		grid.updateComponent();
		input.addProcessor(grid);
	}

	@Override
	public void render(float delta) {
		st.act();
		st.draw();
		A.update();
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
		if (grid.selectedY == 8) {
			U.quit();
		} else {
			grid.select(0, 8);
		}
	}

	@Override
	public String getName() {
		return "start";
	}

}
