package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * A wrapper for Array that allows simple modification
 * @author XGN
 *
 * @param <T>
 */
public class EntityArray<T extends Entity> {
	public Array<T> entities;
	
	public int size() {
		return entities.size;
	}
	
	public T get(int index) {
		return entities.get(index);
	}
	
	public void add(T t) {
		entities.add(t);
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
}
