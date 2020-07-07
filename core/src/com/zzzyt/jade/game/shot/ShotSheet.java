package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class ShotSheet {
	public Texture texture;
	public TextureRegion delay;
	public ObjectMap<Integer, BulletData> data;
	public ObjectMap<String, Integer> nameToId;

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
		Integer tmp=nameToId.get(name);
		if(tmp==null) {
			J.getLogger().error("Shot data of name\""+name+"\" not found!");
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
