public class MultiLinkedList {
    private LineNode head;

    public MultiLinkedList() {
        this.head = null;
    }

    public void addLine(String lineName) {
        LineNode newNode = new LineNode(lineName);
        if (head == null) {
            head = newNode;
        } else {
            LineNode temp = head;
            while (temp.getDown() != null) {
                temp = temp.getDown();
            }
            temp.setDown(newNode);
        }
    }

    public void addCard(String lineName, int cardNumber) {
        if (head == null) {
            System.out.println("There is no line");
        } else {
            ColumnNode newCNode = new ColumnNode(cardNumber);
            LineNode temp = head;
            while (temp != null && !temp.getData().toString().equals(lineName)) {
                temp = temp.getDown();
            }
            if (temp != null) {
                if (temp.getRight() == null)
                    temp.setRight(newCNode);
                else {
                    ColumnNode tempC = temp.getRight();
                    while (tempC.getNext() != null) {
                        tempC = tempC.getNext();
                    }
                    tempC.setNext(newCNode);
                }
            } else {
                System.out.println("There is no line like " + lineName);
            }
        }
    }

    public void deleteLine(String lineName) {
        if (head == null) {
            System.out.println("There is no line");
        } else {
            if (head.getData().toString().equals(lineName))
                head = head.getDown();
            else {
                LineNode temp = head;
                LineNode prev = null;
                while (temp != null && !temp.getData().toString().equals(lineName)) {
                    prev = temp;
                    temp = temp.getDown();
                }
                if (temp != null) {
                    prev.setDown(temp.getDown());
                } else {
                    System.out.println("There is no line like" + lineName);
                }
            }
        }
    }

    public void deleteCard(String lineName, int cardNumber) {
        if (head == null) {
            System.out.println("There is no line");
        } else {
            LineNode temp = head;
            while (temp != null && !temp.getData().toString().equals(lineName)) {
                temp = temp.getDown();
            }
            if (temp != null) {
                if (temp.getRight() == null) {
                    System.out.println("There is no card to delete in the line");
                } else {
                    if (temp.getRight().getData().toString().equalsIgnoreCase(String.valueOf(cardNumber))) {
                        temp.setRight(temp.getRight().getNext());
                    } else {
                        ColumnNode tempC = temp.getRight();
                        ColumnNode prevC = null;
                        while (tempC != null
                                && !tempC.getData().toString().equalsIgnoreCase(String.valueOf(cardNumber))) {
                            prevC = tempC;
                            tempC = tempC.getNext();
                        }
                        if (tempC != null) {
                            prevC.setNext(tempC.getNext());
                        } else {
                            System.out.println("There is no card like " + cardNumber);
                        }
                    }
                }
            } else {
                System.out.println("There is no line like " + lineName);
            }
        }
    }

    public void display() {
        if (head == null) {
            System.out.println("There is no data to display.");
        } else {
            LineNode temp = head;
            while (temp != null) {
                System.out.print(temp.getData().toString() + " | ");
                ColumnNode tempC = temp.getRight();
                while (tempC != null) {
                    System.out.print(tempC.getData().toString() + " ");
                    tempC = tempC.getNext();
                }
                System.out.println();
                temp = temp.getDown();
            }
        }
    }

    public void linePop(String line_name) {
        if (head == null) {
            System.out.println("There is no line.");
        } else {
            LineNode temp = head;
            while (temp.getDown() != null && !line_name.equals(String.valueOf(temp.getData()))) {
                temp = temp.getDown();
            }

            if (!line_name.equals(String.valueOf(temp.getData()))) {
                System.out.println("There is no line named '" + line_name + "'.");
                return;
            }

            ColumnNode tempC = temp.getRight();

            if (tempC == null) {
                System.out.println("There is no data to pop in line '" + line_name + "'.");
            } else if (tempC.getNext() == null) {
                temp.setRight(null);
            } else {
                ColumnNode previous = null;
                ColumnNode current = tempC;
                while (current != null) {
                    if (current.getNext() == null) {
                        previous.setNext(current.getNext());
                        break;
                    }

                    previous = current;
                    current = current.getNext();
                }
            }

        }
    }

