package com.zzzyt.jade.game;

public interface Entity extends Drawable {

	public float getX();

	public float getY();

	public Entity setX(float x);

	public Entity setY(float y);

	public Entity setXY(float x, float y);

}
