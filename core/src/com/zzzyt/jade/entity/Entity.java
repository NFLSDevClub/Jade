package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Entity {
	public void draw(Batch batch);
	public void update();
	public float getX();
	public float getY();
	public Entity setX(float x);
	public Entity setY(float y);
	public Entity setXY(float x,float y);
}
