package net.kasterma.algos;

import lombok.Data;

import java.util.Optional;

/**
 * Binary search tree
 */
class BST<Key, Value> {
    private Node _root;

    private BST() {
        _root = null;
    }

    static <Key, Value> BST<Key, Value> empty() {
        return new BST<Key,Value>();
    }

    void insert(Key key, Value value) {

    }

    Optional<Value> find(Key key) {
        return Optional.empty();
    }

    public String toString() {
        if (_root == null) {
            return "()";
        } else {
            return _root.toString();
        }
    }

    @Data
    class Node {
        private final Key _key;
        private Value _value;
        private Node _left;
        private Node _right;

        Node(final Key key, final Value value) {
            _key = key;
            _value = value;
            _left = null;
            _right = null;
        }
    }
}
