package org.jgrapht.alg.cycle;

import org.jgrapht.alg.util.ReadFile;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UndirectedCycleTestBigGraph
{

    @Test
    public void test()
    {
        PatonCycleBase<String, DefaultEdge> patonFinder =
                new PatonCycleBase<String, DefaultEdge>();

        testAlgorithm(patonFinder);
    }

    private void testAlgorithm(
            UndirectedCycleBase<String, DefaultEdge>
                    finder)
    {
        final SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<String, DefaultEdge>
                (
                        new ClassBasedEdgeFactory<String, DefaultEdge>
                                (
                                        DefaultEdge.class
                                )
                );
        String json=new ReadFile("src/test/resources/graph_PANCAKESWAPV2_extended.json").getContent();
        json=json.replace("null","");
        String stuff=json.substring(0,1);
        String stuffLast=json.substring(json.length()-2,json.length()-1);
        json=json.replace(stuff,"");
        json=json.replace(stuffLast,"");
        json=json.replace("\"","");
        String[] array=json.split(",");
        Set<String> vertices = new HashSet<>(Arrays.asList(array));
        vertices.forEach(graph::addVertex);
        finder.setGraph(graph);
        for(int i=0;i<array.length;i=i+2)
        {
            graph.addEdge(array[i],array[i+1]);
        }

        List<List<String>> list=finder.findCycleBase();
        System.out.println("Number of Cycles: "+list.size());
        int max=list.stream().map(List::size).max(Integer::compare).get();
        System.out.println("Longest Cycle size:"+ max);
        int min=list.stream().map(List::size).min(Integer::compare).get();
        System.out.println("Shortest Cycle size:"+ min);

        assertFalse(list.isEmpty());
    }

}
