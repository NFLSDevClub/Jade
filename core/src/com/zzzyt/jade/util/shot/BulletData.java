package com.zzzyt.jade.util.shot;

import com.badlogic.gdx.graphics.Color;
import com.zzzyt.jade.util.shot.RawShotSheet.RawBulletData;

public class BulletData {
	public int id;
	public String render;
	public String name;
	public BulletTexture texture;
	public float angularVelocity;
	public float radius;
	public Color delayColor;

	public BulletData(ShotSheet parent, RawBulletData raw) {
		this.id = raw.id;
		this.name = raw.name;
		this.render = raw.render;
		this.angularVelocity = raw.angularVelocity;
		this.delayColor = new Color(raw.delayColor[0], raw.delayColor[1], raw.delayColor[2], 1);
		if (raw.animation == null) {
			this.texture = new BulletTexture(parent.texture, raw.rect);
		} else {
			this.texture = new BulletTexture(parent.texture, raw.animation);
		}
		this.radius=Math.max((float)Math.min(texture.getRegionWidth(), texture.getRegionHeight())/3-3,3);
	}
}
