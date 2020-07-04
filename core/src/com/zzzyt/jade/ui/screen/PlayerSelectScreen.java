package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.widget.GridImage;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class PlayerSelectScreen extends BasicScreen {

	private Grid grid;

	public PlayerSelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea29.ogg", A.findRegion("bg.atlas", "select"));

		this.grid = new Grid(true);
		st.addActor(grid);

		grid.add(new GridImage(A.findRegion("diff.atlas", "marisa_description"), 720, 100, 1, 0,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(340, 100, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(720, 100, 0.2f, Interpolation.sine)),
				null));
		grid.add(new GridImage(A.findRegion("diff.atlas", "marisa_portrait"), 360, 100, 1, 0,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(200, 100, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(360, 100, 0.2f, Interpolation.sine)),
				() -> {
					G.put("_player", "marisa");
					G.put("_redirect", "game");
					G.put("_redirectDelay", 0.5f);
					Game.switchScreen("blank", 0.5f);
				}));

		grid.add(new GridImage(A.findRegion("diff.atlas", "reimu_description"), 120, 100, 0, 0,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(120, 100, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(-360, 100, 0.2f, Interpolation.sine)),
				null));
		grid.add(new GridImage(A.findRegion("diff.atlas", "reimu_portrait"), 240, 100, 0, 0,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(240, 100, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(80, 100, 0.2f, Interpolation.sine)),
				() -> {
					G.put("_player", "reimu");
					G.put("_redirect", "game");
					G.put("_redirectDelay", 0.5f);
					Game.switchScreen("blank", 0.5f);
				}));

		if (G.get("_player") == null) {
			grid.selectLast();
		} else if ("reimu".equals(G.get("_player"))) {
			grid.select(0, 0);
		} else if ("marisa".equals(G.get("_player"))) {
			grid.select(1, 0);
		}

		grid.updateAll();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(100, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(100, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		Game.switchScreen("difficultySelect", 0.5f);
	}

	@Override
	public String getName() {
		return "playerSelect";
	}

}
