package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.QuitListener;
import com.zzzyt.jade.ui.widget.FPSDisplay;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.Game;

public class BlankScreen extends BasicScreen {

	public BlankScreen() {
		super();
	}

	@Override
	public void show() {
		this.st = new Stage(viewport);
		this.input = new InputMultiplexer();

		this.background = new Image(A.getTexture("bg/blank.png"));
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
	public boolean fadeIn(float duration) {
		state = ScreenState.FADING_IN;
		show();
		st.getRoot().getColor().a = 1;
		st.getRoot().addAction(Actions.sequence(Actions.delay(duration), Actions.run(() -> {
			state = ScreenState.SHOWN;
			if (Global.get("_redirect") != null) {
				if (Global.get("_redirectDelay") != null) {
					Game.switchScreen((String) Global.get("_redirect"), (float) Global.get("_redirectDelay"));
					Global.remove("_redirectDelay");
				} else {
					Game.switchScreen((String) Global.get("_redirect"));
				}
				Global.remove("_redirect");
			}
		})));
		onFadeIn(duration);
		return true;
	}

	@Override
	public String getName() {
		return "blank";
	}

}
