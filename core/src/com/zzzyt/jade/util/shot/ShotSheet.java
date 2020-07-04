package com.zzzyt.jade.util.shot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.util.U;

public class ShotSheet {
	public Texture texture;
	public TextureRegion delay;
	public ObjectMap<Integer, BulletData> data;
	public ObjectMap<String, BulletData> data2;

	public ShotSheet(String internalSheetFile) {
		this(Gdx.files.internal(internalSheetFile));
	}

	public ShotSheet(FileHandle sheetFile) {
		this(sheetFile, U.fromJson(sheetFile, RawShotSheet.class));
	}

	public ShotSheet(FileHandle sheetFile, RawShotSheet raw) {
		this(new Texture(sheetFile.parent().child(raw.image)), raw);
	}

	public ShotSheet(Texture texture, RawShotSheet raw) {
		this.texture = texture;
		this.delay = new TextureRegion(texture, raw.delayRect[0], raw.delayRect[1], raw.delayRect[2] - raw.delayRect[0],
				raw.delayRect[3] - raw.delayRect[1]);
		;
		this.data = new ObjectMap<Integer, BulletData>();
		this.data2 = new ObjectMap<String, BulletData>();
		for (int i = 0; i < raw.data.length; i++) {
			BulletData tmp = new BulletData(this, raw.data[i]);
			data.put(tmp.id, tmp);
			if (tmp.name != null) {
				data2.put(tmp.name, tmp);
			}
		}
	}

	public BulletData findBullet(int id) {
		return data.get(id);
	}

	public BulletData findBullet(String name) {
		return data2.get(name);
	}
}
