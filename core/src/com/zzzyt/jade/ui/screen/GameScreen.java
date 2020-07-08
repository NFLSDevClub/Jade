package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.demo.difficulty.DifficultyRegular;
import com.zzzyt.jade.demo.difficulty.DifficultyExtra;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.ui.widget.GameFrame;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.Game;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	public GameScreen() {
		super();
	}

	@Override
	public void show() {
		init(null, "bg/game.png");

		this.frame = new GameFrame();
		frame.setBounds(Config.offsetX, Config.offsetY, Config.w, Config.h);
		st.addActor(frame);

		this.jade = new Jade();

		if ("reimu".equals(Global.get("_player"))) {
			jade.setPlayer(new PlayerReimu());
		} else if ("marisa".equals(Global.get("_player"))) {
			jade.setPlayer(new PlayerMarisa());
		}

		frame.setJade(jade);

		if ("regular".equals(Global.get("_gameMode"))) {
			jade.addTask(new DifficultyRegular((int) Global.get("_difficulty")));
		} else if ("extra".equals(Global.get("_gameMode"))) {
			jade.addTask(new DifficultyExtra());
		}

		jade.getTask(0).init();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && (!J.isGameModeReplay() || Config.allowSpeedUpOutOfReplay)) {
			for (int i = 0; i < Config.speedUpMultiplier - 1; i++) {
				jade.update();
				jade.postRender();
			}
		}
		jade.update();
		jade.draw();

		// Important!!!! Or viewport will become stretched.
		Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());
		st.act();
		st.draw();
		jade.postRender();
	}

	@Override
	protected void onQuit() {

	}

	@Override
	public void hide() {
		Game.removeProcessor(input);
		state = ScreenState.HIDDEN;
		if (jade != null)
			jade.dispose();
		if (st != null)
			st.dispose();
	}

	@Override
	public String getName() {
		return "game";
	}

}
