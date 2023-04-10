public class SingleLinkedList {

	private Node head;

	// used in main
	public void add(Object data) {
		if (head == null) {
			Node newNode = new Node(data);
			head = newNode;
		} else {
			Node temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			Node newNode = new Node(data);
			temp.setLink(newNode);
		}
	}

	// not used in main
	public int size() {
		if (head == null) {
			return 0;
		} else {
			int count = 0;
			Node temp = head;

			while (temp != null) {
				temp = temp.getLink();
				count++;
			}
			return count;
		}
	}

	// not used in main
	public void display() {
		if (head == null) {
			System.out.println("List is empty.");
		} else {
			Node temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}

		}
	}

	// not used in main
	public void remove(Object dataToDelete) {
		if (head == null) {
			System.out.println("Linked list is empty.");
		} else {
			while ((Integer) head.getData() == (Integer) dataToDelete)
				head = head.getLink();

			Node temp = head;
			Node previous = null;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
	}

	// not used in main
	public int findMax() {
		if (head == null) {
			System.err.println("The Linked List is empty");
			return Integer.MIN_VALUE;
		} else {
			int maxVal = Integer.MIN_VALUE;

			Node temp = head;

			while (temp != null) {
				if ((int) temp.getData() > maxVal) {
					maxVal = (int) temp.getData();
				}
				temp = temp.getLink();
			}
			return maxVal;
		}
	}

	// used in main
	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty.");
			return false;
		} else {
			Node temp = head;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data) {
					return true;
				}
				temp = temp.getLink();
			}
			return false;
		}
	}

	////////////////////
	// addeds methods //
	////////////////////

	public String getString() {
		if (head == null) {
			return "List is empty.";
		} else {
			String str = "";
			Node temp = head;
			while (temp != null) {
				str += temp.getData() + " ";
				temp = temp.getLink();
			}
			return str;
		}
	}

	public int count(Object dataToCount) {

		int counter = 0;

		if (head == null) {
			return counter;
		} else {

			Node temp = head;

			while (temp != null) {
				if ((int) temp.getData() == (int) dataToCount) {
					counter++;
				}
				temp = temp.getLink();
			}
			return counter;
		}
	}

	// returns data due to index number
	public Object get(int index) {

		if (head == null) {
			System.out.println("List is empty.");
			return false;
		} else {
			Node temp = head;

			int count = 0;
			while (temp.getData() != null && index != count) {
				temp = temp.getLink();
				count++;
			}

			return temp.getData();
		}

	}

	// removes the number that first found
	public void delete(int dataToDelete) {
		if (head == null) {
			return;
		}
		if ((Integer) head.getData() == (Integer) dataToDelete) {
			head = head.getLink();
		} else {
			Node previous = head;
			Node current = head.getLink();

			while (current != null) {
				if ((Integer) current.getData() == (Integer) dataToDelete) {
					previous.setLink(current.getLink());
					break;
				}

				previous = current;
				current = current.getLink();
			}
		}
	}

	// removes the player that first found
	public void delete(Player dataToDelete) {

		if (head == null) {
			return;
		}
		if ((Player) head.getData() == (Player) dataToDelete) {
			head = head.getLink();
		} else {
			Node previous = head;
			Node current = head.getLink();

			while (current != null) {
				if ((Player) current.getData() == (Player) dataToDelete) {
					previous.setLink(current.getLink());
					break;
				}

				previous = current;
				current = current.getLink();
			}
		}
	}

	// custom function to find max from player score
	public Player findMaxPlayer() {
		if (head == null) {
			return null;
		} else {
			int max = Integer.MIN_VALUE;
			Player data = new Player(null, -1);

			Node temp = head;

			while (temp != null) {
				Player player = (Player) temp.getData();
				if (player.getScore() > max) {
					max = player.getScore();
					data = player;
				}
				temp = temp.getLink();
			}
			return data;
		}
	}

}
