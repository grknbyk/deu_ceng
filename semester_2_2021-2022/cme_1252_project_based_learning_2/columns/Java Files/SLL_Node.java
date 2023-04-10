
public class SLL_Node {
    private Object data;
    private SLL_Node link; // next

    public SLL_Node(Object dataToAdd) {
        data = dataToAdd;
        link = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;

    }

    public SLL_Node getLink() {
        return link;

    }

    public void setLink(SLL_Node link) {
        this.link = link;
    }
}