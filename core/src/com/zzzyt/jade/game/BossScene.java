package com.zzzyt.jade.game;

import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.game.task.Spellcard;
import com.zzzyt.jade.util.J;

/**
 * Boss Scene object is the same as Danmakufu <br/>
 * It is used for collecting spellcards and display weird stuff about bosses <br/>
 * All enemies with "boss" marked will be considered to be a boss and will decrease the spellcard health<br/>
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

	public final static int MAXHP=1000000000;
	
	public BossScene(Array<Array<Spellcard>> phases) {
		this.phases=phases;
	}
	
	/**
	 * Must be called before using a boss scene
	 */
	public void init() {
		for(Array<Spellcard> sc:phases) {
			for(Spellcard s:sc) {
				s.init();
			}
		}
	}
	
	public void onSpellcardFinish() {
		// TODO effects
		getCurrentSpellcard().onEnd();
		
		for(Enemy e:J.getSession().enemies.entities) {
			if(e.isBoss) {
				e.hp=MAXHP;
			}
		}
		
		spellIndex++;
		if(spellIndex==phases.get(currentPhase).size) {
			onPhaseFinish();
		}
	}
	
	public void onPhaseFinish() {
		// TODO effects

		spellIndex=0;
		currentPhase++;
		
		if(currentPhase==phases.size) {
			onFinish();
		}
	}
	
	public void onFinish() {
		// TODO effects
	}
	
	public boolean isFinished() {
		return currentPhase==phases.size;
	}
	
	public void update(int t) {
		
		if(isFinished()) {
			return;
		}
		
		updateHP(t);
		updateSC(t);
	}

	
	private void updateHP(int t) {
		int lostHP=0;
		for(Enemy e:J.getSession().enemies.entities) {
			if(e.isBoss) {
				lostHP+=MAXHP-e.hp;
			}
		}
		
		getCurrentSpellcard().hp=getCurrentSpellcard().maxhp-lostHP;
	}
	
	public Spellcard getCurrentSpellcard() {
		return phases.get(currentPhase).get(spellIndex);
	}
	
	private void updateSC(int t) {
		Spellcard csc=getCurrentSpellcard();
		
		if(csc.isFinished()) {
			onSpellcardFinish();
		}
		
		csc=getCurrentSpellcard();
		
		csc.update(t);
	}

}
