public class SinglyLinkedList {

	private SLL_Node head;

	public void add(Object data) {
		if (head == null) {
			SLL_Node newNode = new SLL_Node(data);
			head = newNode;
		} else {
			SLL_Node temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			SLL_Node newNode = new SLL_Node(data);
			temp.setLink(newNode);
		}
	}

	public int size() {
		if (head == null) {
			return 0;
		} else {
			int count = 0;
			SLL_Node temp = head;

			while (temp != null) {
				temp = temp.getLink();
				count++;
			}
			return count;
		}
	}

	public void display() {
		if (head == null) {
			System.out.println("List is empty.");
		} else {
			SLL_Node temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}

		}
	}

	public void remove(Object dataToDelete) {
		if (head == null) {
			System.out.println("Linked list is empty.");
		} else {
			while ((Integer) head.getData() == (Integer) dataToDelete)
				head = head.getLink();

			SLL_Node temp = head;
			SLL_Node previous = null;
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

	public int findMax() {
		if (head == null) {
			System.err.println("The Linked List is empty");
			return Integer.MIN_VALUE;
		} else {
			int maxVal = Integer.MIN_VALUE;

			SLL_Node temp = head;

			while (temp != null) {
				if ((int) temp.getData() > maxVal) {
					maxVal = (int) temp.getData();
				}
				temp = temp.getLink();
			}
			return maxVal;
		}
	}

	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty.");
			return false;
		} else {
			SLL_Node temp = head;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data) {
					return true;
				}
				temp = temp.getLink();
			}
			return false;
		}
	}

	// returns head data of the sll
	public Object get() {
		if (head == null) {
			System.out.println("List is empty.");
			return -1;
		} else {
			return head.getData();
		}

	}

	// returns data due to index number
	public Object get(int index) {

		if (head == null) {
			return -1;
		} else {
			SLL_Node temp = head;

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
			System.out.println("Link is empty.");
			return;
		}
		if ((Integer) head.getData() == (Integer) dataToDelete) {
			head = head.getLink();
		} else {
			SLL_Node previous = head;
			SLL_Node current = head.getLink();

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

	// delete the last node from sll
	public void pop() {

		if (head == null) {
			System.out.println("Link is empty.");
			return;
		}
		if (head.getLink() == null) {
			head = null;
		} else {
			SLL_Node previous = null;
			SLL_Node current = head;
			while (current != null) {
				if (current.getLink() == null) {
					previous.setLink(current.getLink());
					break;
				}

				previous = current;
				current = current.getLink();
			}
		}

	}

	// delete the data from sll due to index
	public void pop(int index) {
		if (head == null) {
			System.out.println("Link is empty.");
			return;
		}
		if (head.getLink() == null) {
			head = null;
		} else {
			SLL_Node previous = null;
			SLL_Node current = head;
			int count = 0;
			while (current != null) {
				if (count == index) {
					previous.setLink(current.getLink());
					break;
				}
				count++;
				previous = current;
				current = current.getLink();
			}
		}
	}
}
