package game;

import java.util.ArrayList;

import base.Component;
import processing.core.PApplet;
import processing.core.PImage;

public class World implements Component {

	private final PApplet pApplet;
	
	private final Player player;
	private final ArrayList<Enemy> enemies;
	private final ArrayList<Enemy> enemyPool;
	
	private final int[] enemyCounts = new int[5];
	private final float[] enemySizes = {15f, 22.5f, 30f, 37.5f, 45f};
	private final int[][] enemyLimitsPerLevel = {{8, 5, 2, 0, 0}, {3, 5, 5, 3, 0}, {2, 3, 5, 7, 5}};
	
	private int highScore;
	
	public World(PApplet pApplet, ArrayList<PImage> faces) {
		this.pApplet = pApplet;
		this.enemyPool = new ArrayList<>();
		for (int i = 1; i < faces.size(); i++) {
			this.enemyPool.add(new Enemy(pApplet, faces.get(i)));
		}
		this.player = new Player(pApplet, faces.get(0));
		this.enemies = new ArrayList<>();
		initEnemies();
	}
	
	public boolean isPlayerAlive() {
		return player.isAlive();
	}
	
	public int getPlayerLevel() {
		return player.getLevel();
	}
	
	public int getPlayerScore() {
		return player.getScore();
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public boolean isNumEnemiesTooLow() {
		return enemies.size() < limitOfLevel(player.getLevel());
	}
	
	public void addRandomEnemy() {
		for (int level = 1; level <= player.getLevel() + 2; level++) {
			if (!enemyPool.isEmpty() && enemyCounts[level-1] != enemyLimitsPerLevel[player.getLevel()-1][level-1]) {
				int index = (int)(Math.random() * enemyPool.size());
				Enemy enemy = enemyPool.get(index);
				enemyPool.remove(index);
				float[] position = randomPosition();
				enemy.setPosition(position[0], position[1]);
				enemy.setRadiusAndSize(level);
				enemies.add(enemy);
				enemyCounts[level-1]++;
			}

		}
	}
	
	public void reset() {
		initEnemies();
	}
	
	private void initEnemies() {
		player.reset();
		enemyPool.addAll(enemies);
		enemies.clear();
		for (Enemy enemy : enemyPool) {
			enemy.reset();
		}
		for (int i = 0; i < enemyCounts.length; i++) {
			enemyCounts[i] = 0;
		}
		for (int i = 0, level = 1; i < limitOfLevel(player.getLevel()); i++) {
			if (enemyCounts[level-1] == enemyLimitsPerLevel[0][level-1]) {
				level++;
			}
			int index = (int)(Math.random() * enemyPool.size());
			Enemy enemy = enemyPool.get(index);
			enemyPool.remove(index);
			float[] position = randomPosition();
			enemy.setPosition(position[0], position[1]);
			enemy.setRadiusAndSize(level);
			enemies.add(enemy);
			enemyCounts[level-1]++;
		}
	}
	
	private int limitOfLevel(int level) {
		int sum = 0;
		for (int i = 0; i < enemyLimitsPerLevel[level-1].length; i++) {
			sum += enemyLimitsPerLevel[level-1][i];
		}
		return sum;
	}
	
	private float[] randomPosition() {
		float x = (float)(Math.random() * pApplet.width);
		float y = (float)(123 + Math.random() * (pApplet.height - 123));
		return (x > player.getPosition().x - 250 && x < player.getPosition().x + 250 && y > player.getPosition().y - 33 && y < player.getPosition().y + 273) ?
				randomPosition() : new float[] {x, y};
	}
	
	@Override
	public void update() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (player.eat(enemy)) {
				if (player.getRadius() >= enemySizes[player.getLevel()]) {
					player.levelUp();
				}
				enemyPool.add(enemy);
				enemyCounts[enemy.getLevel()-1]--;
				if (player.getScore() > highScore) {
					highScore = player.getScore();
				}
			} else {
				if (enemy.eat(player)) {
					player.die();
				} else if ((pApplet.frameCount + i * 100) % 120 == 0) {
					enemy.setDestination();
				}
				enemy.update();
			}
		}
		enemies.removeAll(enemyPool);
		player.update();
	}
	
	@Override
	public void render() {
		for (Enemy enemy : enemies) {
			enemy.render();
		}
		player.render();
	}
}
