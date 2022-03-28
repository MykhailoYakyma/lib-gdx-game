package com.mykhailo.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.sun.tools.sjavac.Log;

public class Game implements ApplicationListener {

	private static final int FRAME_COLS = 9, FRAME_ROWS = 4;

	Animation<TextureRegion> walkAnimation, AbAnimation, IzAnimation, DeAnimation, ArAnimation;
	Texture walkSheet, background, shuriken;
	TextureRegion currentFrame;
	SpriteBatch spriteBatch;
	TextureRegion bgRegion;
	int fonsx, fonsy, x, y, carx, cary, test, balax, balay, timerb, db;
	boolean bulletExt = false;
	float stateTime;

	@Override
	public void create() {

		walkSheet = new Texture(Gdx.files.internal("shkelet.png"));

		shuriken = new Texture(Gdx.files.internal("shuriken1.png"));

		background = new Texture(Gdx.files.internal("background.png"));
		background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		bgRegion = new TextureRegion(background,0,0);
		fonsx = 10000;
		fonsy = 10000;
		x=-5000;
		y=-5000;
		carx = 300;
		cary = 250;
		timerb = 0;
		bulletExt = false;

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		TextureRegion[] up = new TextureRegion[9];
		for (int i = 0; i < FRAME_COLS; i++){
			up[i] = tmp[0][i];
		}
		TextureRegion[] left = new TextureRegion[9];
		for (int i = 0; i < FRAME_COLS; i++){
			left[i] = tmp[1][i];
		}
		TextureRegion[] down = new TextureRegion[9];
		for (int i = 0; i < FRAME_COLS; i++){
			down[i] = tmp[2][i];
		}
		TextureRegion[] right = new TextureRegion[9];
		for (int i = 0; i < FRAME_COLS; i++){
			right[i] = tmp[3][i];
		}

		walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
		AbAnimation = new Animation<TextureRegion>(0.1f,down);
		IzAnimation = new Animation<TextureRegion>(0.1f,left);
		DeAnimation = new Animation<TextureRegion>(0.1f,right);
		ArAnimation = new Animation<TextureRegion>(0.1f,up);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		bgRegion.setRegion(0,0,fonsx,fonsy);
		currentFrame = AbAnimation.getKeyFrame(1, true);

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			test++;
			currentFrame = DeAnimation.getKeyFrame(stateTime, true);
			x=x-5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			test++;
			currentFrame = IzAnimation.getKeyFrame(stateTime, true);
			x=x+5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			test++;
			currentFrame = ArAnimation.getKeyFrame(stateTime, true);
			y=y-5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			test++;
			currentFrame = AbAnimation.getKeyFrame(stateTime, true);
			y=y+5;
		}

		spriteBatch.begin();
		spriteBatch.draw(bgRegion,x,y);
		spriteBatch.draw(currentFrame, carx, cary);
		if (!bulletExt) {
			balax = carx;
			balay = cary;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				spriteBatch.draw(shuriken, balax, balay);
				bulletExt = true;
				if (currentFrame == DeAnimation.getKeyFrame(stateTime, true)) {
					db=1;
				}
				else if (currentFrame == IzAnimation.getKeyFrame(stateTime, true)) {
					db=2;
				}
				else if (currentFrame == ArAnimation.getKeyFrame(stateTime, true)) {
					db=3;
				}
				else if (currentFrame == AbAnimation.getKeyFrame(stateTime, true)) {
					db=4;
				}
			}
		}
		if (bulletExt) {
			if (db == 1) {
				balax = balax+10;
			} else if (db == 2) {
				balax = balax-10;
			} else if (db == 3) {
				balay = balay+10;
			} else if (db == 4) {
				balay = balay-10;
			}
			if (timerb < 20){
				timerb++;
				spriteBatch.draw(shuriken,balax,balay);
			} else if (timerb == 20){
				bulletExt = false;
				timerb = 0;
			}
		}
		spriteBatch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
	}
}