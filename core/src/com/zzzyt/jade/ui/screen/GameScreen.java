package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.demo.difficulty.DifficultyRegular;
import com.zzzyt.jade.demo.difficulty.DifficultyExtra;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.ui.GameFrame;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridLabel;
import com.zzzyt.jade.ui.KeyListener;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.BGM;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	private Grid pauseMenu;
	private boolean paused;

	public GameScreen() {
		super();
	}

	@Override
	public void show() {
		init(null, "bg/game.png");

		input.addProcessor(new KeyListener(U.config().keyPause, () -> {
			pauseGame();
		}));

		this.frame = new GameFrame();
		frame.setBounds(U.config().offsetX, U.config().offsetY, U.config().w, U.config().h);
		st.addActor(frame);

		this.pauseMenu = new Grid(0, 0, true, () -> Actions.parallel(Actions.fadeIn(0.3f), Actions.moveTo(0, 0, 0.2f)),
				() -> Actions.parallel(Actions.fadeOut(0.3f), Actions.moveTo(-30, 0, 0.3f)));
		st.addActor(pauseMenu);
		input.addProcessor(pauseMenu);
		pauseMenu.disable();
		pauseMenu.setColor(new Color(1, 1, 1, 0));

		pauseMenu.add(new GridLabel("Resume Game", 18, 50, 200, 200, 20, 0, 0, () -> {
			resumeGame();
		}));
		pauseMenu.add(new GridLabel("Retart Game", 18, 55, 170, 200, 20, 0, 1, () -> {
			startGame();
			resumeGame();
		}));
		pauseMenu.add(new GridLabel("Quit Game", 18, 60, 140, 200, 20, 0, 2, () -> {
			switchToStart();
		}));
		pauseMenu.selectFirst();

		startGame();

		this.paused = false;
	}

	@Override
	public void render(float delta) {
		if (!paused) {
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)
					&& (!J.isGameModeReplay() || U.config().allowSpeedUpOutOfReplay)) {
				for (int i = 0; i < U.config().speedUpMultiplier - 1; i++) {
					jade.preRender();
					jade.postRender();
				}
			}
			jade.preRender();
			jade.draw();

			// Important!!!! Or viewport will become stretched.
			Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
					viewport.getScreenHeight());
		}

		st.act();
		st.draw();

		if (!paused) {
			jade.postRender();
		}
	}

	@Override
	protected void onQuit() {

	}

	@Override
	public void hide() {
		U.removeProcessor(input);
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

	private void pauseGame() {
		paused = true;
		BGM.pause();
		frame.clearActions();
		frame.addAction(Actions.color(Color.GRAY, 0.3f));
		pauseMenu.enable();
		pauseMenu.activate();
	}

	private void resumeGame() {
		pauseMenu.disable();
		frame.clearActions();
		frame.addAction(Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.run(() -> {
			paused = false;
			BGM.resume();
		})));
	}

	private void switchToStart() {
		U.switchScreen("blank", 0.5f);
		Global.put("_redirect", "start");
		Global.put("_redirectDelay", 0.5f);
	}

	private void startGame() {
		if (jade != null) {
			jade.dispose();
		}
		jade = new Jade();

		if ("reimu".equals(Global.get("_player"))) {
			jade.setPlayer(new PlayerReimu());
		} else if ("marisa".equals(Global.get("_player"))) {
			jade.setPlayer(new PlayerMarisa());
		}

		if ("regular".equals(Global.get("_gameMode"))) {
			J.addTask(new DifficultyRegular((int) Global.get("_difficulty")));
		} else if ("extra".equals(Global.get("_gameMode"))) {
			J.addTask(new DifficultyExtra());
		}

		BGM.play(null);
		frame.setJade(jade);
	}

}
