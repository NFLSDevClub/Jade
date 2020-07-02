package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.LabelButtonGrid;
import com.zzzyt.jade.ui.QuitListener;
import com.zzzyt.jade.ui.widget.FPSDisplay;
import com.zzzyt.jade.ui.widget.LabelButton;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class PlayerSelectScreen implements FadeableScreen {

	public Viewport viewport;
	public Stage st;
	public InputMultiplexer input;

	private Image background;
	private FPSDisplay fps;
	private LabelButtonGrid grid;
	private Group buttons;
	private ScreenState state;

	public PlayerSelectScreen() {
		this.viewport = new ScalingViewport(Scaling.none, Config.windowWidth, Config.windowHeight);
		this.state = ScreenState.HIDDEN;
	}

	@Override
	public void show() {
		BGM.play("mus/Idea29.wav");

		this.st = new Stage(viewport);
		this.input = new InputMultiplexer();

		this.background = new Image((Texture) A.get("bg/start_bg.png"));
		background.setZIndex(0);
		st.addActor(background);

		this.fps = new FPSDisplay();
		st.addActor(fps);
		
		this.buttons = new Group();
		st.addActor(buttons);

		this.grid = new LabelButtonGrid(true);

		grid.add(new LabelButton("Hakurei Reimu", 24, 330, 290, 200, 30, 0, 0, () -> {
			G.put("_selectedPlayer", "reimu");
			Game.switchScreen("game");
		}));
		grid.add(new LabelButton("Kirisame Marisa", 24, 330, 260, 200, 30, 0, 1, () -> {
			G.put("_selectedPlayer", "marisa");
			Game.switchScreen("game");
		}));

		grid.selectFirst();
		grid.addToGroup(buttons);

		input.addProcessor(st);
		input.addProcessor(grid);
		input.addProcessor(new QuitListener(() -> {
			Game.switchScreen("start", 0.5f);
		}));

		grid.updateAll();

		Game.addProcessor(input);
	}

	@Override
	public void render(float delta) {
		st.act();
		st.draw();
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
		buttons.clearActions();
		buttons.setPosition(0, 0);
		buttons.addAction(
				Actions.parallel(Actions.fadeOut(duration), Actions.moveTo(100, 0, duration, Interpolation.sineOut)));
		st.getRoot().addAction(Actions.sequence(Actions.delay(duration), Actions.run(() -> {
			hide();
		})));
		return true;
	}

	@Override
	public boolean fadeIn(float duration) {
		state = ScreenState.FADING_IN;
		show();
		buttons.clearActions();
		buttons.getColor().a = 0;
		buttons.setPosition(100, 0);
		buttons.addAction(
				Actions.parallel(Actions.fadeIn(duration), Actions.moveTo(0, 0, duration, Interpolation.sineOut)));
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
		return "playerSelect";
	}

}
