package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.ui.screen.BasicScreen;
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

		this.title = new Label("Jade Demo Game", new LabelStyle(A.getFont("font/LBRITE.ttf", 120), Color.BLACK));
		title.setPosition(180, 780);
		
		this.subtitle = new Label("by Zzzyt", new LabelStyle(A.getFont("font/LBRITEI.ttf", 60), Color.BLACK));
		subtitle.setPosition(960, 720);
		
		this.titles = new Group();
		titles.addActor(title);
		titles.addActor(subtitle);
		st.addActor(titles);

		this.grid = new Grid(true);
		st.addActor(grid);
		grid.add(new GridButton("Game Start", 48, 860, 580, 400, 60, 0, 0, () -> {
			A.finishLoading();
			Global.put("_gameMode", "regular");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Extra Start", 48, 840, 520, 400, 60, 0, 1, () -> {
			A.finishLoading();
			Global.put("_gameMode", "extra");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Stage Practice", 48, 820, 460, 400, 60, 0, 2, () -> {
			A.finishLoading();
			Global.put("_gameMode", "stagePractice");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Spell Practice", 48, 800, 400, 400, 60, 0, 3, () -> {
			A.finishLoading();
			Global.put("_gameMode", "spellPractice");
			U.switchScreen("difficultySelect", 0.5f);
		}));
		grid.add(new GridButton("Replay", 48, 780, 340, 400, 60, 0, 4, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Player Data", 48, 760, 280, 400, 60, 0, 5, () -> {
			A.finishLoading();
		})).disable();
		grid.add(new GridButton("Music Room", 48, 740, 220, 400, 60, 0, 6, () -> {
			A.finishLoading();
			U.switchScreen("musicRoom", 0.5f);
		}));
		grid.add(new GridButton("Settings", 48, 720, 160, 400, 60, 0, 7, () -> {
			A.finishLoading();
			U.switchScreen("settings", 0.5f);
		}));
		grid.add(new GridButton("Quit", 48, 700, 100, 400, 60, 0, 8, () -> {
			U.quit();
		}));
		grid.activate();
		grid.selectFirst();
		grid.updateComponent();
		input.addProcessor(grid);
	}

	@Override
	public void render(float delta) {
		st.act(U.safeDeltaTime());
		st.draw();
		A.update();
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(400, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));

		titles.clearActions();
		titles.setPosition(0, 400);
		titles.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.addAction(Actions.moveTo(400, 0, duration, Interpolation.sineOut));

		titles.clearActions();
		titles.addAction(Actions.moveTo(0, 400, duration, Interpolation.sineOut));
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
