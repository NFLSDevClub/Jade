package com.zzzyt.jade.util.shot;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.util.A;

public class ShotSheetLoader extends SynchronousAssetLoader<ShotSheet, ShotSheetLoader.ShotSheetParameter> {
	public ShotSheetLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	RawShotSheet raw;

	@Override
	public ShotSheet load(AssetManager assetManager, String fileName, FileHandle file, ShotSheetParameter parameter) {
		ShotSheet sheet = new ShotSheet(assetManager.get(raw.image, Texture.class), raw);
		raw = null;
		return sheet;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle sheetFile, ShotSheetParameter parameter) {
		FileHandle imgDir = sheetFile.parent();

		raw = RawShotSheet.fromJson(sheetFile);

		Array<AssetDescriptor> dependencies = new Array();
		dependencies.add(new AssetDescriptor(imgDir.child(raw.image), Texture.class, A.defaultTextureParameter()));
		return dependencies;
	}

	static public class ShotSheetParameter extends AssetLoaderParameters<ShotSheet> {
		public ShotSheetParameter() {

		}
	}
}