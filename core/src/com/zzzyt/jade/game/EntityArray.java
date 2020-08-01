package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.util.U;

/**
 * A wrapper for Array that allows simple modification
 * @author XGN
 *
 * @param <T>
 */
public class EntityArray<T extends Entity> {
	public Array<T> entities;
	/**
	 * The number of blank elements
	 */
	public int blankCount;
	
	/**
	 * The number of active elements
	 */
	public int count;
	
	public int size() {
		return entities.size;
	}
	
	public T get(int index) {
		return entities.get(index);
	}
	
	public void add(T t) {
		count++;
		t.internalId = entities.size;
		entities.add(t);
	}
	
	public void remove(T t) {
		if (t.internalId != entities.size - 1)
			blankCount++;
		count--;
		entities.set(t.internalId, null);
		t.internalId = -1;
	}
	
	public void set(int index,T value) {
		entities.set(index, value);
	}
	public EntityArray() {
		entities=new Array<>(false,1024);
	}
	
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < entities.size; i++) {
			if (entities.get(i) != null) {
				entities.get(i).draw(batch);
			}
		}
	}
	
	public void update(int frame) {
		for (int i = 0; i < entities.size; i++) {
			if (entities.get(i) != null) {
				entities.get(i).update(frame);
			}
		}
	}

	public void cleanUp() {
		U.cleanupArray(entities);
		for (int i = 0; i < entities.size; i++) {
			entities.get(i).internalId = i;
		}
		blankCount = 0;
	}


}
