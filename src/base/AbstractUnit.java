package base;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public abstract class AbstractUnit implements Unit {

	protected final PApplet pApplet;
	protected final PImage face;
	
	protected final PVector position;
	protected final PVector velocity;
	
	protected float radius;
	protected int level;
	protected int value;
	protected int color;
	
	protected AbstractUnit(float x, float y, float r, int l, PApplet pApplet, PImage face) {
		this.pApplet = pApplet;
		this.face = face;
		this.position = new PVector(x, y);
		this.velocity = new PVector();
		this.radius = r;
		this.level = l;
		this.color = 0xFFE5E19C;
	}
	
	@Override
	public PVector getPosition() {
		return position;
	}
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	@Override
	public boolean eat(Unit other) {
		float dist = PVector.dist(position, other.getPosition());
		if (dist < radius * .8f + other.getRadius() && radius > other.getRadius()) {
			radius += .05f * other.getRadius();
			return true;
		}
		return false;
	}
	
	@Override
	public void reset() {
		velocity.mult(0);
		level = 1;
		color = 0xFFE5E19C;
	}
	
	@Override
	public void update() {
		position.add(velocity);
		if (position.y <= 123 + radius) {
			position.y = 123 + radius;
		} else if (position.y > pApplet.height - radius) {
			position.y = pApplet.height - radius;
		} else if (position.x < radius) {
			position.x = radius;
		} else if (position.x > pApplet.width - radius) {
			position.x = pApplet.width - radius;
		}
	}
	
	@Override
	public void render() {
		pApplet.stroke(color);
		pApplet.strokeWeight(1.5f);
		pApplet.ellipse(position.x, position.y, radius * 2.5f, radius * 2.5f);
		pApplet.image(face, position.x, position.y, radius * 2, radius * 2);
	}
}
