package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Entity {
	public void draw(Batch batch);
	public void update();
	public float getX();
	public float getY();
	public void x(float x);
	public void y(float y);
	public void xy(float x,float y);
}
