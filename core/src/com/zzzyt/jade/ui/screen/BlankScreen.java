package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.QuitListener;
import com.zzzyt.jade.ui.widget.FPSDisplay;
import com.zzzyt.jade.util.A;

public class BlankScreen extends BasicScreen {

	public BlankScreen() {
		super();
	}

	@Override
	public void show() {
		A.load("font/debug.fnt");
		A.load("bg/bg_blank.png");
		A.finishLoading();
		
		this.st = new Stage(viewport);
		this.input = new InputMultiplexer();

		this.background = new Image((Texture) A.get("bg/bg_blank.png"));
		background.setZIndex(0);
		background.setSize(Config.windowWidth, Config.windowHeight);
		st.addActor(this.background);

		this.fps = new FPSDisplay();
		st.addActor(fps);

		input.addProcessor(st);
		input.addProcessor(new QuitListener(() -> {
			onQuit();
		}));
	}

	@Override
	public String getName() {
		return "blank";
	}

}
