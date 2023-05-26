package data;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name;
    private String parentLegion;
    private int marinesCount;
    private String world;

    public Chapter(String name, String parentLegion, int marinesCount, String world){
        this.name = name;
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
        this.world = world;
    }
    public Chapter(){}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getParentLegion(){return parentLegion;}
    public void setParentLegion(String parentLegion){this.parentLegion = parentLegion;}
    public long getMarinesCount(){return marinesCount;}
    public void setMarinesCount(int marinesCount){this.marinesCount = marinesCount;}
    public String getWorld(){return world;}
    public void setWorld(String world){this.world = world;}
    @Override
    public String toString() {
        String infoChap = "";
        infoChap += "Order: " + name + " (" + marinesCount + " soldier)";
        infoChap += "\n        emerged from " + parentLegion;
        infoChap += "\n        In the world: " + world;
        infoChap += "\n";
        return infoChap;
    }
}
