
public class ColumnNode {
	private Object data;
	private ColumnNode next;

	public ColumnNode(Object data) {
		this.data = data;
		this.next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ColumnNode getNext() {
		return next;
	}

	public void setNext(ColumnNode next) {
		this.next = next;
	}
}
