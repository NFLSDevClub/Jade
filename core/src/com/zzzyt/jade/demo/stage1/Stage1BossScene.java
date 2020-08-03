package com.zzzyt.jade.demo.stage1;

import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.BossScene;
import com.zzzyt.jade.game.task.Spellcard;
import com.zzzyt.jade.util.U;

public class Stage1BossScene extends BossScene {

	public Stage1BossScene() {
		super();
		
		Array<Spellcard> phase1=new Array<>();
		
		phase1.add(new Spellcard(1000, 60*60, false, 1000, new Stage1Mid1()));
		phase1.add(new Spellcard(1000, 60*60, false, 1000, new Stage1Mid2()));
		
		Array<Spellcard> phase2=new Array<>();
		
		phase2.add(new Spellcard(1000, 60*60, false, 1000, new Stage1Mid1()));
		phase2.add(new Spellcard(1000, 60*60, true, 1000, new Stage1Mid2()));
		
		phases=U.makeArray(phase1,phase2);
	}

}
