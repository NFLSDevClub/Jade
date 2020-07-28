package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.grid.Grid;
import com.zzzyt.jade.ui.grid.GridImage;
import com.zzzyt.jade.ui.screen.BasicScreen;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Global;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class PlayerSelectScreen extends BasicScreen {

	private Grid grid;

	public PlayerSelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.grid = new Grid(true);
		st.addActor(grid);

		grid.add(new GridImage(A.getRegion("diff/marisa_description.png"), 1440, 200, 1, 0, true,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(680, 200, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(1440, 200, 0.2f, Interpolation.sine)),
				null));
		grid.add(new GridImage(A.getRegion("diff/marisa_portrait.png"), 720, 200, 1, 0, true,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(400, 200, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(720, 200, 0.2f, Interpolation.sine)),
				() -> {
					Global.put("_player", "marisa");
					Global.put("_redirect", "game");
					Global.put("_redirectDelay", 0.5f);
					U.switchScreen("blank", 0.5f);
				}));

		grid.add(new GridImage(A.getRegion("diff/reimu_description.png"), 240, 200, 0, 0, true,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(240, 200, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(-720, 200, 0.2f, Interpolation.sine)),
				null));
		grid.add(new GridImage(A.getRegion("diff/reimu_portrait.png"), 480, 200, 0, 0, true,
				() -> Actions.sequence(Actions.color(Color.WHITE), Actions.moveTo(480, 200, 0.2f, Interpolation.sine)),
				() -> Actions.sequence(Actions.color(Color.GRAY), Actions.moveTo(160, 200, 0.2f, Interpolation.sine)),
				() -> {
					Global.put("_player", "reimu");
					Global.put("_redirect", "game");
					Global.put("_redirectDelay", 0.5f);
					U.switchScreen("blank", 0.5f);
				}));
		grid.activate();
		if (Global.get("_player") == null) {
			grid.selectLast();
		} else if ("reimu".equals(Global.get("_player"))) {
			grid.select(0, 0);
		} else if ("marisa".equals(Global.get("_player"))) {
			grid.select(1, 0);
		}
		grid.updateComponent();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(200, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(200, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		if (J.isGameModeSpellPractice()) {
			U.switchScreen("spellSelect", 0.5f);
		} else if (J.isGameModeStagePractice()) {
			U.switchScreen("stageSelect", 0.5f);
		} else {
			U.switchScreen("difficultySelect", 0.5f);
		}
		super.onQuit();
	}

	@Override
	public String getName() {
		return "playerSelect";
	}

}
