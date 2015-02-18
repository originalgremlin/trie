package originalgremlin;

import java.util.*;

public class Trie {
    Node root;
    int size;

    public Trie () {
        root = new Node();
        size = 0;
    }

    public int getSize () {
        return size;
    }

    public void insert (String s) {
        Node current = root;
        for (char c : s.toCharArray()) {
            current = current.addChild(c);
        }
        if (!current.isTerminal()) {
            current.setTerminal();
            size++;
        }
    }

    public void delete (String s) {
        Node node = find(s);
        if ((node != null) && (node.isTerminal())) {
            node.clearTerminal();
            size--;
        }
    }

    public boolean contains (String s) {
        Node node = find(s);
        return (node != null) && (node.isTerminal());
    }

    private Node find (String s) {
        Node current = root;
        for (char c : s.toCharArray()) {
            current = current.getChild(c);
            if (current == null) {
                break;
            }
        }
        return current;
    }

    public Vector<String> suffixes (String s) {
        Vector<String> rv = new Vector<>();
        Stack<Node> stack = new Stack<>();
        stack.push(find(s));
        Node current;
        do {
            current = stack.pop();
            if (current == null) {
                continue;
            }
            if (current.isTerminal()) {
                rv.add(current.toString());
            }
            for (Node child : current.children.values()) {
                stack.add(child);
            }
        } while (!stack.isEmpty());
        return rv;
    }

    private static class Node {
        boolean terminal;
        Map<Character, Node> children;

        public Node () {
            terminal = false;
            children = new TreeMap<>();
        }

        public Node getChild (Character c) {
            return children.get(c);
        }

        public Node addChild (Character c) {
            if (children.containsKey(c)) {
                return children.get(c);
            } else {
                return children.put(c, new Node());
            }
        }

        public boolean isTerminal () {
            return terminal;
        }

        public void clearTerminal () {
            terminal = false;
        }

        public void setTerminal () {
            terminal = true;
        }
    }
}
