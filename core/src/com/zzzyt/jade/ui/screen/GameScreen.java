package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.demo.difficulty.DifficultyRegular;
import com.zzzyt.jade.demo.difficulty.DifficultyExtra;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.ui.GameFrame;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.ui.KeyListener;
import com.zzzyt.jade.ui.YesNoMenu;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.BGM;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	private Grid pauseMenu;
	private YesNoMenu yesNoMenu;

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
		frame.setOrigin(frame.getWidth() / 2, frame.getHeight() / 2);
		st.addActor(frame);

		this.yesNoMenu = new YesNoMenu(250, 150);
		st.addActor(yesNoMenu);
		yesNoMenu.disable();
		yesNoMenu.setColor(new Color(1, 1, 1, 0));

		this.pauseMenu = new Grid(0, 0, true, () -> Actions.fadeIn(0.2f), () -> Actions.fadeOut(0.1f));
		pauseMenu.setPosition(-30, 0);
		st.addActor(pauseMenu);
		yesNoMenu.setParent(pauseMenu);
		pauseMenu.disable();
		pauseMenu.setColor(new Color(1, 1, 1, 0));

		pauseMenu.add(new GridButton("Resume Game", 18, 50, 200, 200, 20, 0, 0, () -> {
			resumeGame();
		}));
		pauseMenu.add(new GridButton("Retart Game", 18, 55, 170, 200, 20, 0, 1, () -> {
			yesNoMenu.setYes(() -> {
				yesNoMenu.deactivate();
				yesNoMenu.disable();
				startGame();
				resumeGame();
			});
			yesNoMenu.activate();
			yesNoMenu.enable();
			yesNoMenu.selectFirst();
			pauseMenu.disable();
		}));
		pauseMenu.add(new GridButton("Quit Game", 18, 60, 140, 200, 20, 0, 2, () -> {
			yesNoMenu.setYes(() -> {
				yesNoMenu.deactivate();
				yesNoMenu.disable();
				pauseMenu.deactivate();
				pauseMenu.disable();
				switchToStart();
			});
			yesNoMenu.activate();
			yesNoMenu.enable();
			yesNoMenu.selectFirst();
			pauseMenu.disable();
		}));
		pauseMenu.updateComponent();
		pauseMenu.selectFirst();

		input.addProcessor(pauseMenu);
		input.addProcessor(yesNoMenu);
		startGame();
	}

	@Override
	public void render(float delta) {
		if (jade.isRunning() && !jade.isPaused()) {
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)
					&& (!J.isGameModeReplay() || U.config().allowSpeedUpOutOfReplay)) {
				for (int i = 0; i < U.config().speedUpMultiplier - 1; i++) {
					jade.update();
				}
			}
			jade.update();
		}
		if (!jade.isRunning()) {
			U.switchScreen("start");
		}
		jade.draw();

		// Important!!!! Or viewport will become stretched.
		Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());

		st.act();
		st.draw();
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
		jade.pause();
		BGM.pause();
		frame.clearActions();
		frame.addAction(Actions.color(Color.GRAY, 0.3f));
		pauseMenu.enable();
		pauseMenu.activate();
		pauseMenu.selectFirst();
		pauseMenu.addAction(Actions.moveTo(0, 0, 0.2f, Interpolation.sine));
	}

	private void resumeGame() {
		frame.clearActions();
		frame.addAction(Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.run(() -> {
			jade.resume();
			BGM.resume();
		})));
		pauseMenu.disable();
		pauseMenu.deactivate();
		pauseMenu.addAction(Actions.moveTo(-30, 0, 0.1f, Interpolation.sine));
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

		if (J.isGameModeRegular()) {
			J.addTask(new DifficultyRegular((int) Global.get("_difficulty")));
		} else if (J.isGameModeExtra()) {
			J.addTask(new DifficultyExtra());
		} else if (J.isGameModeSpellPractice() || J.isGameModeStagePractice()) {
			J.addTask((Task) Global.get("_practice"));
		}

		BGM.play(null);
		frame.setJade(jade);
	}

}
