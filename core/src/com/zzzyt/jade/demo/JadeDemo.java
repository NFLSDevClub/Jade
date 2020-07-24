package com.zzzyt.jade.demo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.WindowedMean;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.music.BackgroundMusic;
import com.zzzyt.jade.ui.BackgroundLoader;
import com.zzzyt.jade.ui.InputBlocker;
import com.zzzyt.jade.ui.screen.BlankScreen;
import com.zzzyt.jade.ui.screen.DifficultySelectScreen;
import com.zzzyt.jade.ui.screen.FadeableScreen;
import com.zzzyt.jade.ui.screen.GameScreen;
import com.zzzyt.jade.ui.screen.MusicRoomScreen;
import com.zzzyt.jade.ui.screen.PlayerSelectScreen;
import com.zzzyt.jade.ui.screen.ScreenState;
import com.zzzyt.jade.ui.screen.SpellSelectScreen;
import com.zzzyt.jade.ui.screen.StageSelectScreen;
import com.zzzyt.jade.ui.screen.StartScreen;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.U;

public class JadeDemo implements ApplicationListener {

	public Logger logger;
	public Array<FadeableScreen> screens;
	public InputMultiplexer input;
	public InputBlocker blocker;

	public WindowedMean fpsCounter;

	@Override
	public void create() {
		Gdx.app.setLogLevel(U.config().logLevel);

		U.game = this;

		if(U.config().fullScreenOnStart) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
		
		this.logger = new Logger("Main", U.config().logLevel);
		logger.info("Game start!");

		A.init();

		BGM.register(new BackgroundMusic("mus/Idea12.ogg", 0, 12));
		BGM.register(new BackgroundMusic("mus/E.0109.ogg", 10, Float.MAX_VALUE));
		BGM.register(new BackgroundMusic("mus/Yet Another Tetris (Piano ver.).ogg", 0, Float.MAX_VALUE));
		A.load(U.config().UIFont);
		A.load("font/LBRITE.ttf");
		A.load("font/LBRITEI.ttf");
		A.load("font/debug.fnt");
		A.load("bg/blank.png");
		A.load("bg/start.png");
		A.load("default_shot.shot");
		A.finishLoading();

		B.setSheet(U.config().defaultShotSheet);

		this.blocker = new InputBlocker();
		blocker.enable();

		this.input = new InputMultiplexer();
		input.addProcessor(blocker);
		Gdx.input.setInputProcessor(input);

		this.fpsCounter = new WindowedMean(10);

		this.screens = new Array<FadeableScreen>();
		screens.add(new BlankScreen());
		screens.add(new StartScreen());
		screens.add(new GameScreen());
		screens.add(new DifficultySelectScreen());
		screens.add(new PlayerSelectScreen());
		screens.add(new StageSelectScreen());
		screens.add(new SpellSelectScreen());
		screens.add(new MusicRoomScreen());

		U.switchScreen("blank");
		U.switchScreen("start", 0.5f);

		BackgroundLoader backgroundLoader = new BackgroundLoader();
		backgroundLoader.start();
	}

	@Override
	public void render() {
		BGM.update();

		if (U.config().allowFullScreen && Gdx.input.isKeyPressed(Keys.F4)) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(U.config().windowWidth, U.config().windowHeight);
			} else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		}

		fpsCounter.addValue(Gdx.graphics.getDeltaTime());

		U.glClear();

		boolean flag1 = false;
		boolean flag2 = false;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.FADING_IN) {
				flag1 = true;
				screens.get(i).render(U.safeDeltaTime());
			}
		}
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.SHOWN) {
				flag2 = true;
				screens.get(i).render(U.safeDeltaTime());
			}
		}
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState() == ScreenState.FADING_OUT) {
				flag1 = true;
				screens.get(i).render(U.safeDeltaTime());
			}
		}
		if (flag1) {
			blocker.enable();
		} else {
			blocker.disable();
		}
		if (!flag1 && !flag2) {
			Gdx.app.exit();
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
