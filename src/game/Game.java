package game;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Game {

	private final PApplet pApplet;

	private final World world;

	private boolean started;

	public Game(PApplet pApplet, ArrayList<PImage> faces) {
		this.pApplet = pApplet;
		this.world = new World(pApplet, faces);
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isEnded() {
		return !world.isPlayerAlive();
	}

	public void start() {
		started = true;
	}

	public void restart() {
		world.reset();
	}

	public void showStats() {
		pApplet.fill(0xFF666666);
		pApplet.textSize(35);
		pApplet.text(world.getPlayerScore(), 805, 378);
		pApplet.text(world.getHighScore(), 896, 415);
	}

	public void update() {
		if (world.isNumEnemiesTooLow() && pApplet.frameCount % 180 == 0) {
			world.addRandomEnemy();
		}
		world.update();
		world.render();
		pApplet.fill(0xFFFFFFFF);
		pApplet.textSize(27);
		pApplet.text(world.getPlayerLevel(), 277, 44);
		pApplet.textSize(23);
		pApplet.text(world.getPlayerScore(), 251, 74);
		pApplet.text(world.getHighScore(), 304, 102);
	}
}
