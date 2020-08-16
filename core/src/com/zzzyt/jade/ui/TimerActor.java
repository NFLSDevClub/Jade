package com.zzzyt.jade.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.zzzyt.jade.demo.DemoEventManager;
import com.zzzyt.jade.game.task.Spellcard;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.J;

/**
 * Timer Actor will display the timer of the spellcard on screen
 * @author XGN
 *
 */
public class TimerActor extends Label {
	
	public Spellcard sc;
	
	public TimerActor(Spellcard sc) {
		
		super("00.00",A.getUILabelStyle(30));
		setOrigin(Align.center);
		setPosition(200, 600);
		addAction(Actions.moveBy(0,-200,1f,Interpolation.sineOut));
		
		this.sc=sc;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		//update timer
		float realtime=sc.timeLeft/60f;
		setText(String.format("%02d", (int)realtime)
				+"."
				+String.format("%02d", (int)(realtime*100)%100)
				+"\nHP:"+sc.hp+"/"+sc.intitialHealth
				+"\nBonus:"+sc.getBonus()
				+"\nLife:"+((DemoEventManager)(J.getEM())).playerLife
				+"\nBomb:"+((DemoEventManager)(J.getEM())).playerBomb
				+"\nScore:"+((DemoEventManager)(J.getEM())).score
				+"\nValue:"+((DemoEventManager)(J.getEM())).maxPoint);
		
		if(realtime<=5) {
			getStyle().fontColor=Color.RED;
		}else if(realtime<=10) {
			getStyle().fontColor=Color.PINK;
		}

//		if(J.getPlayer().getY()>=-200) {
//			addAction(Actions.alpha(0.5f,0.5f));
//		}else {
//			addAction(Actions.alpha(1f,0.5f));
//		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
}
