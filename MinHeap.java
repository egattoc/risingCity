/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package risingcity;

/**
 *
 * @author Anushka
 */


public class MinHeap {

    // represents the MinHeap made using an array of BuildingNode
    public BuildingNode Heap[] = new BuildingNode[2005];
    public int current = 1;

    // returns the parent index
    public int parent(int index) {
        return index / 2;
    }

    // returns the left child
    public int left_child(int index) {
        return 2 * index;
    }

    // returns the right child
    public int right_child(int index) {
        return 2 * index + 1;
    }

    // on inserting a node, it is placed in proper position in the MinHeap
    public BuildingNode insert(int buildingNum, int executedTime, int totalTime, RBNode rbn) {
        BuildingNode node = new BuildingNode(buildingNum, executedTime, totalTime,rbn);
        Heap[current] = node;
        buildMinHeap();
        current++;
        return node;
    }

    // this function places the newly added node in the proper position
    public void buildMinHeap() {
        int i = current;
        BuildingNode temp;
        while (i != 1 && Heap[i].executedTime <= Heap[parent(i)].executedTime) {
            if (Heap[i].executedTime == Heap[parent(i)].executedTime) {
                if (Heap[i].buildingNum > Heap[parent(i)].buildingNum) {
                    return;
                }
            }
            temp = Heap[parent(i)];
            Heap[parent(i)] = Heap[i];
            Heap[i] = temp;
            i = parent(i);
        }
    }

    // this function removes the root node of the MinHeap
    public BuildingNode extractMin() {
        if (current == 2) {
            BuildingNode node = Heap[1];
            current--;
            Heap[1] = null;
            return node;
        }
        BuildingNode node = Heap[1];
        Heap[1] = Heap[current - 1];
        Heap[current - 1] = null;
        current--;
        minHeapify(1);
        return node;
    }

    // function to print and view the MinHeap
    public void printMinHeap() {
        int i = 1;
        while (Heap[i] != null) {
            System.out.println("Parent: " + Heap[i].executedTime + " Bldg: " + Heap[i].buildingNum);
            if (Heap[left_child(i)] != null) {
                System.out.print("Left: " + Heap[left_child(i)].executedTime);
            } else {
                System.out.print("Left: " + Heap[left_child(i)]);
            }
            if (Heap[right_child(i)] != null) {
                System.out.print("Right: " + Heap[right_child(i)].executedTime);
            } else {
                System.out.print("Right: " + Heap[right_child(i)]);
            }
            i++;
        }
    }
    
    // this function shows the values of the array that represents the MinHeap
    public void printHeap(){
        for(int i = 1; i < Heap.length; i++){
            if (Heap[i] == null)
                return;
            System.out.print("Bldg: " + Heap[i].buildingNum +" exec Time: "+Heap[i].executedTime);
        }
    }

    // this function always positions the node with smallest key value
    // at the root
    public void minHeapify(int i) {
        int l = left_child(i);
        int r = right_child(i);
        int smallest = i;
        BuildingNode temp;
        if (Heap[l] != null && Heap[l].executedTime <= Heap[i].executedTime){
            if (Heap[l].executedTime == Heap[i].executedTime){
                if (Heap[l].buildingNum < Heap[i].buildingNum){
                    smallest = l;
                }
            }
            else{
                smallest = l;
            }   
        }
        if (Heap[r] != null && Heap[r].executedTime <= Heap[smallest].executedTime){
            if (Heap[r].executedTime == Heap[smallest].executedTime){
                if (Heap[r].buildingNum < Heap[smallest].buildingNum){
                    smallest = r;
                }
            }
            else{
                smallest = r;
            }
        }
        if (smallest != i) {
            temp = Heap[smallest];
            Heap[smallest] = Heap[i];
            Heap[i] = temp;
            minHeapify(smallest);
        }
    }
}
