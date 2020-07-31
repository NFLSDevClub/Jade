package com.zzzyt.jade.demo.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.grid.GridButton;
import com.zzzyt.jade.ui.grid.ScrollingCenteredGrid;
import com.zzzyt.jade.ui.grid.ScrollingGrid;
import com.zzzyt.jade.ui.screen.BasicScreen;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;

public class OptionScreen extends BasicScreen {

	protected ScrollingGrid grid;
	protected ScrollingCenteredGrid musicVolume;
	protected ScrollingCenteredGrid SEVolume;

	public OptionScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.grid = new ScrollingGrid(true, new Rectangle(-2048, 64, 4096, 832));
		st.addActor(grid);

		grid.add(new GridButton("Music Vol", 48, 760, 340, 200, 60, 0, 0, null));
		musicVolume = new ScrollingCenteredGrid(false, 0, 0);
		musicVolume.setPosition(1060, 340);
		musicVolume.setGridX(0).setGridY(0);
		for (int ii = 0; ii <= 20; ii++) {
			final int i = ii;
			GridButton button = (GridButton) musicVolume
					.add(new GridButton(i * 5 + "%", 48, 0, 0, 200, 60, i, 0, null));
			button.setActiveAction(() -> Actions.parallel(
					Actions.sequence(Actions.show(),
							Actions.moveTo(button.staticX + 2, button.staticY, 0.03f, Interpolation.sine),
							Actions.moveTo(button.staticX - 4, button.staticY, 0.06f, Interpolation.sine),
							Actions.moveTo(button.staticX, button.staticY, 0.03f, Interpolation.sine)),
					Actions.forever(Actions.sequence(Actions.color(new Color(0.9f, 0.9f, 0.9f, 1f), 0.5f),
							Actions.color(Color.WHITE, 0.5f)))));
			button.setInactiveAction(() -> Actions.hide());
			button.setAction(() -> {
				U.config().musicVolume = (float) i / 20;
				BGM.setVolume((float) i / 20);
			});
		}
		musicVolume.select(M.clamp(M.round(U.config().musicVolume * 20), 0, 20), 0);
		musicVolume.update();
		input.addProcessor(musicVolume);
		grid.add(musicVolume);

		grid.add(new GridButton("S.E. Vol", 48, 740, 280, 200, 60, 0, 1, null));
		SEVolume = new ScrollingCenteredGrid(false, 0, 0);
		SEVolume.setPosition(1040, 280);
		SEVolume.setGridX(0).setGridY(1);
		for (int ii = 0; ii <= 20; ii++) {
			final int i = ii;
			GridButton button = (GridButton) SEVolume.add(new GridButton(i * 5 + "%", 48, 0, 0, 200, 60, i, 0, null));
			button.setActiveAction(() -> Actions.parallel(
					Actions.sequence(Actions.show(),
							Actions.moveTo(button.staticX + 2, button.staticY, 0.03f, Interpolation.sine),
							Actions.moveTo(button.staticX - 4, button.staticY, 0.06f, Interpolation.sine),
							Actions.moveTo(button.staticX, button.staticY, 0.03f, Interpolation.sine)),
					Actions.forever(Actions.sequence(Actions.color(new Color(0.9f, 0.9f, 0.9f, 1f), 0.5f),
							Actions.color(Color.WHITE, 0.5f)))));
			button.setInactiveAction(() -> Actions.hide());
			button.setAction(() -> {
				U.config().SEVolume = (float) i / 20;
			});
		}
		SEVolume.select(M.clamp(M.round(U.config().SEVolume * 20), 0, 20), 0);
		SEVolume.update();
		input.addProcessor(SEVolume);
		grid.add(SEVolume);

		grid.add(new GridButton("Key Config", 48, 720, 220, 200, 60, 0, 2, () -> {

		})).disable();

		grid.add(new GridButton("Default", 48, 700, 160, 200, 60, 0, 3, () -> {
			U.config().setDefault();
			BGM.setVolume(U.config().musicVolume);
		}));

		((GridButton)grid.add(new GridButton("Quit", 48, 680, 100, 200, 60, 0, 4, () -> {
			SE.play("cancel");
			U.switchScreen("title", 0.5f);
		}))).setSound(false);

		grid.selectFirst();
		grid.activate();
		grid.updateComponent();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(400, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(400, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		if (grid.selectedY == 4) {
			SE.play("cancel");
			U.switchScreen("title", 0.5f);
			super.onQuit();
		} else {
			grid.select(0, 4);
		}
	}

	@Override
	public String getName() {
		return "option";
	}

}