import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Survivors extends PApplet {

	private ArrayList<PImage> faces;
	private PImage startScreen;
	private PImage background;
	private PImage gameOverScreen;
	
	private Game game;
	
	@Override
	public void settings() {
		size(1080, 640);
		faces = new ArrayList<>();
		startScreen = loadImage("res/images/backgrounds/start.png");
		background = loadImage("res/images/backgrounds/background.png");
		gameOverScreen = loadImage("res/images/backgrounds/over.png");
		for (File file : new File("res/images/faces").listFiles()) {
			if (file.getName().endsWith(".png")) {
				if (file.getName().equals("0.png")) {
					faces.add(0, loadImage(file.getPath()));
				} else {
					faces.add(loadImage(file.getPath()));
				}
			}
		}
	}
	
	@Override
	public void setup() {
		try {
			textFont(new PFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("res/fonts/OCR")), true));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		imageMode(CENTER);
		image(startScreen, width / 2, height / 2);
		game = new Game(this, faces);
	}
	
	@Override
	public void draw() {
		if (game.isStarted()) {
			if (!game.isEnded()) {
				image(background, width / 2, height / 2);
				game.update();
			} else {
				image(gameOverScreen, width / 2, height / 2);
				game.showStats();
			}
		}
	}
	
	@Override
	public void keyPressed() {
		if (!game.isStarted()) {
			game.start();
		} else if (game.isEnded()) {
			game.restart();
		}
	}
	
	/* Main method */
	public static void main(String[] args) {
		PApplet.main("Survivors");
	}
}
