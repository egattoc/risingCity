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

import java.util.LinkedList;
import java.util.List;


public class RBTree {


    public RBNode nullValue;
    public RBNode root;


    public enum COLOR{
        RED, BLACK
    }


    public RBTree(){
        nullValue = RBNode.nullValue;
        root = nullValue;
        root.left = nullValue;
        root.right = nullValue;
    }



    public RBNode search(int bNum){
        return search(root, bNum);
    }



    
    // this function performs the binary search tree operation
    private RBNode search(RBNode root, int bNum){
        if (root == nullValue){
            return null;
        }
        if (root.bNum == bNum){
            return root;
        }
        else if (bNum < root.bNum){
            return search(root.left, bNum);
        }
        else{
            return search(root.right, bNum);
        }
    }



    
    // this function returns the RBNodes that lie within a range of buildingNumbers
    public List<RBNode> searchRange(int bNum1, int bNum2){

        List<RBNode> list = new LinkedList<RBNode>();
        searchRange(root, list, bNum1, bNum2);
        return list;
    }



    // a helper function for the main searchRange
    private void searchRange(RBNode root, List<RBNode> list, int bNum1, int bNum2) {

        if (root == nullValue) {
            return;
        }
        if (bNum1 < root.bNum) {
            searchRange(root.left, list, bNum1, bNum2);
        }
        if (bNum1 <= root.bNum && bNum2 >= root.bNum) {
            list.add(root);
        }
        if (bNum2 > root.bNum) {
            searchRange(root.right, list, bNum1, bNum2);
        }

    }



    
    // right rotation to correct the red black tree
    private RBNode rotateRight(RBNode rbn1){

        RBNode rbn2 = rbn1.left;
        RBNode ar = rbn1.right;
        RBNode bl = rbn2.left;
        RBNode br = rbn2.right;
        rbn1.left = br;
        if(br != nullValue){
            br.parent = rbn1;
        }
        rbn2.parent = rbn1.parent;
        if (rbn1.parent == nullValue){
            root = rbn2;
        }
        else if (rbn1 == rbn1.parent.left){
            rbn1.parent.left = rbn2;
        }
        else {
            rbn1.parent.right = rbn2;
        }
        rbn2.right = rbn1;
        rbn1.parent = rbn2;
        return rbn2;

    }



    
    // left rotation to correct the red black tree
    private RBNode rotateLeft(RBNode rbn1){

        RBNode rbn2 = rbn1.right;
        RBNode al = rbn1.left;
        RBNode bl = rbn2.left;
        RBNode br = rbn2.right;
        rbn1.right = bl;
        if(bl != nullValue){
            bl.parent = rbn1;
        }
        rbn2.parent = rbn1.parent;
        if (rbn1.parent == nullValue){
            root = rbn2;
        }
        else if (rbn1 == rbn1.parent.left){
            rbn1.parent.left = rbn2;
        }
        else {
            rbn1.parent.right = rbn2;
        }
        rbn2.left = rbn1;
        rbn1.parent = rbn2;
        return rbn2;

    }




    // helper function for the insert operation
    public void insert(RBNode p){

        p.color = COLOR.RED;
        if (root == nullValue || root.bNum == p.bNum){
            root = p;
            root.color = COLOR.BLACK;
            root.parent = nullValue;
            return;
        }
        doBSTInsertion(root, p);
        correctInsertion(p);

    }



