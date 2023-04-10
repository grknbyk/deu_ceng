package StarTrekWarpWars;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.core.Enigma;

public class Game {

	//variables

	KeyListener klis = new KeyListener() {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (keypr == 0) {
				keypr = 1;
				rkey = e.getKeyCode();
			}
		}

		public void keyReleased(KeyEvent e) {
		}
	}; // do not touch
	CircularQueue trap = new CircularQueue(10); // to avoid affecting the same element twice by the same trap/warp device

	int keypr; // key pressed? // do not touch
	int rkey; // do not touch
	
	Element[] elements; // keep the movable elements to call
	
	int[] xy;
	Player player;
	
	char[][] game_field = new char[23][55];
	TargetSquare[] computergains = new TargetSquare[4];
	int c_score = 0;
	String input_queue_str = "";
	int timer_m_sec = 0; // timer millisec
	int element_amount_counter = 0; // element amount in game field
	enigma.console.Console cn;
	int input_queue_capacity; // element amount in game field
	int p_lives;
	CircularQueue input_queue;
	int element_limit; // element limit in game field
	int fast_speed; // 1000/250 = 4 frames per second (1 sec = 1000 millisec)
	int slow_speed; // 1000/500 = 2 frames per second (1 sec = 1000 millisec)
	int speed_m_sec; // game speed due to player
	int backpack_size;
	int time_period; // (milli second)time period for insert to game field from input queue
	int checkWidth; // assigned 3 to check 3x3 area (odd numbers only)
	int active_time; // for trap and wrap device

	public Game(enigma.console.Console Console, int element_limit, int backpack_size, int p_lives, int fast_speed, int slow_speed, int active_time, int input_queue_capacity, int time_period_for_input_queue) {
		this.element_limit = element_limit; // element limit in game field 20
		this.backpack_size = backpack_size; // 8
		this.p_lives = p_lives; // 5
		this.cn = Console;
		this.fast_speed = fast_speed; //250
		this.slow_speed = slow_speed; //500
		this.speed_m_sec = slow_speed;
		this.input_queue_str = "";
		this.input_queue_capacity = input_queue_capacity; // 15
		this.input_queue = new CircularQueue(input_queue_capacity);
		this.element_amount_counter = 0;
		this.time_period = time_period_for_input_queue; //3000
		this.checkWidth = 3;
		this.active_time = active_time; //25000
		this.elements = new Element[element_limit+backpack_size+1];
	}

	// game
	public void play() throws InterruptedException {
		cn.getTextWindow().addKeyListener(klis); // add keylistener

		loadMap("maze.txt");
		
		xy = findRandomFreeSpace(); // coordinates for player to initalize
		player = new Player(xy[0], xy[1], new Stack(backpack_size));
		game_field[player.getY()][player.getX()] = player.getElementType(); // place the player into game field

		xy = findRandomFreeSpace(); // coordinates for computer to initalize
		game_field[xy[1]][xy[0]] = 'C'; // place the player into game field
		elements[getEmptyIndexOfObject()] = new Element('C', xy[0], xy[1], -1,true,'I');

		printBoxes();
		fillTheGameField();

		while (player.getLives() > 0) {
			
			setSpeed();
			printInfo();
			printGameField();
			fillTheQueue();
			insertToGameFieldFromInputQueue();
			playerMovement();
			moveComputer();
			releaseTrap();
			trapCheck();
			move4_5();
			Thread.sleep(speed_m_sec); //delay
			updateInfo();
		}
		printInfo();
		print(cn, 30, 24, "GAME OVER");
		print(cn, 15, 25, "End-Game Score of The Human Player = " + (player.getScore()- c_score));
	}
	

	// string printer
	public void print(enigma.console.Console console, int x, int y, String str) {
		for (int i = 0; i < str.length(); i++) {
			console.getTextWindow().output(x + i, y, str.charAt(i));
		}
	}

	public void printGameField() {
		for (int i = 0; i < game_field.length; i++) {
			for (int j = 0; j < game_field[i].length; j++) {
				cn.getTextWindow().output(j, i, game_field[i][j]);
			}
		}
	}

	public void printBoxes() {
		print(cn, 56, 0, "Input");
		for (int i = 0; i < input_queue_capacity; i++) {
			cn.getTextWindow().output(56+i, 1, '<');
			cn.getTextWindow().output(56+i, 3, '<');
		}
		print(cn, 60, 5, "|   |");
		print(cn, 60, 6, "|   |");
		print(cn, 60, 7, "|   |");
		print(cn, 60, 8, "|   |");
		print(cn, 60, 9, "|   |");
		print(cn, 60, 10, "|   |");
		print(cn, 60, 11, "|   |");
		print(cn, 60, 12, "|   |");
		print(cn, 60, 13, "+---+");
		print(cn, 56, 14, "P. Backpack");
		print(cn, 56, 16, "P.Energy: ");
		print(cn, 56, 17, "P.Score : ");
		print(cn, 56, 18, "P.Life  : ");
		print(cn, 56, 20, "C.Score : ");
		print(cn, 56, 22, "Time    : ");
	}

	public void printInfo() {		
			for (int i = 0; i < 8; i++) {
			cn.getTextWindow().output(62, 12-i, ' ');
		}
		//prints backpack elements
		Stack temp_stack = new Stack(8);
		while (!player.getBackpack().isEmpty()) {
			temp_stack.push(player.getBackpack().pop());
		}
		int a = 0;
		while(!temp_stack.isEmpty()){
			char data = (char) temp_stack.pop();
			player.getBackpack().push(data);
			cn.getTextWindow().output(62, 12-a, data);
			a++;
		}
		print(cn, 56, 2, input_queue_str.substring(input_queue_str.length() - input_queue.size())); //print input queue
		cn.getTextWindow().output(67, 16, ' ');
		cn.getTextWindow().output(68, 16, ' ');
		cn.getTextWindow().output(69, 16, ' ');
		print(cn, 67, 16, String.valueOf((int) player.getEnergy() / 1000)); // p.energy
		print(cn, 67, 17, String.valueOf(player.getScore())); // p.score
		print(cn, 67, 18, String.valueOf(player.getLives())); // p.lives
		print(cn, 67, 20, String.valueOf(c_score)); // c.score
		print(cn, 67, 22, String.valueOf((int) (timer_m_sec/1000))); // timer
	}

	// updates time, p.energy
	public void updateInfo() {
		for (int i = 0; i < elements.length; i++) {
			if(elements[i] != null && (elements[i].getElementType() == '=' || elements[i].getElementType() == '*')){
				if (elements[i].getActive_time() > 0) {
					elements[i].setActive_time(elements[i].getActive_time()-speed_m_sec);
				}else{
					game_field[elements[i].getY()][elements[i].getX()] = ' ';
					int index = findTheIndexOfElement(elements[i].getX(), elements[i].getY());
					elements[index] = null;
					element_amount_counter--;
				}
			}
		}
		if (player.getEnergy() > 0) {
			player.setEnergy(player.getEnergy() - speed_m_sec);
		}
		timer_m_sec += speed_m_sec;
	}

	public void setSpeed() {
		if (player.getEnergy() > 0)
			speed_m_sec = fast_speed;
		else
			speed_m_sec = slow_speed;
	}

	public boolean getTimePeriod(int milli_second) {return (double) timer_m_sec % milli_second == 0;}

	public void addToBackpack(int x, int y) {
		char data = game_field[y][x];
		if(data == '1' || data == '2' || data == '3' || data == '4' || data == '5'){ // take point
			player.setScore(player.getScore() + getPointAmount(data, player.getElementType())); // update score
			int index = findTheIndexOfElement(x, y);
			elements[index] = null;
			element_amount_counter--;
			if(data != '1'){
				if(player.getBackpack().isEmpty()){
					player.getBackpack().push(data);
				}
				else if(!player.getBackpack().isFull()){
					char last_data = (char) player.getBackpack().peek();
					if(last_data == '=' || last_data == '*'){
						player.getBackpack().push(data);
					}
					else if((last_data == '2' || last_data == '3' || last_data == '4' || last_data == '5') && data != last_data){
						player.getBackpack().pop();
					}else{
						player.getBackpack().pop();
						if(data == '2' && data == last_data){
							player.setEnergy(player.getEnergy()+30000);
						}else if(data == '3' && data == last_data){
							player.getBackpack().push('=');
						}else if(data == '4' && data == last_data){
							player.setEnergy(player.getEnergy()+240000);
						}else if(data == '5' && data == last_data){
							player.getBackpack().push('*');
						}
					}
				}
			}
		}
	}

	public int getPointAmount(char element_type, char performer) { 
		switch (performer) {    //Returns the score of the elements in the game according to the player and computer
			case 'P':
				switch (element_type) {
					case '1':
						return 1;
					case '2':
						return 5;
					case '3':
						return 15;
					case '4':
						return 50;
					case '5':
						return 150;
					case '*':
						return 0;
					case '=':
						return 0;
					case 'C':
						return 300;
					case ' ':
						return 0;
					default: // else
						return 0;
				}

			case 'C': // scores for computer
				switch (element_type) {
					case '1':
						return 2;
					case '2':
						return 10;
					case '3':
						return 30;
					case '4':
						return 100;
					case '5':
						return 300;
					case '*':
						return 300;
					case '=':
						return 300;
					case 'C':
						return 0;
					case ' ':
						return 0;
					default: // else
						return 0;
				}

			default: // else
				return -1;
		}
	}

	public void fillTheGameField() {
		while(element_amount_counter < element_limit){
			int[] xy = findRandomFreeSpace(); //  Returns the coordinates of an empty random point on the game field
			char data = randomElementType();  // the element to be assigned to the game field
			game_field[xy[1]][xy[0]] = data;
			int index = getEmptyIndexOfObject(); // returns the index of the null element
			boolean isMoving = false;
			if(data == 'C' || data == '4' || data == '5'){ // movable elements
				isMoving = true;
			}
			if (data == '=' || data == '*'){
				elements[index] = new Element(data, xy[0], xy[1], active_time, isMoving, 'I');
			}else{
				elements[index] = new Element(data, xy[0], xy[1], -1, isMoving, 'I'); // -1 is assigned instead of those without activation time
			}

			element_amount_counter++;
		}
	}

	// fills the queue if it is empty and prints the elements of queue
	public void fillTheQueue() {
		while (!input_queue.isFull()) {
			char data = randomElementType();
			input_queue_str += String.valueOf(data);
			input_queue.enqueue(data);
		}
		print(cn, 56, 2, input_queue_str.substring(input_queue_str.length() - input_queue.size())); //print input queue
	}

	public int getEmptyIndexOfObject() { // returns the index of the null element 
		int index = -1;
		for (int i = 0; i < elements.length; i++) {
			if(elements[i] == null){
				index = i;
				break;
			}
		}		
		return index;
	}

	public int findTheIndexOfElement(int x, int y) { // Returns the index of the element whose coordinates are given in the elements array
		int index = -1;
		for (int i = 0; i < elements.length; i++) {
			if(elements[i] == null){
				continue;
			}
			else{
				if(elements[i].getX() == x && elements[i].getY() == y){
					index = i;
					break;
				}
			}
		}		
		return index;
	}

	public void insertToGameFieldFromInputQueue() {
		if (element_amount_counter < element_limit
				&& getTimePeriod(time_period)) {
			int[] temp_xy = findRandomFreeSpace();
			char data = (char) input_queue.dequeue();
			game_field[temp_xy[1]][temp_xy[0]] = data;
			print(cn, 56, 2, " "); // delete the used element from console
			element_amount_counter++;
			
			// add the movable element in the game field to the movable_elements array
			int index = getEmptyIndexOfObject();
			boolean isMoving = false;
			if(data == 'C' || data == '4' || data == '5'){
				isMoving = true;
			}
			if(data == '=' || data == '*'){
				elements[index] = new Element(data, temp_xy[0], temp_xy[1], active_time, isMoving, 'I');
			}else{
				elements[index] = new Element(data, temp_xy[0], temp_xy[1], -1, isMoving, 'I');
			}
		}
	}

	public boolean isTheSquareInDirectionEmpty(int x, int y, int directory) { // 0 left, 1 right, 2 up, 3 down
		boolean flag = false;  // It checks whether the point in the game field is ' ' for the direction taken as a parameter.
		if(directory == 0 && game_field[y][x - 1] == ' '){
			flag = true;
		}
		else if(directory == 1 && game_field[y][x + 1] == ' '){
			flag = true;
		}
		else if(directory == 2 && game_field[y - 1][x] == ' '){
			flag = true;
		}
		else if(directory == 3 && game_field[y + 1][x] == ' '){
			flag = true;
		}
		return flag;
	}

	public boolean canStep(int x, int y, int directory) { // 0 left, 1 right, 2 up, 3 down
		switch (directory) {   // Returns true for places the player can step
			case 0:
			switch (game_field[y][x - 1]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
					return true;
				default:
					return false;
			}
			case 1:
			switch (game_field[y][x + 1]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
					return true;
				default:
					return false;
			}
			case 2:
			switch (game_field[y - 1][x]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
					return true;
				default:
					return false;
			}
			case 3:
			switch (game_field[y + 1][x]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
					return true;
				default:
					return false;
			}
			default:
			return false;
		}
	}

	public void playerMovement() {

		if (keypr == 1) { // if keyboard button pressed
			if (rkey == KeyEvent.VK_LEFT && canStep(player.getX(), player.getY(), 0)) {
				// old position
				game_field[player.getY()][player.getX()] = ' ';
				// new position
				addToBackpack(player.getX()-1, player.getY());
				game_field[player.getY()][player.getX() - 1] = player.getElementType();
				player.setX(player.getX() - 1);
			} else if (rkey == KeyEvent.VK_RIGHT && canStep(player.getX(), player.getY(), 1)) {
				// old position
				game_field[player.getY()][player.getX()] = ' ';
				// new position
				addToBackpack(player.getX()+1, player.getY());
				game_field[player.getY()][player.getX() + 1] = player.getElementType();
				player.setX(player.getX() + 1);
			} else if (rkey == KeyEvent.VK_UP && canStep(player.getX(), player.getY(), 2)) {
				// old position
				game_field[player.getY()][player.getX()] = ' ';
				// new position
				addToBackpack(player.getX(), player.getY()-1);
				game_field[player.getY() - 1][player.getX()] = player.getElementType();
				player.setY(player.getY() - 1);
			} else if (rkey == KeyEvent.VK_DOWN && canStep(player.getX(), player.getY(), 3)) {
				// old position
				game_field[player.getY()][player.getX()] = ' ';
				// new position
				addToBackpack(player.getX(), player.getY()+1);
				game_field[player.getY() + 1][player.getX()] = player.getElementType();
				player.setY(player.getY() + 1);
			}else if ( !player.getBackpack().isEmpty()) { // if keyboard button pressed and player backpack is not empty
				if (rkey == KeyEvent.VK_A && isTheSquareInDirectionEmpty(player.getX(), player.getY(), 0)) {
					char last_data = (char) player.getBackpack().peek();
					if(last_data == '=' || last_data == '*'){
						game_field[player.getY()][player.getX()-1]=(char) player.getBackpack().pop();
						int index = getEmptyIndexOfObject();
						elements[index] = new Element(last_data,player.getX()-1,player.getY(),active_time,false,'P');
						element_amount_counter++;
					}else{
						player.getBackpack().pop();
					}
				} else if (rkey == KeyEvent.VK_D && isTheSquareInDirectionEmpty(player.getX(), player.getY(), 1)) {
					char last_data = (char) player.getBackpack().peek();
					if(last_data == '=' || last_data == '*'){
						game_field[player.getY()][player.getX()+1]=(char) player.getBackpack().pop();
						int index = getEmptyIndexOfObject();
						elements[index] = new Element(last_data,player.getX()+1,player.getY(),active_time,false, 'P');
						element_amount_counter++;
					}else{
						player.getBackpack().pop();
					}
				} else if (rkey == KeyEvent.VK_W && isTheSquareInDirectionEmpty(player.getX(), player.getY(), 2)) {
					char last_data = (char) player.getBackpack().peek();
					if(last_data == '=' || last_data == '*'){
						game_field[player.getY()-1][player.getX()]=(char) player.getBackpack().pop();
						int index = getEmptyIndexOfObject();
						elements[index] = new Element(last_data,player.getX(),player.getY()-1,active_time,false, 'P');//index -1 hatasu burda alÃ„Â±yoz
						element_amount_counter++;
					}else{
						player.getBackpack().pop();
					}
				} else if (rkey == KeyEvent.VK_S && isTheSquareInDirectionEmpty(player.getX(), player.getY(), 3)) {
					char last_data = (char) player.getBackpack().peek();
					if(last_data == '=' || last_data == '*'){
						game_field[player.getY()+1][player.getX()]=(char) player.getBackpack().pop();
						int index = getEmptyIndexOfObject();
						elements[index] = new Element(last_data,player.getX(),player.getY()+1,active_time,false, 'P');
						element_amount_counter++;
					}else{
						player.getBackpack().pop();
					}
				}
			}
			

			keypr = 0; // last action
		}
	}

	public void move4_5(){
		Random rnd = new Random();
		if(getTimePeriod(500)){
			for(int i = 0 ; i<elements.length; i++)//checks all the elements on the game board
				{
					if(elements[i] == null || !elements[i].getisMoving() || elements[i].getElementType()=='C')
						continue;
				
					while (true){//loops until move

						if(!isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 0) &&
						!isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 1) &&
						!isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 2) &&
						!isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 3)){
							break;
						}

						int random = rnd.nextInt(0,4);

						// 0 left, 1 right, 2 up, 3 down
						if(random==1 && isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 1)) {//right
							game_field[elements[i].getY()][elements[i].getX()] = ' ';
							//assigning new position
							elements[i].setX(elements[i].getX() + 1);
							game_field[elements[i].getY()][elements[i].getX()] = elements[i].getElementType();
							break;
						}
						else if(random==0 && isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 0)) {//left
							game_field[elements[i].getY()][elements[i].getX()] = ' ';
							//assigning new position
							elements[i].setX(elements[i].getX() - 1);
							game_field[elements[i].getY()][elements[i].getX()] = elements[i].getElementType();
							break;
						}
						else if(random==2 && isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 2)) {//up
							game_field[elements[i].getY()][elements[i].getX()] = ' ';
							//assigning new position
							elements[i].setY(elements[i].getY() - 1);
							game_field[elements[i].getY()][elements[i].getX()] = elements[i].getElementType();
							break;
						}
						else if(random==3 && isTheSquareInDirectionEmpty(elements[i].getX(), elements[i].getY(), 3)) {//down
							game_field[elements[i].getY()][elements[i].getX()] = ' ';
							//assigning new position
							elements[i].setY(elements[i].getY() + 1);
							game_field[elements[i].getY()][elements[i].getX()] = elements[i].getElementType();
							break;
						}
					}//loops until move
				}
			}
	}

	public void releaseTrap(){ // '=' operations

		for(int i = 0 ; i < elements.length;i++){
			if(elements[i]==null || elements[i].getRestrictions().isEmpty())
				continue; // skip the elements which are not under a restriction.

			int size = elements[i].getRestrictions().size();
			for(int m = 0 ; m < size ; m++) // releasing the element from trap
			{ // (int) elements[idx].getRestrictions().peek() returns one of the trap's index which affects the element.
				if(elements[(int) elements[i].getRestrictions().peek()] == null)
					//enters if trap's time is up
				elements[i].getRestrictions().dequeue();
				else
					elements[i].getRestrictions().enqueue(elements[i].getRestrictions().dequeue());
			}

			if((elements[i].getElementType()=='C' || elements[i].getElementType()=='4' || elements[i].getElementType()=='5') &&
					elements[i].getRestrictions().isEmpty())
				elements[i].setisMoving(true);
		}

	}// '=' operations

	public void trapCheck(){// for both '*' & '=' operations

		char try1;
		char restriction;
		char performer;
		for(int i = 0 ; i<elements.length; i++){
			if(elements[i] == null || !(elements[i].getElementType() == '*' || elements[i].getElementType() == '='))
				continue;

			restriction = elements[i].getElementType(); // * or =
			performer = elements[i].getPerformer(); //inserted to game field by whom? (input queue or player)

			if(restriction == '='){

				for(int j = -(checkWidth/2) ;j<=(checkWidth/2);j++) {// to check surround of related restriction
					int idx;

					try {
						try1 = game_field[j+elements[i].getY()][elements[i].getX()];
					}catch (Exception ArrayIndexOutOfBoundsException){
						continue;
					}

					for (int k = -(checkWidth/2); k <= checkWidth/2; k++) {
						if(j == 0 && k== 0) // mid point
							continue;

						try {

							if(game_field[j + elements[i].getY()][k + elements[i].getX()] == ' ' ||
									game_field[j + elements[i].getY()][k + elements[i].getX()] == '#')// skipping map elements
								continue;
							idx = findTheIndexOfElement(k + elements[i].getX(),j + elements[i].getY());

							if(elements[idx].getRestrictions().isEmpty()) // no restriction affects this element
							{
								elements[idx].getRestrictions().enqueue(i); // adding the trap's index
								elements[idx].setisMoving(false);
							}else {
								boolean contains = false;
								for(int l = 0 ; l<elements[idx].getRestrictions().size();l++ ) //checking all the traps which affected this element
								{
									if((int) elements[idx].getRestrictions().peek()== i) //is this element affected by same trap before?
										contains = true;
									elements[idx].getRestrictions().enqueue(elements[idx].getRestrictions().dequeue());
								}
								if(!contains) // enters if the trap not affected
								{
									elements[idx].getRestrictions().enqueue(i); // adding the trap's index
									elements[idx].setisMoving(false);
								}
							}


						}catch (Exception ArrayIndexOutOfBoundsException){
							continue;
						}

					}
				}// to check surround of related restriction


			}
			else if(restriction == '*'){

				for(int j = -(checkWidth/2) ;j<=(checkWidth/2);j++) {// to check surround of related restriction
					int idx;

					try {
						try1 = game_field[j+elements[i].getY()][elements[i].getX()];
					}catch (Exception ArrayIndexOutOfBoundsException){
						continue;
					}

					for (int k = -(checkWidth/2); k <= checkWidth/2; k++) {
						if(j == 0 && k== 0) // mid point
							continue;

						try {

							if(game_field[j + elements[i].getY()][k + elements[i].getX()] == ' ' ||
									game_field[j + elements[i].getY()][k + elements[i].getX()] == '#')// skipping map elements
								continue;

							idx = findTheIndexOfElement(k + elements[i].getX(),j + elements[i].getY());

							if(elements[idx].getElementType() != 'P' &&
									elements[idx].getElementType() != '=')//according to rules ward device affects only numbers and computers
							{
								game_field[elements[idx].getY()][elements[idx].getX()] = ' ';
								player.setScore(player.getScore() + getPointAmount(elements[idx].getElementType(), performer));
								elements[idx] = null;
								element_amount_counter--;

							}


						}catch (Exception ArrayIndexOutOfBoundsException){
							continue;
						}

					}
				}// to check surround of related restriction


			}



		}
	}// for both '*' & '=' operations

	public char randomElementType() { // generate element based on possibilities
		Random randint = new Random();
		char ch = 0;
		int num = randint.nextInt(40) + 1;
		if (num >= 1 && num <= 12)
			return '1';
		else if (num >= 13 && num <= 20)
			return '2';
		else if (num >= 21 && num <= 26)
			return '3';
		else if (num >= 27 && num <= 31)
			return '4';
		else if (num >= 32 && num <= 35)
			return '5';
		else if (num >= 36 && num <= 37)
			return '=';
		else if (num == 38)
			return '*';
		else if (num >= 39 && num <= 40)
			return 'C';
		else
			return ch;
	}

	public int[] findRandomFreeSpace() {  // Returns the coordinates of an empty random point on the game field
		int[] coordinates = new int[2];
		Random randint = new Random();

		while (true) {
			int x = randint.nextInt(1, 53);
			int y = randint.nextInt(1, 22);
			if (game_field[y][x] == ' ') {
				coordinates[0] = x;
				coordinates[1] = y;
				break;
			} else
				continue;
		}
		return coordinates;
	}

	public void loadMap(String file_name) { //map is read and assigned to 'game_field' char array
		File board = new File(file_name);
		Scanner boardtext = null;
		try {
			boardtext = new Scanner(board);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		int i = 0;
		while (boardtext.hasNextLine()) {

			String line = boardtext.nextLine();
			for (int a = 0; a < line.length(); a++) {
				game_field[i][a] = line.charAt(a);
			}
			i++;
		}
		boardtext.close();
	}

	public void moveComputer() {
		Random randint = new Random();
		if(getTimePeriod(500)){
			for(int i = 0 ; i<elements.length; i++){//checks all the elements on the game board
				if(elements[i]==null) {
					continue;
				}
				if(elements[i].getElementType() == 'C'&&elements[i].getisMoving()==true) { //  if it's not in the trap effect
					while(true) {	
					
						computergains[0]=null; // All elements are assigned null before doing the gain comparison
						computergains[1]=null;  
						computergains[2]=null;
						computergains[3]=null;
								
						calculateTotalGain(elements[i].getX(),elements[i].getY()); // the gains in the directions the computer can go are calculated
						double maxgain = -1000000;
						int aimx= -1,aimy=-1;
					
						if(computergains[0] == null && computergains[1] == null && computergains[2] == null && computergains[3] == null)
							break;
						for(int j=0;j<computergains.length;j++)
						{
							if(computergains[j]==null)
								continue;
							if(computergains[j].getTotalgain()==maxgain) {  // makes a random move if there are two moves with the same gain
								int randomfordirection = randint.nextInt(2);
								if(randomfordirection==0) {
									continue;
								}
								else {
									maxgain = computergains[j].getTotalgain();//the coordinates of that point are kept in the variables.
									aimx = computergains[j].getAimx();
									aimy = computergains[j].getAimy();
								}
							}
							
							if(computergains[j].getTotalgain()>maxgain) { //the maximum gain is calculated and 
								maxgain = computergains[j].getTotalgain();//the coordinates of that point are kept in the variables.
								aimx = computergains[j].getAimx();
								aimy = computergains[j].getAimy();
							}
							
							
						}
						game_field[elements[i].getY()][elements[i].getX()] = ' '; 
						
						if(neighbor_control(aimx,aimy,'P')) { // if it is adjacent to the computer player, two elements are deleted from the backpack
							int delete_counter = 0;           // and add the scores of those elements to its own score.
							while(!player.getBackpack().isEmpty()) {
								c_score += getPointAmount((char)player.getBackpack().peek(),elements[i].getElementType());
								player.getBackpack().pop();
								delete_counter ++;
								if(delete_counter==2) {
									break;
								}
							}
						}
						if(game_field[aimy][aimx]=='P' && player.getLives()>0) { // if computer catches the player
							player.setLives(player.getLives()-1);
							c_score += getPointAmount(game_field[aimy][aimx],elements[i].getElementType());
							while(!player.getBackpack().isEmpty()) {
								player.getBackpack().pop();
							} //As long as the player has life left, a new player is assigned to the game field.
							int[] new_Pcoordinates = findRandomFreeSpace();
							int x = new_Pcoordinates[0];
							int y = new_Pcoordinates[1];
							game_field[y][x]='P';
							player.setX(x);
							player.setY(y);
						}
						//if target square contains elements,its score adds to computer's score.
						if(game_field[aimy][aimx]=='1' || game_field[aimy][aimx]=='2' ||game_field[aimy][aimx]=='3' || game_field[aimy][aimx]=='4' || game_field[aimy][aimx]=='5' ){
							
							c_score += getPointAmount(game_field[aimy][aimx],elements[i].getElementType());
							int index = findTheIndexOfElement(aimx, aimy);
							elements[index] = null;
							element_amount_counter--;
						}
						//assigning new position
						elements[i].setX(aimx);
						elements[i].setY(aimy);
						game_field[aimy][aimx] = 'C';
							
						break;
					}
					
				}
				
				
			}
		}
		
	}
	
	public void calculateTotalGain(int computer_x,int computer_y) {
		int direction=0;  // 0 = LEFT / 1 = RIGHT / 2 = UP / 3 = DOWN
		boolean[] canStep = new boolean[4];
		int a=0;
		while(direction<4) {

			if(canStepforComputer(computer_x,computer_y,direction)) 
				canStep[a] = true; // we can understand the computer can move if direction != -1 and movement direction by assigning 0,1,2,3

			else
				canStep[a]= false; // the square computer cannot step

			direction++;                                  
			a++;                                 
		}
		int k=0;
		for(int dir=0;dir<canStep.length;dir++) {//dir means direction 0 left, 1 right, 2 up, 3 down
			if(!canStep[dir])
				continue;

			Object[] targetSquareInfo = returnTargetSquare(computer_x,computer_y,dir);
			char targetSquareType = (char)targetSquareInfo[0]; // type of element on the target location
			int targetLocX = (int)(targetSquareInfo[1]); //x coordinate of target location
			int targetLocY = (int)(targetSquareInfo[2]); //y coordinate of target location

			double distancegain =calculateDistanceGain(computer_x,computer_y,targetLocX,targetLocY)*500; 
			double elementgain = getPointAmount(targetSquareType,'C');
			double totalgain = elementgain + distancegain; 
			TargetSquare square = new TargetSquare(totalgain,targetLocX,targetLocY);
			computergains[k]=square;
			k++;
			//The computer can gain a maximum of 300 points from the element.
		}	//We added a coefficient greater than 300 to increase the importance of approaching the player.
	}

	public Object[] returnTargetSquare(int computer_x,int computer_y,int direction) {//returns array that includes the type of target square (' ','1'...), x coordinate, y coordinate

		Object[] element= new Object[3];
		if(direction == 0) {
			element[0] = game_field[computer_y][computer_x-1];
			element[1] = computer_x-1;
			element[2]= computer_y;
		}
		else if(direction==1) {
			element[0] = game_field[computer_y][computer_x+1];
			element[1] = computer_x+1;
			element[2]= computer_y;
		}
		else if(direction==2) {
			element[0] = game_field[computer_y-1][computer_x];
			element[1] = computer_x;
			element[2]= computer_y-1;
		}
		else if(direction==3) {
			element[0] =game_field[computer_y+1][computer_x];
			element[1] = computer_x;
			element[2]= computer_y+1;
		}
		
		return element;
	}
	
	public boolean neighbor_control(int x,int y ,char element_type) {  //Returns whether the point as a parameter is adjacent to the element taken as a parameter
		boolean flag = false; 
		if((game_field[y][x - 1] == element_type)||(game_field[y][x + 1] == element_type )||(game_field[y - 1][x] == element_type)||(game_field[y + 1][x] == element_type)){
			flag = true;
		}
		
		return flag;
	}
	
	public boolean canStepforComputer(int x, int y, int directory) { // Returns true for where the computer can step
		switch (directory) {  // directory-  0 left , 1 right , 2 up , 3 down
			case 0: 
			switch (game_field[y][x - 1]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
				case 'P':
				
					return true;
				default:
					return false;
			}
			case 1:
			switch (game_field[y][x + 1]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
				case 'P':
				
					return true;
				default:
					return false;
			}
			case 2:
			switch (game_field[y - 1][x]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
				case 'P':
				
					return true;
				default:
					return false;
			}
			case 3:
			switch (game_field[y + 1][x]) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case ' ':
				case 'P':
				
					return true;
				default:
					return false;
			}
			default:
			return false;
		}
	}


	public int calculateDistanceGain(int computerx,int computery,int targetx,int targety) {
		int first_distance = Math.abs(player.getX()-computerx)+Math.abs(player.getY()-computery);
		int second_distance = Math.abs(player.getX()-targetx)+Math.abs(player.getY()-targety);
		int distancegain = first_distance - second_distance;
		return distancegain;
		
	}

}