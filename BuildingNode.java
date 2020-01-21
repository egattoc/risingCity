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
public class BuildingNode {
    //startDay is required to calculate executed time correctly
    public int startDay;
    public int buildingNum;
    public int executedTime;
    public int totalTime;
    public RBNode rbNode;
    
    
    // constructor
    public BuildingNode(int buildingNum, int executedTime, int totalTime, RBNode rbn) {
        this.startDay = 0;
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
        this.rbNode = rbn;
    }
}
