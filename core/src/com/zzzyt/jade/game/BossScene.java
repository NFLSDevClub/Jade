package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.demo.JadeDemoApplication;
import com.zzzyt.jade.demo.ui.screen.GameScreen;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.game.task.Spellcard;
import com.zzzyt.jade.ui.TimerActor;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

/**
 * Boss Scene object is the same as Danmakufu <br/>
 * It is used for collecting spellcards and display weird stuff about bosses
 * <br/>
 * All enemies with "boss" marked will be considered to be a boss and will
 * decrease the spellcard health<br/>
 * 
 * @author XGN
 *
 */
public class BossScene {
	/**
	 * Phases is an <b>ordered</b> array
	 */
	public Array<Array<Spellcard>> phases;
	public int currentPhase;
	public int spellIndex;

	public final static double MAXHP = 1e9;

	public Group st;

	/**
	 * Player have died/bombed in the current spellcard if this is true.
	 */

	public BossScene(Array<Array<Spellcard>> phases) {
		this();
		this.phases = phases;
	}

	public BossScene() {
		st = new Group();
	}

	/**
	 * Must be called before using a boss scene
	 */
	public void init() {
		for (Array<Spellcard> sc : phases) {
			for (Spellcard s : sc) {
				s.init();
			}
		}

		for (Enemy e : J.getSession().enemies.entities) {
			if (e != null && e.isBoss) {
				e.hp = MAXHP;
			}
		}

		// add the first SC information
		GameScreen tmp = (GameScreen) JadeDemoApplication.self.screens.get(2);
		tmp.st.addActor(new TimerActor(getCurrentSpellcard()));
	}

	public void onSpellcardFinish() {
		// TODO effects
		GameScreen tmp = (GameScreen) JadeDemoApplication.self.screens.get(2);
		J.getEM().onSpellcardFinish(getCurrentSpellcard(), this);

		// remove old actors
		for (Actor a : tmp.st.getActors()) {
			if (a instanceof TimerActor) {
				a.addAction(Actions.sequence(
						Actions.parallel(Actions.moveBy(0, -350, 0.5f, Interpolation.sineIn), Actions.fadeOut(0.5f)),
						Actions.removeActor()));
			}
		}

		getCurrentSpellcard().onEnd();

		for (Enemy e : J.getSession().enemies.entities) {
			if (e != null && e.isBoss) {
				e.hp = MAXHP;
			}
		}

		spellIndex++;
		if (spellIndex == phases.get(currentPhase).size) {
			onPhaseFinish();
		}

		// add new actor
		if (!isFinished()) {
			tmp.st.addActor(new TimerActor(getCurrentSpellcard()));
		}
	}

	public void onPhaseFinish() {
		// TODO effects

		J.getEM().onPhaseFinish(this);

		spellIndex = 0;
		currentPhase++;

		if (currentPhase == phases.size) {
			onFinish();
		}
	}

	public void onFinish() {
		// TODO effects
	}

	public boolean isFinished() {
		return currentPhase == phases.size;
	}

	public void draw(Batch batch, float a) {
		// TODO Draw something

		// draw circles
		int leftHP = 0, totalHP = 0;
		for (int i = 0; i < phases.get(currentPhase).size; i++) {
			if (i < spellIndex) {
				totalHP += phases.get(currentPhase).get(i).intitialHealth;
			} else {
				totalHP += phases.get(currentPhase).get(i).intitialHealth;
				leftHP += phases.get(currentPhase).get(i).hp;
			}
		}

//		System.out.println(leftHP+" "+totalHP);

		st.act(U.safeDeltaTime());
		st.draw(batch, a);

	}

	public void update(int t) {

		if (isFinished()) {
			return;
		}

//		System.out.println("boss state:"+currentPhase+" "+spellIndex+" "+getCurrentSpellcard());
		updateHP(t);
		updateSC(t);
	}

	private void updateHP(int t) {
		if (getCurrentSpellcard().isSurvival) {
			return;
		}

		int lostHP = 0;
		for (Enemy e : J.getSession().enemies.entities) {
			if (e != null && e.isBoss) {
				lostHP += MAXHP - e.hp;
			}
		}

		getCurrentSpellcard().hp = getCurrentSpellcard().intitialHealth - lostHP;
	}

	public Spellcard getCurrentSpellcard() {
		return phases.get(currentPhase).get(spellIndex);
	}

	private void updateSC(int t) {
		Spellcard csc = getCurrentSpellcard();

		if (csc.isFinished()) {
			onSpellcardFinish();
		}

		if (!isFinished()) {
			csc = getCurrentSpellcard();

			csc.update(t);
		}
	}

}
