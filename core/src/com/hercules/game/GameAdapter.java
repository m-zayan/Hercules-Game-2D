/** @author Z. Mohamed Osama */

package com.hercules.game;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.engine.world.BodyProperty;
import com.engine.world.World2D;
import com.hercules.init.World;

public class GameAdapter extends ApplicationAdapter {

	public static final String TITEL = "Hercules"; // Screen Title

	public static final int V_WIDTH = 960; // Center X
	public static final int V_HEIGHT = 540; // Center Y
	public static final int SCALE = 2; // Center Scale

	public static final float GU = 100.0f; // Box2D Game-Unit Scaler
	public static final float FPS = 1 / 60f; // Frame per second (Delta Time)

	// Test World
	public static final String mapDir = "./epic-scene/map/";

	public static final String[] mapFname = { "level-1.tmx" };
	public static final String[] mapId = { "1" };

	World world;
	public static final float gravityX = 0.0f;
	public static final float gravityY = -9.81f;
	public static final short[] categoryBits = { World.BIT_GROUND, World.BIT_RANDOM_OBJECTS };
	public static final short[] bitsMask = { World.BIT_RANDOM_OBJECTS, World.BIT_GROUND };

	HashMap<Integer, BodyProperty> bodyTypeSignature;

	World2D world2d; // game world

	OrthographicCamera envCam;
	OrthographicCamera playerCam;
	OrthographicCamera box2dCam; // debug

	@Override
	public void create() {

		float width = Gdx.graphics.getWidth() / GU;
		float height = Gdx.graphics.getHeight() / GU;

		envCam = new OrthographicCamera();
		envCam.setToOrtho(false);

		box2dCam = new OrthographicCamera(width, height); // box2d debugging camera

		box2dCam.position.x = V_WIDTH / GU;
		box2dCam.position.y = V_HEIGHT / GU;
		box2dCam.update();

		bodyTypeSignature = new HashMap<Integer, BodyProperty>();

		// bodyTypeSignature: for each layer
		bodyTypeSignature.put(0, null);
		bodyTypeSignature.put(1, null);
		bodyTypeSignature.put(2, new BodyProperty(BodyType.StaticBody, 0.0f, 0.0f, 0.0f, categoryBits[0], bitsMask[0]));
		bodyTypeSignature.put(3,
				new BodyProperty(BodyType.DynamicBody, 1.0f, 0.0f, 0.0f, categoryBits[1], bitsMask[1]));

		world = new World(mapDir, mapFname, mapId);

		world2d = world.getWorldFromMap("1", bodyTypeSignature, gravityX, gravityY, true, GU);

		// Player
	}

	@Override
	public void render() {

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {

			Gdx.app.exit();
		}

		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.renderMap("1", envCam);
		envCam.update();

		world2d.renderBox2dDebug(box2dCam.combined);
		world2d.update(FPS, 6, 2);

		box2dCam.update();
	}

	@Override
	public void dispose() {

	}

}
