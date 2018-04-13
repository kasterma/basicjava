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
        return new BST<>();
    }

    void insert(final Key key, final Value value) {
        if (_root == null) {
            _root = new Node(key, value);
        } else {
            _root.insert(key, value);
        }

    }

    Optional<Value> find(final Key key) {
        return _root == null ? Optional.empty() : _root.find(key);
    }

    Optional<Value> min() {
        return _root == null ? Optional.empty() : _root.min();
    }

    Optional<Value> max() {
        return _root == null ? Optional.empty() : _root.max();
    }

    public String toString() {
        return _root == null ? "()" : _root.toString();
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

        void insert(final Key key, final Value value) {
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

        Optional<Value> find(final Key key) {
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

        Optional<Value> min() {
            Node n = this;
            while (n._left != null) {
                n = n._left;
            }
            return Optional.of(n._value);
        }

        Optional<Value> max() {
            Node n = this;
            while (n._right != null) {
                n = n._right;
            }
            return Optional.of(n._value);
        }

        public String toString() {
            String ls;
            if (_left != null) {
                ls = _left.toString();
            } else {
                ls = ".";
            }
            String rs;
            if (_right != null) {
                rs = _right.toString();
            } else {
                rs = ".";
            }
            return "(" + _key.toString() + "->" + _value.toString() + ls + rs + ")";
        }
    }
}
