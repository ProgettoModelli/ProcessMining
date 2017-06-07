package com.rtt.cnmining;

import org.processmining.models.flexiblemodel.Flex;
import org.processmining.models.flexiblemodel.FlexNode;
import org.processmining.models.flexiblemodel.SetFlex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class RTTmining {

    private Flex causalnet;
    private FlexInspector inspector;

    public RTTmining(Flex diagram){
        this.causalnet = diagram;
        this.inspector = new FlexInspector(this.causalnet);
    }

    public RTTgraph process(){
        // Inizializza il grafo inserendovi i nodi rappresentanti le attività
        RTTgraph graph = new RTTgraph();

        ArrayList<String> startActivities = this.inspector.startActivities();
        ArrayList<String> endActivities = this.inspector.endActivities();

        for (String activity: this.inspector.activities()){
            RTTnode node = new RTTnode(activity);

            if(startActivities.contains(activity))
                node.initialNode();
            else if(endActivities.contains(activity))
                node.finalNode();

            graph.add(node);
        }

        // Conversione degli output bindings
        this.convertOutputBindings(graph);
        System.out.println();
        // Conversione degli input bindings
        this.convertInputBindings(graph);

        return graph;
    }

    private void convertOutputBindings(RTTgraph graph){
        System.out.println("[RTTmining] computing otuput bindings...");

        for(FlexNode node: this.causalnet.getNodes()){
            Set<SetFlex> outputs = node.getOutputNodes();
            RTTnode current = graph.node(node.getLabel());

            if(outputs.size() > 1){
                // Aggiungi un branch
                RTTnode branchNode = new RTTnode("Branch"+node.getLabel());
                branchNode.branch();
                graph.add(branchNode);

                graph.add(new RTTedge(current, branchNode));
                current = branchNode;
            }

            for(SetFlex output: outputs){
                System.out.println(node.getLabel() + " -> " + output);

                RTTnode beginNode = current;

                // Inserisci un fork
                if(output.size() > 1){
                    RTTnode forkNode = new RTTnode("Fork"+current.name);
                    forkNode.fork();
                    graph.add(forkNode);

                    graph.add(new RTTedge(beginNode, forkNode));

                    beginNode = forkNode;
                }

                // Aggiungi gli archi
                Iterator<FlexNode> i = output.iterator();
                while(i.hasNext()){
                    FlexNode n = i.next();

                    RTTnode endNode = graph.node(n.getLabel());
                    if(endNode == null){
                        System.out.println("[Warning:convertOutputBindings] cannot find node: " + n.getLabel());
                        continue;
                    }

                    graph.add(new RTTedge(beginNode, endNode));
                }
            }
        }
    }

    /*
        In questo caso non posso semplicemente inserire gli archi,
        ma devo andare a modificare quelli precedentemente inseriti
        durante la fase di conversione degli output bindings
     */
    private void convertInputBindings(RTTgraph graph){
        System.out.println("[RTTmining] computing input bindings...");

        for(FlexNode node: this.causalnet.getNodes()) {
            Set<SetFlex> inputs = node.getInputNodes();
            RTTnode current = graph.node(node.getLabel());

            for(SetFlex input: inputs) {

                System.out.println(input + " -> " + node.getLabel());

                RTTnode endNode = current;

                // Inserisci un join
                if (input.size() > 1) {
                    RTTnode joinNode = new RTTnode("Join" + current.name);
                    joinNode.join();
                    graph.add(joinNode);

                    graph.add(new RTTedge(joinNode, endNode));
                    endNode = joinNode;
                }

                // Modifica gli archi gli archi
                Iterator<FlexNode> i = input.iterator();
                while(i.hasNext()) {
                    FlexNode n = i.next();

                    boolean trovato = false;
                    for(RTTedge e: graph.edgesEndWith(current))
                    {
                        if(e.begin().name.contains(n.getLabel())) {
                            trovato = true;
                            e.end(endNode);
                        }
                    }
                    if(!trovato){
                        graph.add(new RTTedge(graph.node(n.getLabel()), endNode));
                    }
                }
            }

            // Branch join
            if(inputs.size() > 1){
                RTTnode branchNode = new RTTnode("Branch"+node.getLabel());
                branchNode.branch();
                branchNode = graph.add(branchNode);

                // Aggiorna tutti i collegamenti che puntavano
                // al nodo corrente, verso il branch
                for(RTTedge edge: graph.edgesEndWith(current)){
                    edge.end(branchNode);
                }

                // Collega il branch al nodo corrente
                graph.add(new RTTedge(branchNode, current));
            }
        }
    }

    private void convertInputBindings1(RTTgraph graph) {
        System.out.println("[RTTmining] computing input bindings...");

        for (FlexNode node : this.causalnet.getNodes()) {
            Set<SetFlex> inputs = node.getInputNodes();
            RTTnode current = graph.node(node.getLabel());

            if(inputs.size() > 1){
                // Aggiungi un branch
                RTTnode branchNode = new RTTnode("Branch"+node.getLabel());
                branchNode.branch();
                graph.add(branchNode);

                graph.add(new RTTedge(branchNode, current));
                current = branchNode;
            }

            for(SetFlex input: inputs) {
                System.out.println(input + " -> " + node.getLabel());

                RTTnode endNode = current;

                if(input.size() > 1){
                    RTTnode joinNode = new RTTnode("Join" + current.name);
                    joinNode.join();
                    joinNode = graph.add(joinNode);

                    graph.add(new RTTedge(joinNode, endNode));
                    endNode = joinNode;
                }

                // Aggiungi gli archi
                Iterator<FlexNode> i = input.iterator();
                while(i.hasNext()) {
                    FlexNode n = i.next();
                    for(RTTedge e: graph.edgesEndWith(graph.node(node.getLabel()))){
                        if(e.begin().name.contains(n.getLabel()))
                            e.end(endNode);
                    }
                }
            }
        }
    }

}
