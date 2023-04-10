
public class LineNode {
	private Object data;
	private LineNode down;
	private ColumnNode right;

	public LineNode(Object data) {
		this.data = data;
		this.down = null;
		this.right = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public LineNode getDown() {
		return down;
	}

	public void setDown(LineNode down) {
		this.down = down;
	}

	public ColumnNode getRight() {
		return right;
	}

	public void setRight(ColumnNode right) {
		this.right = right;
	}

}
