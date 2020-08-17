package com.engine.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.engine.math.Vec;

public class KinematicBody extends Body2D {

	public static final BodyType bdtype = BodyType.KinematicBody;

	public KinematicBody() {

		super(bdtype);

	}

	public void createBody(World world, float posX, float posY, Shape shape, boolean scale, short categoryBits,
			short[] bitsMask) {

		Vector2 pos = new Vector2(posX, posY);

		if (scale) {
			Vec.div2D(pos, World2D.GU);
		}

		bdef.position.set(pos);

		Body body = world.createBody(bdef);

		fdef.shape = shape;

		fdef.filter.categoryBits = categoryBits;

		for (int i = 0; i < bitsMask.length; i++) {

			fdef.filter.maskBits |= bitsMask[i];
		}

		body.createFixture(fdef);
	}

	public void createBody(World world, Shape shape, Vector2 position, float angle, short categoryBits,
			short[] bitsMask) {

		Body body = world.createBody(bdef);

		fdef.shape = shape;

		fdef.filter.categoryBits = categoryBits;

		for (int i = 0; i < bitsMask.length; i++) {

			fdef.filter.maskBits |= bitsMask[i];
		}

		body.createFixture(fdef);

		body.setTransform(position, angle);

	}

	public void createBody(World world, Shape shape, short categoryBits, short[] bitsMask) {

		Body body = world.createBody(bdef);

		fdef.shape = shape;

		fdef.filter.categoryBits = categoryBits;

		for (int i = 0; i < bitsMask.length; i++) {

			fdef.filter.maskBits |= bitsMask[i];
		}

		body.createFixture(fdef);

	}

}
