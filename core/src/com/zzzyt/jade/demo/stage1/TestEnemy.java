package com.zzzyt.jade.demo.stage1;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.zzzyt.jade.game.entity.Item;
import com.zzzyt.jade.game.entity.TaskedEnemy;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.J;

public class TestEnemy extends TaskedEnemy {

	public TestEnemy(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float x,
			float y, float hp, float radiusS, float radiusP, boolean isBoss) {
		super(atlas, regionName, frameLength, transitionFrameLength, x, y, hp, radiusS, radiusP, isBoss);
	}

	@Override
	public void onDie() {
		
		Item itm=new Item(A.getTexture("item/point.png"), 0, 10,x,y);
		J.add(itm);
		super.onDie();
	}
}
