public class DoublyLinkedList {
	private DLL_Node head;
	private DLL_Node tail;

	public DoublyLinkedList() {
		head = null;
		tail = null;
	}

	public DLL_Node getHead() {
		return head;
	}

	public void setHead(DLL_Node head) {
		this.head = head;
	}

	public DLL_Node getTail() {
		return tail;
	}

	public void setTail(DLL_Node tail) {
		this.tail = tail;
	}

	public void addAsSorted(Object name, double scoredata) {
		DLL_Node newNode = new DLL_Node(name + "-" + scoredata);
		if (head == null) {
			head = newNode;
			tail = newNode;
		} else {
			DLL_Node temp = head;
			if (scoredata >= Double.parseDouble(temp.getData().toString().split("-")[1])) {
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
			} else {
				boolean flag = false;
				while (flag == false && temp.getNext() != null
						&& scoredata < Double.parseDouble(temp.getData().toString().split("-")[1])) {
					if (flag == false
							&& Double.parseDouble(temp.getNext().getData().toString().split("-")[1]) < scoredata) {
						flag = true;
					} else {
						temp = temp.getNext();
					}
				}
				newNode.setPrev(temp);
				newNode.setNext(temp.getNext());
				if (temp.getNext() != null) {
					temp.getNext().setPrev(newNode);
				} else
					tail = newNode;

				temp.setNext(newNode);
			}
		}
	}

	public int size() {
		int count = 0;
		if (head == null) {
			System.out.println("List is empty.");
		} else {
			DLL_Node temp = head;
			while (temp != null) {
				count++;
				temp = temp.getNext();
			}
		}
		return count;
	}

	public void display() {
		if (head == null) {
			System.out.println("List is empty.");
		} else {
			DLL_Node temp = head;
			while (temp != null) {
				System.out.println(temp.getData() + " ");
				temp = temp.getNext();
			}
			System.out.println();
		}
	}

}
