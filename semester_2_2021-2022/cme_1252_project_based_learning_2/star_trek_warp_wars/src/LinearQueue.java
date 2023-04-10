public class LinearQueue {
	private int rear, front;
	private Object[] elements;

	public LinearQueue(int capacity) {
		elements = new Object[capacity];
		rear = -1;
		front = 0;
	}

	public boolean isEmpty() {
		if (front > rear) {
			return true;
		} else
			return false;
	}

	public Object dequeue() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
			return null;
		} else {
			Object retdate = elements[front];
			elements[front] = null;
			front++;
			return retdate;
		}
	}

	public boolean isFull() {
		return rear + 1 == elements.length;
	}

	public void enqueue(Object data) {
		if (isFull()) {
			System.out.println("Queue overflow");
		} else {
			rear++;
			elements[rear] = data;
		}
	}

	public Object peek() {
		if (isEmpty()) {
			System.out.println("Queue is empty");
			return null;
		} else {
			return elements[front];
		}
	}

	public int size() {
		return rear - front + 1;
	}
}