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
public class RBNode {

        public static final RBNode nullValue = new RBNode(-999, RBTree.COLOR.BLACK);
        public int bNum;
        public RBNode left = nullValue;
        public RBNode right = nullValue;
        public RBNode parent = nullValue;
        public RBTree.COLOR color;
        public BuildingNode heapNode;


        // constructors
        public RBNode(int bNum, BuildingNode heapNode){
            this.bNum = bNum;
            this.heapNode = heapNode;
        }

        
        public RBNode(int bNum){
            this.bNum = bNum;
        }

        
        public RBNode(int bNum, RBTree.COLOR color){
            this.bNum = bNum;
            this.color = color;
        }


}
