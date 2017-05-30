package com.rtt.cnmining;

import java.util.ArrayList;
import java.util.Random;

public class RTTgraph {

    private ArrayList<RTTnode> nodes;
    private ArrayList<RTTedge> edges;
    private String name;

    public RTTgraph(){
        this.name = "Activity Diagram";
        this.nodes = new ArrayList<RTTnode>();
        this.edges = new ArrayList<RTTedge>();
    }

    public RTTgraph(String name){
        this.nodes = new ArrayList<RTTnode>();
        this.edges = new ArrayList<RTTedge>();
        this.name = name;
    }

    public void add(RTTnode node){
        if(this.nodes.contains(node) == false)
            this.nodes.add(node);
    }

    public void add(RTTedge edge){
        if(this.edges.contains(edge) == false)
            this.edges.add(edge);
    }

    public ArrayList<RTTnode> nodes(){
        return this.nodes;
    }

    public RTTnode node(String name){
        for(RTTnode node: this.nodes()){
            if(node.name.equals((name)))
                return node;
        }
        return null;
    }

    public ArrayList<RTTedge> edges(){
        return this.edges;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();

        str.append("RTTgraph:" + this.name);
        str.append("\nNodes:");
        for (RTTnode node: this.nodes()) {
            str.append("\n");
            str.append(node);
        }

        str.append("\nEdges:");
        for (RTTedge edge: this.edges()) {
            str.append("\n");
            str.append(edge);
        }

        return str.toString();
    }

    public String toXMI(){
        StringBuilder xmi = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        Random random = new Random();

        xmi.append("\n");
        xmi.append("<uml:Model xmi:version=\"20131001\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\" " +
                "xmlns:uml=\"http://www.eclipse.org/uml2/5.0.0/UML\" xmi:id=\"" +
                random.nextInt(99999999)
                + "\" name=\"" + this.name + "\">");
        xmi.append("\n");
        xmi.append("<packagedElement xmi:type=\"uml:Activity\" xmi:id=\"" +
                random.nextInt(99999999)
                + "\" name=\"" + this.name +
                "\" node=\"");
        for(RTTnode node: this.nodes()){
            xmi.append(node.id + " ");
        }
        xmi.append("\">");

        for(RTTnode node: this.nodes()){
            xmi.append("\n");
            xmi.append(node.toXMI());
        }


        xmi.append("\n");
        xmi.append("</packagedElement>");
        xmi.append("\n");
        xmi.append("</uml:Model>");

        return xmi.toString();
    }

}
