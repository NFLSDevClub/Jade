package com.zzzyt.jade.game;

import com.zzzyt.jade.util.Collision.CollisionData;

public abstract class Entity implements Drawable {

	public int internalId = Integer.MAX_VALUE;

	public abstract float getX();

	public abstract float getY();

	public abstract Entity setX(float x);

	public abstract Entity setY(float y);

	public abstract Entity setXY(float x, float y);

	public abstract CollisionData getCollisionData(Entity other);

}