    // helper function for insertion
    private void doBSTInsertion(RBNode root, RBNode p){

        if (p.bNum < root.bNum){
            if (root.left == nullValue){
                root.left = p;
                p.parent = root;
            }
            else {
                doBSTInsertion(root.left, p);
            }
        }
        else{
            if (root.right == nullValue){
                root.right = p;
                p.parent = root;
            }
            else {
                doBSTInsertion(root.right, p);
            }
        }
    }



    
    // helper function for insertion, to correct errors in structure of the red black tree
    private void correctInsertion(RBNode p){

        RBNode pp = nullValue;
        RBNode gp = nullValue;
        if (p.bNum == root.bNum){
            p.color = COLOR.BLACK;
            return;
        }
        while (root.bNum != p.bNum && p.color != COLOR.BLACK && p.parent.color == COLOR.RED){
            pp = p.parent;
            gp = pp.parent;
            if (pp == gp.left){
                RBNode u = gp.right;
                if (u != nullValue && u.color == COLOR.RED){
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    u.color = COLOR.BLACK;
                    p = gp;
                }
                else {
                    if (pp.right == p){
                        pp = rotateLeft(pp);
                        p = pp.left;
                    }
                    rotateRight(gp);
                    colorChange(pp,gp);
                    p = pp;
                }
            }
            else if (pp == gp.right){
                RBNode u = gp.left;
                if (u != nullValue && u.color == COLOR.RED){
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    u.color = COLOR.BLACK;
                    p = gp;
                }
                else {
                    if (pp.left == p){
                        pp = rotateRight(pp);
                        p = pp.right;
                    }
                    rotateLeft(gp);
                    colorChange(pp,gp);
                    p = pp;
                }
            }
        }
        root.color = COLOR.BLACK;
        
    }



    
    // the b node is moved to the level of node a
    private void moveUp(RBNode a, RBNode b){

        if (a.parent == nullValue){
            root = b;
        }
        else if (a == a.parent.left){
            a.parent.left = b;
        }
        else {
            a.parent.right = b;
        }
        b.parent = a.parent;
        
    }


    // the delete operation on the red black tree
    public boolean delete(RBNode p){

        return delete(p.bNum);

    }



    // deletion is done to maintain the properties of the red black tree
    public boolean delete(int bNum){

        RBNode rbn1 = search(root,bNum);
        if (rbn1 == null){
            return false;
        }
        RBNode rbn2;
        RBNode temp = rbn1;
        COLOR origColor = rbn1.color;
        if (rbn1.left == nullValue){
            rbn2 = rbn1.right;
            moveUp(rbn1, rbn1.right);
        } else if (rbn1.right == nullValue){
            rbn2 = rbn1.left;
            moveUp(rbn1, rbn1.left);
        //If both children are not nullValue
        } else {
            temp = getMinimum(rbn1.right);
            origColor = temp.color;
            rbn2 = temp.right;
            if (temp.parent == rbn1) {
                rbn2.parent = temp;
            }
            else {
                moveUp(temp, temp.right);
                temp.right = rbn1.right;
                temp.right.parent = temp;
            }
            moveUp(rbn1, temp);
            temp.left = rbn1.left;
            temp.left.parent = temp;
            temp.color = rbn1.color;
        }
        if (origColor == COLOR.BLACK) {
            correctDeletion(rbn2);
        }
        return true;

    }



    
    // errors in structure are corrected
    private void correctDeletion(RBNode py){

        while(py!=root && py.color == COLOR.BLACK){
            if(py == py.parent.left){
                RBNode rbn1 = py.parent.right;
                if(rbn1.color == COLOR.RED){
                    rbn1.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    rotateLeft(py.parent);
                    rbn1 = py.parent.right;
                }
                if(rbn1.left.color == COLOR.BLACK && rbn1.right.color == COLOR.BLACK){
                    rbn1.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }
                else if(rbn1.right.color == COLOR.BLACK){
                    rbn1.left.color = COLOR.BLACK;
                    rbn1.color = COLOR.RED;
                    rotateRight(rbn1);
                    rbn1 = py.parent.right;
                }
                if(rbn1.right.color == COLOR.RED){
                    rbn1.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    rbn1.right.color = COLOR.BLACK;
                    rotateLeft(py.parent);
                    py = root;
                }
            } else {
                RBNode v = py.parent.left;
                if(v.color == COLOR.RED){
                    v.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    rotateRight(py.parent);
                    v = py.parent.left;
                }
                if(v.right.color == COLOR.BLACK && v.left.color == COLOR.BLACK){
                    v.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }
                else if(v.left.color == COLOR.BLACK){
                    v.right.color = COLOR.BLACK;
                    v.color = COLOR.RED;
                    rotateLeft(v);
                    v = py.parent.left;
                }
                if(v.left.color == COLOR.RED){
                    v.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    v.left.color = COLOR.BLACK;
                    rotateRight(py.parent);
                    py = root;
                }
            }
        }
        py.color = COLOR.BLACK;
    }



    
    // gets the minimum node from the red black tree
    private RBNode getMinimum(RBNode root){

        while (root.left != nullValue){
            root = root.left;
        }
        return root;

    }


    // swaps colours of two nodes pp and gp
    private void colorChange(RBNode pp, RBNode gp) {

        COLOR temp = pp.color;
        pp.color = gp.color;
        gp.color = temp;

    }

}