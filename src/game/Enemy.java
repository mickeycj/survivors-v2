package game;

import base.AbstractUnit;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Enemy extends AbstractUnit {

	private PVector destination;
	
	public Enemy(PApplet pApplet, PImage face) {
		super(0, 0, 0, 0, pApplet, face);
		this.setDestination();
	}
	
	public void setRadiusAndSize(int lvl) {
		radius = (lvl - 1) * 7.5f + 15f;
		level = lvl;
		switch (level) {
			case 1 :
				value = 1;
				color = 0xFFE5E19C;
				break;
			case 2 :
				value = 3;
				color = 0xFF9BB37E;
				break;
			case 3 :
				value = 5;
				color = 0xFF85ADAF;
				break;
			case 4 :
				value = 10;
				color = 0xFFB28077;
				break;
			case 5 :
				value = 20;
				color = 0xFF9B87AA;
				break;
			default :
		}
	}
	
	public void setDestination() {
		destination = new PVector((int)(Math.random() * pApplet.width), (int)(Math.random() * pApplet.height));
	}
	
	@Override
	public void reset() {
		super.reset();
		position.x = position.y = radius = 0;
		setDestination();
	}
	
	@Override
	public void update() {
		if (Math.abs(destination.x - position.x) < 1 && Math.abs(destination.y - position.y) < 1) {
			setDestination();
		}
		PVector newVector = new PVector(destination.x - position.x, destination.y - position.y);
		newVector.setMag(level);
		velocity.lerp(newVector, .2f);
		super.update();
	}
	
	@Override
	public void render() {
		pApplet.noFill();
		super.render();
	}
}
