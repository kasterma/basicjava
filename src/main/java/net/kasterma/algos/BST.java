package net.kasterma.algos;

import lombok.Data;

import java.util.Optional;

/**
 * Binary search tree
 */
class BST<Key extends Comparable<Key>, Value> {
    private Node _root;

    private BST() {
        _root = null;
    }

    static <Key extends Comparable<Key>, Value> BST<Key, Value> empty() {
        return new BST<Key,Value>();
    }

    void insert(Key key, Value value) {
        if (_root == null) {
            _root = new Node(key, value);
        } else {
            _root.insert(key, value);
        }

    }

    Optional<Value> find(Key key) {
        if (_root == null) {
            return Optional.empty();
        } else {
            return _root.find(key);
        }
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

        void insert(Key key, Value value) {
            int cmp = key.compareTo(_key);
            if (cmp < 0) {
                if (_left == null) {
                    _left = new Node(key, value);
                } else {
                    _left.insert(key, value);
                }
            } else if (cmp > 0) {
                if (_right == null) {
                    _right = new Node(key, value);
                } else {
                    _right.insert(key, value);
                }
            } else { // cmp == 0
                _value = value;
            }
        }

        Optional<Value> find(Key key) {
            int cmp = key.compareTo(_key);
            if (cmp < 0) {
                if (_left == null) {
                    return Optional.empty();
                } else {
                    return _left.find(key);
                }
            } else if (cmp > 0) {
                if (_right == null) {
                    return Optional.empty();
                } else {
                    return _right.find(key);
                }
            } else { // cmp == 0
                return Optional.of(_value);
            }
        }
    }
}
