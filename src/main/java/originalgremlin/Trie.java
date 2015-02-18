package originalgremlin;

import java.util.*;

public class Trie {
    Node root;
    int size;

    public Trie () {
        root = new Node();
        size = 0;
    }

    public Trie (Iterable<String> keys) {
        this();
        insert(keys);
    }

    public int getSize () {
        return size;
    }

    public int getSize (String prefix) {
        int rv = 0;
        Stack<Node> stack = new Stack<>();
        Node current = find(prefix);
        if (current != null) {
            stack.push(current);
        }
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (current.isTerminal()) {
                rv++;
            }
            for (Node child : current.children) {
                if (child != null) {
                    stack.push(child);
                }
            }
        }
        return rv;
    }

    public boolean isEmpty () {
        return getSize() == 0;
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

    public void insert (Iterable<String> keys) {
        for (String key : keys) {
            insert(key);
        }
    }

    public void delete (String s) {
        Node node = find(s);
        if ((node != null) && (node.isTerminal())) {
            node.clearTerminal();
            size--;
        }
    }

    public void clear () {
        root = new Node();
        size = 0;
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

    public Collection<String> getAll () {
        return getAll(new StringBuilder(), root);
    }

    public Collection<String> getAll (String prefix) {
        return getAll(new StringBuilder(prefix), find(prefix));
    }

    private Collection<String> getAll (StringBuilder prefix, Node root) {
        LinkedList<String> rv = new LinkedList<>();
        if (root == null) {
            return rv;
        } else {
            if (root.isTerminal()) {
                rv.addLast(prefix.toString());
            }
            for (int i = 0; i < Node.BRANCHES; i++) {
                Node child = root.children[i];
                if (child != null) {
                    prefix.append(Character.toChars(i));
                    rv.addAll(getAll(prefix, child));
                    prefix.deleteCharAt(prefix.length() - 1);
                }
            }
            return rv;
        }
    }

    private static class Node {
        final static int BRANCHES = 256;   // extended ASCII
        Node[] children;
        boolean terminal;

        public Node () {
            children = new Node[BRANCHES];
            terminal = false;
        }

        public Node getChild (char c) {
            return children[c];
        }

        public Node addChild (char c) {
            if (children[c] == null) {
                children[c] = new Node();
            }
            return children[c];
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
