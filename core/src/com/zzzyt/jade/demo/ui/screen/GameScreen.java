package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zzzyt.jade.demo.difficulty.DifficultyRegular;
import com.zzzyt.jade.demo.difficulty.DifficultyExtra;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.ui.GameFrame;
import com.zzzyt.jade.ui.KeyListener;
import com.zzzyt.jade.ui.YesNoMenu;
import com.zzzyt.jade.ui.grid.Grid;
import com.zzzyt.jade.ui.grid.GridButton;
import com.zzzyt.jade.ui.grid.GridLabel;
import com.zzzyt.jade.ui.screen.BasicScreen;
import com.zzzyt.jade.ui.screen.ScreenState;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	private Image bg;

	private Grid pauseMenu;
	private YesNoMenu yesNoMenu;

	public GameScreen() {
		super();
	}

	@Override
	public void show() {
		init(null);

		input.addProcessor(new KeyListener(U.config().keyPause, () -> {
			if (!jade.isPaused()) {
				pauseGame();
			}
		}));

		this.frame = new GameFrame();
		frame.setBounds(U.config().frameOffsetX, U.config().frameOffsetY, U.config().frameWidth,
				U.config().frameHeight);
		frame.setOrigin(frame.getWidth() / 2, frame.getHeight() / 2);
		st.addActor(frame);

		this.yesNoMenu = new YesNoMenu(500, 300);
		st.addActor(yesNoMenu);
		yesNoMenu.disable();
		yesNoMenu.setColor(new Color(1, 1, 1, 0));

		this.pauseMenu = new Grid(0, 0, true, true, () -> Actions.fadeIn(0.2f), () -> Actions.fadeOut(0.1f));
		pauseMenu.setPosition(-60, 0);
		st.addActor(pauseMenu);
		yesNoMenu.setParent(pauseMenu);
		pauseMenu.disable();
		pauseMenu.setColor(new Color(1, 1, 1, 0));

		pauseMenu.add(new GridButton("Resume Game", 36, 100, 400, 400, 40, 0, 1, () -> {
			resumeGame();
		}));
		pauseMenu.add(new GridButton("Retart Game", 36, 110, 340, 400, 40, 0, 2, () -> {
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
		pauseMenu.add(new GridButton("Quit Game", 36, 120, 280, 400, 40, 0, 3, () -> {
			yesNoMenu.setYes(() -> {
				yesNoMenu.deactivate();
				yesNoMenu.disable();
				pauseMenu.deactivate();
				pauseMenu.disable();
				switchToTitle();
			});
			yesNoMenu.activate();
			yesNoMenu.enable();
			yesNoMenu.selectFirst();
			pauseMenu.disable();
		}));
		pauseMenu.add(new GridLabel("Game Paused", 48, 90, 500, 400, 60, 0, 0));
		pauseMenu.updateComponent();
		pauseMenu.selectFirst();

		this.bg = new Image(A.getRegion("bg/game.png"));
		bg.setBounds(0, 0, U.config().windowWidth, U.config().windowHeight);
		st.addActor(bg);

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
			U.switchScreen("title");
		}
		jade.draw();

		// Important!!!! Or viewport will become stretched.
		Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)
				&& (!J.isGameModeReplay() || U.config().allowSpeedUpOutOfReplay)) {
			for (int i = 0; i < U.config().speedUpMultiplier - 1; i++) {
				st.act(U.safeDeltaTime());
			}
		}
		st.act(U.safeDeltaTime());
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
		pauseMenu.addAction(Actions.moveTo(-60, 0, 0.1f, Interpolation.sine));
	}

	private void switchToTitle() {
		SE.play("cancel");
		U.switchScreen("blank", 0.5f);
		Global.put("_redirect", "title");
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
