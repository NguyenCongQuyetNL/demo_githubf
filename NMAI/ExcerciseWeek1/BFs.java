package praticalLTDT.lap1.NMAI.ExcerciseWeek1;

import java.util.*;

public class BFs {
    public static  void bfsUsingQueue(Node inital,int goal){
        Node node =inital;
        Queue<Node>queue = new ArrayDeque<>();
        while(node.state!=goal) {
            for (Node n : node.neighbours) {
                if (n.state == goal) {
                    Node temp =node;
                    node=n;
                    node.parent=temp;
                    break;
                }else{
                if(!queue.contains(n)&&n.parent==null)queue.add(n);
                n.parent=node;
                }
            }
            if(node.state==goal)break;
            node = queue.poll();

        }
        if(node.state==goal){
            String s ="";
            while(node!=inital){
                s = node.state+"->"+s;
                node=node.parent;
            }
            System.out.println(inital.state +"->"+s);
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

        BFs bfsExample = new BFs();
        System.out.println("The BFS traversal of the graph using queue ");
        bfsExample.bfsUsingQueue(node40,70);
    }
    }
