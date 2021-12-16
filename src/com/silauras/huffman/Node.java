package com.silauras.huffman;

public class Node {
    protected Long weight;
    protected byte[] values;
    protected Node leftNode;
    protected Node rightNode;

    protected Node(Long weight, byte[] value) {
        this.weight = weight;
        this.values = value;
    }

    protected Node(Long weight, byte[] leftNodeValues, byte[] rightNodeValues, Node leftNode, Node rightNode) {
        this.weight = weight;
        values = new byte[leftNodeValues.length + rightNodeValues.length];
        System.arraycopy(leftNodeValues, 0, values, 0, leftNodeValues.length);
        System.arraycopy(rightNodeValues, 0, values, leftNodeValues.length, rightNodeValues.length);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    protected boolean valueContains(byte value) {
        for (byte b : values) {
            if (b == value) {
                return true;
            }
        }
        return false;
    }

    public String pathToValue(byte value) {
        Node pointerNode = this;
        StringBuilder code = new StringBuilder();
        while (pointerNode.values.length != 1) {
            if (pointerNode.rightNode.valueContains(value)) {
                code.append("0");
                pointerNode = pointerNode.rightNode;
            } else if (pointerNode.leftNode.valueContains(value)) {
                code.append("1");
                pointerNode = pointerNode.leftNode;
            }
        }
        return code.toString();
    }

    protected Node childByValue(byte value) {
        Node pointerNode = this;
        StringBuilder code = new StringBuilder();
        while (pointerNode.values.length != 1) {
            if (pointerNode.rightNode.valueContains(value)) {
                pointerNode = pointerNode.rightNode;
            } else if (pointerNode.leftNode.valueContains(value)) {
                pointerNode = pointerNode.leftNode;
            }
        }
        return pointerNode;
    }
}
