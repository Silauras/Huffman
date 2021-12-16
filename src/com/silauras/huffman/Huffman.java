package com.silauras.huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Huffman {
    private final Node startNode;

    public Huffman(Long[] dictionary) {
        List<Node> nodes = new ArrayList<>();
        for (int i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            if (dictionary[i - Byte.MIN_VALUE] != 0) {
                nodes.add(new Node(dictionary[i - Byte.MIN_VALUE], new byte[]{(byte) i}));
            }
        }
        while (nodes.size() != 1) {
            nodes = sortNodes(nodes);
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node node = new Node(leftNode.weight + rightNode.weight, leftNode.values, rightNode.values, leftNode, rightNode);
            nodes.remove(1);
            nodes.remove(0);
            nodes.add(node);
        }
        startNode = nodes.get(0);
        for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            if (dictionary[i - Byte.MIN_VALUE] != 0) {
                System.out.println(i + "(" + startNode.childByValue(i).weight + "): 0b" + startNode.pathToValue(i));
            }
        }

    }

    public Node getStartNode() {
        return startNode;
    }


    //we will use quicksort
    //arraylist will be used because when we will code, we will need add and remove 2 elements, and add one
    //in the worst case, we will have 256 sorts
    private List<Node> sortNodes(List<Node> nodes) {
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        for (Node node : nodes) {
            max = Long.max(max, node.weight);
            min = Long.min(min, node.weight);
        }
        return quickSort(nodes, 0, nodes.size() - 1);
    }

    private int partition(List<Node> nodes, int begin, int end) {
        Node pivotKey = nodes.get(begin);
        while (begin < end) {
            while (begin < end && nodes.get(end).weight >= pivotKey.weight) {
                end--;
            }
            nodes.set(begin, nodes.get(end));
            while (begin < end && nodes.get(begin).weight <= pivotKey.weight) {
                begin++;
            }
            nodes.set(end, nodes.get(begin));
        }
        nodes.set(begin, pivotKey);
        return begin;
    }

    private List<Node> quickSort(List<Node> nodes, int begin, int end) {
        if (begin < end) {
            int pivotLoc = partition(nodes, begin, end);
            quickSort(nodes, begin, pivotLoc - 1);
            quickSort(nodes, pivotLoc + 1, end);
        }
        return nodes;
    }

    public Map<Byte, String> getDictionary() {
        Map<Byte, String> dictionary = new HashMap<>();
        for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            if (startNode.valueContains(i)) {
                dictionary.put(i, startNode.pathToValue(i));
            }
        }
        return dictionary;
    }
}
