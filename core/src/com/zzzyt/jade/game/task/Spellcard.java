package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Updatable;
import com.zzzyt.jade.util.J;

/**
 * A spellcard
 * @author XGN
 *
 */
public class Spellcard {
	public int maxhp;
	public int hp;
	
	public int maxtime;
	public int time;
	
	public boolean isSurvival;
	public Updatable updatable;
	
	public long bonus;
	private int firstT=-1;
	
	public Spellcard(int maxhp, int maxtime, boolean isSurvival,long bonus, Updatable updatable) {
		super();
		this.maxhp = maxhp;
		this.maxtime = maxtime;
		this.isSurvival = isSurvival;
		this.bonus=bonus;
		this.updatable = updatable;
	}

	public void init() {
		this.hp=this.maxhp;
		this.time=this.maxtime;
		firstT=-1;
	}
	
	public void update(int t) {
		if(firstT==-1) {
			firstT=t;
		}
		updatable.update(t-firstT);
		
		time-=t;
	}
	
	public void onEnd() {
		J.clearBullets();
	}
	
	public boolean isFinished() {
		return time<=0 || hp<=0;
	}
	
	/**
	 * Does not count if player died/bombed
	 * @return - the current spellcard bonus score
	 */
	public long getBonus() {
		if(isSurvival) {
			return bonus;
		}
		
		/*
		 * according to DDC
		 * delta score=max¡Â(time-300f)x(2/3)
		 */
		if(this.maxtime-this.time<=300) {
			return bonus;
		}else {
			long delta=(long)(bonus/(maxtime-300.0)*(2/3.0));
			return bonus-delta*(this.maxtime-this.time-300);
		}
	}
}
