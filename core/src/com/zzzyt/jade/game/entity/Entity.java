package com.zzzyt.jade.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Entity {
	public void draw(Batch batch);
	public void update(int frame);
	public float getX();
	public float getY();
	public Entity setX(float x);
	public Entity setY(float y);
	public Entity setXY(float x,float y);
}
