package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public interface Drawable extends Updatable{

	public void draw(Batch batch);

	public int getZIndex();

	public static int binarySearch(Array<Drawable> a, int key) {
		int low = 0;
		int high = a.size - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			Drawable tmp = a.get(mid);
			if (tmp.getZIndex() < key)
				low = mid + 1;
			else if (tmp.getZIndex() > key)
				high = mid - 1;
			else
				return mid;
		}
		return low;
	}

}
