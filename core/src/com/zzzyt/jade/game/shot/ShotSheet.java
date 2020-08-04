package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class ShotSheet {
	public TextureAtlas atlas;
	public ObjectMap<Integer, BulletData> data;
	public ObjectMap<String, Integer> nameToId;

	public ShotSheet(String internalSheetFile) {
		this(Gdx.files.internal(internalSheetFile));
	}

	public ShotSheet(FileHandle sheetFile) {
		this(sheetFile, U.fromJson(sheetFile, RawShotSheet.class));
	}

	public ShotSheet(FileHandle sheetFile, RawShotSheet raw) {
		this(new TextureAtlas(sheetFile.parent().child(raw.atlas)), raw);
	}

	public ShotSheet(TextureAtlas atlas, RawShotSheet raw) {
		this.atlas = atlas;
		this.data = new ObjectMap<Integer, BulletData>();
		this.nameToId = new ObjectMap<String, Integer>();
		for (int i = 0; i < raw.data.length; i++) {
			BulletData tmp = new BulletData(this, raw.data[i]);
			data.put(tmp.id, tmp);
			if (tmp.name != null) {
				nameToId.put(tmp.name, tmp.id);
			}
		}
	}

	public int getId(String name) {
		Integer tmp = nameToId.get(name);
		if (tmp == null) {
			J.getLogger().error("Shot data of name\"" + name + "\" not found!");
		}
		return tmp;
	}

	public BulletData findBullet(int id) {
		return data.get(id);
	}

	public BulletData findBullet(String name) {
		return data.get(nameToId.get(name));
	}
}
