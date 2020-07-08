package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.FPSDisplay;
import com.zzzyt.jade.ui.KeyListener;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.Game;

public class BasicScreen implements FadeableScreen {

	public Viewport viewport;
	public Stage st;
	public InputMultiplexer input;

	protected Image background;
	protected FPSDisplay fps;
	protected ScreenState state;

	public BasicScreen() {
		this.viewport = new ScalingViewport(Scaling.none, Config.windowWidth, Config.windowHeight);
		this.state = ScreenState.HIDDEN;
	}

	@Override
	public void show() {

	}

	public void init(String bgm, String backgroundName) {
		init(bgm, A.getRegion(backgroundName));
	}

	public void init(String bgm, TextureRegion background) {
		BGM.play(bgm);

		this.st = new Stage(viewport);
		st.setDebugAll(Config.debugActorLayout);
		this.input = new InputMultiplexer();

		this.background = new Image(background);
		this.background.setZIndex(0);
		st.addActor(this.background);

		this.fps = new FPSDisplay();
		st.addActor(fps);

		input.addProcessor(st);
		input.addProcessor(new KeyListener(Config.keyCancel, () -> {
			onQuit();
		}));

		Game.addProcessor(input);
	}

	protected void onFadeIn(float duration) {

	}

	protected void onFadeOut(float duration) {

	}

	protected void onQuit() {

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
		st.getRoot().addAction(Actions.sequence(Actions.fadeOut(duration), Actions.run(() -> {
			hide();
		})));
		onFadeOut(duration);
		return true;
	}

	@Override
	public boolean fadeIn(float duration) {
		state = ScreenState.FADING_IN;
		show();
		st.getRoot().getColor().a = 1;
		st.getRoot().addAction(Actions.sequence(Actions.delay(duration), Actions.run(() -> {
			state = ScreenState.SHOWN;
		})));
		onFadeIn(duration);
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
		return "";
	}

}
