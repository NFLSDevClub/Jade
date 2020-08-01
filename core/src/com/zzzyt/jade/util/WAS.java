package com.zzzyt.jade.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * WAS= Walking Animation System
 * @author XGN
 *
 */
public class WAS {

	public transient Array<? extends TextureRegion> left, center, right, toLeft, toRight;
	
	public int pos;
	public int frameLength;
	public int transitionFrameLength;
	
	public int timer1,timer2;
	
	
	public WAS(Array<? extends TextureRegion> left, Array<? extends TextureRegion> center,
			Array<? extends TextureRegion> right, Array<? extends TextureRegion> toLeft,
			Array<? extends TextureRegion> toRight, int frameLength, int transitionFrameLength) {
		this.left = left;
		this.center = center;
		this.right = right;
		this.toLeft = toLeft;
		this.toRight = toRight;
		this.frameLength = frameLength;
		this.transitionFrameLength = transitionFrameLength;
		
//		System.out.println(left+" "+center+" "+right+" "+toLeft+" "+toRight+" "+frameLength+" "+transitionFrameLength);
	}

	public TextureRegion getTexture() {
		if (pos == -toLeft.size * transitionFrameLength) {
			return left.get(timer2);
		}
		if (pos < 0) {
			return toLeft.get((-pos + transitionFrameLength - 1) / transitionFrameLength - 1);
		}
		if (pos == 0) {
			return center.get(timer2);
		}
		if (pos == toRight.size * transitionFrameLength) {
			return right.get(timer2);
		}
		if (pos > 0) {
			return toRight.get((pos + transitionFrameLength - 1) / transitionFrameLength - 1);
		}
		return center.get(timer2);
	}
	
	public void update(int t,int dx) {
		timer1 = t % frameLength;
		if (dx < 0) {
			if (pos > -toLeft.size * transitionFrameLength) {
				pos--;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % left.size;
				}
			}
		} else if (dx > 0) {
			if (pos < toRight.size * transitionFrameLength) {
				pos++;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % right.size;
				}
			}
		} else {
			if (pos > 0) {
				pos--;
				timer2 = 0;
			} else if (pos < 0) {
				pos++;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % center.size;
				}
			}
		}
	}
}
