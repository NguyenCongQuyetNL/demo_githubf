package praticalLTDT.lap1.NMAI.ExcerciseWeek1;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int state;
    boolean visited;
    List<Node> neighbours;
    Node parent;
    public Node() {
    }
    Node(int state)
    {
        this.state=state;
        this.neighbours=new ArrayList<>();
        this.parent= null;
    }
public void addNeighbour(Node n){
        this.neighbours.add(n);
}
public List addNeighbour(){
        return this.neighbours;

}
}

