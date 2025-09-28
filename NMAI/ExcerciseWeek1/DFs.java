package praticalLTDT.lap1.NMAI.ExcerciseWeek1;

import java.util.Stack;

public class DFs {
    public void dfsUsingStack(Node inital,int goal){
        Node current = inital;
        Stack<Node>stack = new Stack<>();
        while(current.state!=goal) {
            for (Node n : current.neighbours) {
                if (n.state == goal) {
                    Node temp = current;
                    current = n;
                    current.parent = temp;
                    break;
                } else {
                    if (!stack.contains(n) && n.parent==null) {
                        stack.push(n);
                        n.parent = current;
                    }
                }
            }
            if (current.state == goal) break;
            current = stack.pop();
        }
            if(current.state==goal){
                String result = "";
                Node n =current;
                while(n!=inital){
                    result = n.state +"->" +result;
                    n=n.parent;
                }
                System.out.println(n.state+"->"+result);
            }

    }

    public static void main(String[] args) {
        Node node40 =new Node(40);
        Node node10 =new Node(10);
        Node node20 =new Node(20);
        Node node30 =new Node(30);
        Node node60 =new Node(60);
        Node node50 =new Node(50);
        Node node70 =new Node(70);

        node40.addNeighbour(node10);
        node40.addNeighbour(node20);
        node10.addNeighbour(node30);
        node20.addNeighbour(node10);
        node20.addNeighbour(node30);
        node20.addNeighbour(node60);
        node20.addNeighbour(node50);
        node30.addNeighbour(node60);
        node60.addNeighbour(node70);
        node50.addNeighbour(node70);

        DFs dfsExample = new DFs();
        System.out.println("The DFS traversal of the graph using queue ");
        dfsExample.dfsUsingStack(node40,70);
    }
}
