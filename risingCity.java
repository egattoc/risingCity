/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package risingcity;

import java.io.BufferedReader;
import java.io.*;
import java.util.List;

/**
 *
 * @author Anushka
 */
public class risingCity {

    /**
     * @param wr
     * @param tree
     * @param bN
     * @param counter
     * @param global
     * @throws IOException
     */
    
    public static void printBuilding(BufferedWriter wr,RBTree tree,int bN,int counter,int global) throws IOException{
        RBNode rb = tree.search(bN);
        if (rb == null){
            wr.write("(0,0,0)");
            return;
        }
        // this condition helps us to check later when the work was done on the building
        if (counter - rb.heapNode.startDay <= 4){
            int temp = global-counter;
            int temp1 = rb.heapNode.executedTime;
            wr.write("(" + bN + "," + (temp1-temp) + "," + rb.heapNode.totalTime + ")");
        }
        else{
            wr.write("(" + bN + "," + rb.heapNode.executedTime + "," + rb.heapNode.totalTime + ")");
        }
    }
    
    
    public static void printOneBuilding(String line,RBTree tree,BufferedWriter wr,int global) throws IOException{
        int bN = Integer.parseInt(line.split(":")[1].split("\\(")[1].split("\\)")[0]);
        int counter = Integer.parseInt(line.split(":")[0]);
        printBuilding(wr,tree,bN,counter,global);
        wr.newLine(); 
    }
    
    public static void printMultipleBuildings(String line,RBTree tree,BufferedWriter wr,int global)throws IOException{
        int arg1 = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[0]);
        int arg2 = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[1].split("\\)")[0]);
        int counter = Integer.parseInt(line.split(":")[0]);
        List<RBNode> rbList = tree.searchRange(arg1, arg2);
        if (rbList.isEmpty()){
            wr.write("(0,0,0)");
        }
        for(RBNode n:rbList){
            printBuilding(wr,tree,n.bNum,counter,global);
            if (rbList.indexOf(n)+1 != rbList.size())
                wr.write(",");
        }
        wr.newLine(); 
    }
    
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        // creating the minheap and the rbtree
        // creating the objects to read and write to file
        MinHeap heap = new MinHeap();
        RBNode rbn;
        BuildingNode bnRef;
        RBTree tree = new RBTree();
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter wr = new BufferedWriter(new FileWriter("output_file.txt"));
        String line;

        line = br.readLine();
        
        // global is set to value of first input command in input file
        int global = Integer.parseInt(line.split(":")[0]);
        // first building is inserted into minheap and RBT
        int bldgNum = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[0]);
        int totalTime = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[1].split("\\)")[0]);
        rbn = new RBNode(bldgNum);
        bnRef = heap.insert(bldgNum,0,totalTime,rbn);
        rbn.heapNode = bnRef;
        tree.insert(rbn);
        line = br.readLine();
        // both conditions are necessary
        while (line != null || heap.Heap[1] != null) {
            if (heap.Heap[1] != null) {
                // if difference is 5 or more days, incremented by 5
                int eT = heap.Heap[1].executedTime;
                int tT = heap.Heap[1].totalTime;
                heap.Heap[1].startDay = global + 1;
                if (tT - eT > 5) {
                    heap.Heap[1].executedTime += 5;
                    global += 5;
                    heap.minHeapify(1);
                } else {
                    // else incremented by tT-eT
                    heap.Heap[1].executedTime += (tT - eT);
                    global += (tT - eT);
                    if (line != null && Integer.parseInt(line.split(":")[0]) <= global && line.contains("Print")){
                        if(line.contains(","))
                            printMultipleBuildings(line,tree,wr,global);
                        else
                            printOneBuilding(line,tree,wr,global);
                        line = br.readLine();
                    }
                    // the node is noew deleted from both minheap and RBT
                    BuildingNode deletedNode = heap.extractMin();
                    tree.delete(deletedNode.buildingNum);
                    wr.write("(" + deletedNode.buildingNum + "," + global + ")");
                    wr.newLine();
                }
            }
            else{
                // this is required when there is some input left but minheap is empty
                global = Integer.parseInt(line.split(":")[0]);
            }
            // reading all commands for which the counter is less than or equal to global value
            while (line != null && Integer.parseInt(line.split(":")[0]) <= global) {
                if(line.contains("Print")){
                    // code for printing
                    if(line.contains(",")){
                        printMultipleBuildings(line,tree,wr,global);      
                    }
                    else{
                        printOneBuilding(line,tree,wr,global);  
                    }
                }
                else if (line.contains("Insert")) {
                    // code for insertion
                    bldgNum = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[0]);
                    if (tree.search(bldgNum) != null){
                        wr.write("Duplicate building present!");
                        wr.newLine();
                        System.exit(0);
                    }
                    // building is inserted and minheap and RBT are restructured
                    totalTime = Integer.parseInt(line.split(":")[1].split("\\(")[1].split(",")[1].split("\\)")[0]);
                    rbn = new RBNode(bldgNum);
                    bnRef = heap.insert(bldgNum,0,totalTime,rbn);
                    rbn.heapNode = bnRef;
                    tree.insert(rbn);
                }
                line = br.readLine();
            }
        }
        wr.newLine();
        wr.close();
    }

}