    public void linePop(String line_name, int index) {
        if (head == null) {
            System.out.println("There is no line.");
        } else {
            LineNode temp = head;
            while (temp.getDown() != null && !line_name.equals(String.valueOf(temp.getData()))) {
                temp = temp.getDown();
            }

            if (!line_name.equals(String.valueOf(temp.getData()))) {
                System.out.println("There is no line named '" + line_name + "'.");
                return;
            }

            ColumnNode tempC = temp.getRight();

            if (tempC == null) {
                System.out.println("There is no data to pop in line " + line_name + ".");
            } else if (index == 0) {
                temp.setRight(tempC.getNext());
            } else {
                if (index >= lineSize(line_name)) {
                    System.out.println("Index out of bounds.");
                    System.out.println("Index:" + index + "  Size:" + lineSize(line_name) + "  Line:" + line_name);
                }
                int count = 0;
                ColumnNode previous = null;
                ColumnNode current = tempC;
                while (current != null) {
                    if (index == count) {
                        previous.setNext(current.getNext());
                        break;
                    }

                    previous = current;
                    current = current.getNext();
                    count++;
                }
            }

        }
    }

    public String getLineName(int index) {
        if (head == null) {
            System.out.println("There is no line.");
            return null;
        } else {
            LineNode temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getDown();
            }
            return String.valueOf(temp.getData());
        }
    }

    public int lineSize(String line_name) {
        if (head == null) {
            System.out.println("There is no line.");
            return -1;
        } else {
            LineNode temp = head;
            while (temp.getDown() != null && !line_name.equals(String.valueOf(temp.getData()))) {
                temp = temp.getDown();
            }

            if (!line_name.equals(String.valueOf(temp.getData()))) {
                System.out.println("There is no line named '" + line_name + "'.");
                return -1;
            }

            ColumnNode tempC = temp.getRight();

            int count = 0;
            while (tempC != null) {
                count++;
                tempC = tempC.getNext();
            }
            return count;

        }
    }

    public int lineSize(int index) {
        return lineSize(getLineName(index));
    }

    public int getCardFromLine(String line_name, int index) {
        if (head == null) {
            System.out.println("There is no line.");
            return -1;
        } else {
            LineNode temp = head;
            while (temp.getDown() != null && !line_name.equals(String.valueOf(temp.getData()))) {
                temp = temp.getDown();
            }

            if (!line_name.equals(String.valueOf(temp.getData()))) {
                System.out.println("There is no line named '" + line_name + "'.");
                return -1;
            }

            ColumnNode tempC = temp.getRight();

            for (int i = 0; i < index; i++) {
                tempC = tempC.getNext();
            }

            return (int) tempC.getData();

        }
    }

    public boolean isThereAnySet(String line_name) {

        boolean isSet = true;

        LineNode temp = head;
        while (temp.getDown() != null && !line_name.equals(String.valueOf(temp.getData()))) {
            temp = temp.getDown();
        }

        ColumnNode tempC = temp.getRight();

        if ((Integer) tempC.getData() == 1) {
            for (int i = 0; i < 10; i++) {
                if (tempC != null && (Integer) tempC.getData() != i + 1) {
                    isSet = false;
                    break;
                }
                tempC = tempC.getNext();
            }
        } else if ((Integer) tempC.getData() == 10) {
            for (int i = 0; i < 10; i++) {
                if (tempC != null && (Integer) tempC.getData() != 10 - i) {
                    isSet = false;
                    break;
                }
                tempC = tempC.getNext();
            }
        } else {
            isSet = false;
        }

        return isSet;

    }

    public int maxLineSize() {
        int maxSize = -1; // means no line
        LineNode temp = head;
        while (temp != null) {
            if (maxSize < lineSize((String) temp.getData())) {
                maxSize = lineSize((String) temp.getData());
            }
            temp = temp.getDown();
        }
        return maxSize;
    }

    public int getCardFromLine(int x, int y) {
        String lineName = (String) getLineName(x);
        return getCardFromLine(lineName, y);
    }
}
