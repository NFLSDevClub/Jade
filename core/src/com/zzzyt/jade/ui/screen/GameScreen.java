package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.entity.SquareBullet;
import com.zzzyt.jade.entity.BasicPlayerBuilder;
import com.zzzyt.jade.ui.QuitListener;
import com.zzzyt.jade.ui.widget.FPSDisplay;
import com.zzzyt.jade.ui.widget.GameFrame;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class GameScreen implements FadeableScreen {

	public Jade jade;

	public Viewport viewport;
	public Stage st;
	public InputMultiplexer input;

	private Image background;
	private FPSDisplay fps;
	private GameFrame frame;

	private BasicPlayerBuilder reimu, marisa;

	private ScreenState state;

	public GameScreen() {
		this.viewport = new ScalingViewport(Scaling.fit, Config.windowWidth, Config.windowHeight);
		this.state = ScreenState.HIDDEN;
	}

	@Override
	public void show() {
		BGM.play("mus/Idea12.ogg");

		if (reimu == null) {
			this.reimu = new BasicPlayerBuilder();
			reimu.fromAtlas(A.get("player/th10_player.atlas"), "th10_reimu", 5, 2).data(2, 4.5f, 2)
					.hitbox(new Sprite((Texture) A.get("player/hitbox.png")));
		}
		if (marisa == null) {
			this.marisa = new BasicPlayerBuilder();
			marisa.fromAtlas(A.get("player/th10_player.atlas"), "th10_marisa", 5, 2).data(3.5f, 5, 2)
					.hitbox(new Sprite((Texture) A.get("player/hitbox.png")));
		}

		this.st = new Stage(viewport);
		this.input = new InputMultiplexer();

		this.background = new Image((Texture) A.get("bg/game_bg.png"));
		background.setZIndex(0);
		st.addActor(background);

		this.fps = new FPSDisplay();
		st.addActor(fps);

		this.frame = new GameFrame();
		frame.setBounds(Config.offsetX, Config.offsetY, Config.w, Config.h);
		st.addActor(frame);

		input.addProcessor(st);
		input.addProcessor(new QuitListener(() -> {
			Game.switchScreen("start");
		}));

		this.jade = new Jade();

		if ("reimu".equals(G.get("_selectedPlayer"))) {
			jade.setPlayer(reimu.build());
		} else if ("marisa".equals(G.get("_selectedPlayer"))) {
			jade.setPlayer(marisa.build());
		}

		frame.setJade(jade);

		Game.addProcessor(input);
	}

	@Override
	public void render(float delta) {
		for(int i=0;i<1;i++) {
			if (MathUtils.randomBoolean()) {
				jade.add(jade.newRoundBullet(new TextureRegion(A.get("bu/testbullet2.png", Texture.class)), 0, 4))
				.setXY(MathUtils.random(-150, 150), MathUtils.random(-100, 0))
				.setDir(MathUtils.random(-150, -30))
				.setSpeed(1)
				.setScale(1f).setColor(new Color(MathUtils.random(), 0, 1, 1));
			} else {
				jade.add(new SquareBullet(new TextureRegion(A.get("bu/rectbullet.png", Texture.class)), 0, 8,
						MathUtils.random(-150, 150), MathUtils.random(-100, 0), 1, MathUtils.random(-150, -30)))
						.setScale(0.5f).setColor(new Color(MathUtils.random(), 0, 1, 1));
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
		if (jade != null)
			jade.dispose();
		if (st != null)
			st.dispose();
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean fadeOut(float duration) {
		state = ScreenState.FADING_OUT;
		st.getRoot().addAction(Actions.sequence(Actions.delay(duration), Actions.run(() -> {
			hide();
		})));
		return true;
	}

	@Override
	public boolean fadeIn(float duration) {
		state = ScreenState.FADING_IN;
		show();
		st.getRoot().getColor().a = 0;
		st.getRoot().addAction(Actions.sequence(Actions.fadeIn(duration), Actions.run(() -> {
			state = ScreenState.SHOWN;
		})));
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
		return "game";
	}

}
