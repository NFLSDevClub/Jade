package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.ui.widget.GameFrame;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	public GameScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea12.ogg", A.getRegion("bg/game.png"));

		this.frame = new GameFrame();
		frame.setBounds(Config.offsetX, Config.offsetY, Config.w, Config.h);
		st.addActor(frame);

		this.jade = new Jade();

		if ("reimu".equals(G.get("_player"))) {
			jade.setPlayer(new PlayerReimu());
		} else if ("marisa".equals(G.get("_player"))) {
			jade.setPlayer(new PlayerMarisa());
		}

		frame.setJade(jade);
	}

	@Override
	public void render(float delta) {
		if (MathUtils.randomBoolean(diffToChance((String) G.get("_difficulty")))) {
			B.as(MathUtils.random(1, 248), 0, 2, MathUtils.random(-150, 150), MathUtils.random(-100, 0), 1,
					MathUtils.random(-150, -30));
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

	private float diffToChance(String str) {
		switch (str) {
		case "easy":
			return 0.1f;
		case "normal":
			return 0.25f;
		case "hard":
			return 0.5f;
		case "lunatic":
			return 1f;
		default:
			return 0;
		}
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
