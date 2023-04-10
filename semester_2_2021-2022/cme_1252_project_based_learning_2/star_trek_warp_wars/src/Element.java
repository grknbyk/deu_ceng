public class Element {
    private char element_type; //1,2,3,4,5,C,P,*,=
    private int x, y, active_time; //coordinates & the time left
    private boolean isMoving; //moving status of elements
    private char performer; //Who inserted to the game field?
    private CircularQueue restrictions; //Holds the traps which affect the element

    public Element(char element_type, int x, int y, int active_time, boolean isMoving, char performer) {
        this.element_type = element_type;
        this.x = x;
        this.y = y;
        this.isMoving = isMoving;
        this.active_time = active_time;
        this.performer = performer;
        this.restrictions = new CircularQueue(10);//element handles max 10 trap device at a time.
    }

 

	public void setElementType(char element_type) {
        this.element_type = element_type;
    }

    public char getElementType() {
        return element_type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public boolean getisMoving() { 
        return isMoving; 
    }

    public void setisMoving(boolean isMoving) { 
        this.isMoving = isMoving;
    }

    public void setActive_time(int active_time) {
        this.active_time = active_time;
    }

    public int getActive_time() {
        return active_time;
    }

    public char getPerformer() {return performer;}

    public void setPerformer(char performer) {this.performer = performer;}

    public CircularQueue getRestrictions() {return restrictions;}

    public void setRestrictions(CircularQueue restrictions) {this.restrictions = restrictions;}
}
