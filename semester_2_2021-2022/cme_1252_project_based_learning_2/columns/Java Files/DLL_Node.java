
public class DLL_Node {

	private Object data;
	private DLL_Node prev;
	private DLL_Node next;

	public DLL_Node(Object dataToAdd) {
		data = dataToAdd;
		prev = null;
		next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DLL_Node getPrev() {
		return prev;
	}

	public void setPrev(DLL_Node prev) {
		this.prev = prev;
	}

	public DLL_Node getNext() {
		return next;
	}

	public void setNext(DLL_Node next) {
		this.next = next;
	}

}
