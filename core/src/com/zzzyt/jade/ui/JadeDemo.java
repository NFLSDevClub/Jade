package com.zzzyt.jade.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.WindowedMean;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.music.BackgroundMusic;
import com.zzzyt.jade.ui.screen.FadeableScreen;
import com.zzzyt.jade.ui.screen.GameScreen;
import com.zzzyt.jade.ui.screen.PlayerSelectScreen;
import com.zzzyt.jade.ui.screen.ScreenState;
import com.zzzyt.jade.ui.screen.StartScreen;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.util.Utils;

public class JadeDemo implements ApplicationListener {

	public Logger logger;
	public Array<FadeableScreen> screens;
	public InputMultiplexer input;
	public InputBlocker blocker;

	public WindowedMean fpsCounter;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Config.logLevel);
		
		Game.game = this;

		this.logger = new Logger("Main", Config.logLevel);
		logger.info("Game start!");

		A.init();

		BGM.register(new BackgroundMusic("mus/Idea12.wav",6,9));
		BGM.register(new BackgroundMusic("mus/Idea29.wav",10,26));
		
		this.blocker = new InputBlocker();
		blocker.enable();

		this.input = new InputMultiplexer();
		input.addProcessor(blocker);
		Gdx.input.setInputProcessor(input);

		this.fpsCounter = new WindowedMean(10);

		this.screens = new Array<FadeableScreen>();
		screens.add(new StartScreen());
		screens.add(new GameScreen());
		screens.add(new PlayerSelectScreen());

		Game.switchScreen("start", 0.5f);

		BackgroundLoader backgroundLoader = new BackgroundLoader();
		backgroundLoader.start();
	}

	@Override
	public void render() {
		BGM.update();
		
		if (Gdx.input.isKeyPressed(Keys.F4)) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(Config.windowWidth, Config.windowHeight);
			} else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}
		fpsCounter.addValue(Gdx.graphics.getDeltaTime());

		Utils.glClear();

		int flag = 0;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.FADING_OUT) {
				flag++;
				screens.get(i).render(Utils.safeDeltaTime());
			}
		}
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.SHOWN) {
				flag++;
				screens.get(i).render(Utils.safeDeltaTime());
			}
		}
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.FADING_IN) {
				flag++;
				screens.get(i).render(Utils.safeDeltaTime());
			}
		}
		if (flag <= 1) {
			blocker.disable();
		} else {
			blocker.enable();
		}
	}

	@Override
	public void dispose() {
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).hide();
			}
			screens.get(i).dispose();
		}
		A.dispose();
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).resize(width, height);
			}
		}
	}

	@Override
	public void pause() {
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).pause();
			}
		}
	}

	@Override
	public void resume() {
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).resume();
			}
		}
	}
}
