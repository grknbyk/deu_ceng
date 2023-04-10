public class Player extends Element {
	private int energy;
	private int lives;
	private int score;
	private Stack backpack;

	public Player(int x, int y, Stack backpack) {
		super('P', x, y, -1, true,'P');
		this.energy = 50000;//m.second
		this.lives = 5;
		this.score = 0;
		this.backpack = backpack;
	}

	public Stack getBackpack() {
		return backpack;
	}

	public void setBackpack(Stack backpack) {
		this.backpack = backpack;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}