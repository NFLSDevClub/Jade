package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.QuitListener;
import com.zzzyt.jade.ui.widget.FPSDisplay;
import com.zzzyt.jade.ui.widget.LabelButton;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.Game;

public class StartScreen implements FadeableScreen {

	public Viewport viewport;
	public Stage st;
	public InputMultiplexer input;

	private Image background;
	private FPSDisplay fps;
	private Grid grid;
	private ScreenState state;

	public StartScreen() {
		this.viewport = new ScalingViewport(Scaling.fit, Config.windowWidth, Config.windowHeight);
		this.state = ScreenState.HIDDEN;
	}

	@Override
	public void show() {
		A.load("font/SongSC.ttf");
		A.load("bg/start_bg.png");
		A.load("font/debug.fnt");
		A.finishLoading();

		this.st = new Stage(viewport);
		this.input = new InputMultiplexer();

		BGM.play("mus/Idea29.ogg");

		this.background = new Image((Texture) A.get("bg/start_bg.png"));
		background.setZIndex(0);
		st.addActor(background);

		this.fps = new FPSDisplay();
		st.addActor(fps);

		this.grid = new Grid(true);
		st.addActor(grid);
		grid.add(new LabelButton("Game Start", 24, 430, 290, 200, 30, 0, 1, () -> {
			Game.switchScreen("playerSelect", 0.5f);
		}));
		grid.add(new LabelButton("Extra Start", 24, 420, 260, 200, 30, 0, 2, () -> {

		}));
		grid.add(new LabelButton("Stage Practise", 24, 410, 230, 2001, 30, 0, 3, () -> {

		}));
		grid.add(new LabelButton("Spell Practise", 24, 400, 200, 200, 30, 0, 4, () -> {

		}));
		grid.add(new LabelButton("Replay", 24, 390, 170, 200, 30, 0, 5, () -> {

		}));
		grid.add(new LabelButton("Player Data", 24, 380, 140, 200, 30, 0, 6, () -> {

		}));
		grid.add(new LabelButton("Music Room", 24, 370, 110, 200, 30, 0, 7, () -> {

		}));
		grid.add(new LabelButton("Settings", 24, 360, 80, 200, 30, 0, 8, () -> {

		}));
		grid.add(new LabelButton("Quit", 24, 350, 50, 200, 30, 0, 9, () -> {
			Game.quit();
		}));
		grid.selectFirst();

		input.addProcessor(st);
		input.addProcessor(grid);
		input.addProcessor(new QuitListener(() -> {
			Game.quit();
		}));

		grid.updateAll();

		Game.addProcessor(input);
	}

	@Override
	public void render(float delta) {
		st.act();
		st.draw();
		A.update(2);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		Game.removeProcessor(input);
		state = ScreenState.HIDDEN;
		if (st != null)
			st.dispose();
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean fadeOut(float duration) {
		state = ScreenState.FADING_OUT;
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(100, 0, duration, Interpolation.sineOut));
		st.getRoot().addAction(Actions.sequence(Actions.delay(duration), Actions.run(() -> {
			hide();
		})));
		return true;
	}

	@Override
	public boolean fadeIn(float duration) {
		state = ScreenState.FADING_IN;
		show();
		grid.clearActions();
		grid.setPosition(100, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
		st.getRoot().getColor().a = 0;
		st.getRoot().addAction(Actions.sequence(Actions.fadeIn(duration), Actions.run(() -> {
			state = ScreenState.SHOWN;
		})));
		return true;
	}

	@Override
	public ScreenState getState() {
		return state;
	}

	@Override
	public void setState(ScreenState state) {
		this.state = state;
	}
	
	@Override
	public String getName() {
		return "start";
	}

}
