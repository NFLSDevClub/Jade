package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.ui.JadeDemo;
import com.zzzyt.jade.ui.screen.FadeableScreen;
import com.zzzyt.jade.ui.screen.ScreenState;

public class Game {
	public static JadeDemo game;

	public static void quit() {
		Gdx.app.exit();
	}

	public static InputProcessor addProcessor(InputProcessor processor) {
		game.input.addProcessor(processor);
		return processor;
	}

	public static InputProcessor removeProcessor(InputProcessor processor) {
		game.input.removeProcessor(processor);
		return processor;
	}

	public static void switchScreen(String name) {
		Array<FadeableScreen> screens = game.screens;
		FadeableScreen scr = null;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getName().equals(name)) {
				scr = screens.get(i);
				break;
			}
		}
		game.blocker.enable();
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).hide();
				screens.get(i).setState(ScreenState.HIDDEN);
			}
		}
		if (scr != null) {
			game.logger.info("Switching to screen \"" + name + "\".");
			scr.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scr.show();
			scr.setState(ScreenState.SHOWN);
		} else {
			game.logger.info("Switching to no screen.");
		}
	}

	public static void switchScreen(String name, float fadeTime) {
		Array<FadeableScreen> screens = game.screens;
		FadeableScreen scr = null;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getName().equals(name)) {
				scr = screens.get(i);
				break;
			}
		}
		game.blocker.enable();
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).fadeOut(fadeTime);
			}
		}
		if (scr != null) {
			game.logger.info("Switching to screen \"" + name + "\".");
			scr.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scr.fadeIn(fadeTime);
		} else {
			game.logger.info("Switching to no screen.");
		}
	}
}
