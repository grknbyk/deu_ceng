import enigma.core.Enigma;

public class Main {

	public static void main(String[] args) throws Exception {

		enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars", 120, 35, 15);
		
		Game game = new Game(cn, 20, 8, 5, 250, 500, 25000, 15, 3000);

		game.play();
		
	}
}