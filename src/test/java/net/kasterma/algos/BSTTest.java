package net.kasterma.algos;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class BSTTest {
    @Test
    void test1() {
        BST<Integer, Integer> bst = BST.empty();
        bst.insert(0, 0);
        bst.insert(2, 2);
        log.info(bst.toString());
        assertEquals(0, (int) bst.find(0).get());
        assertEquals(2, (int) bst.find(2).get());
        assertEquals(0, (int) bst.min().get());
        assertEquals(2, (int) bst.max().get());

        bst.insert(-1, -1);
        bst.insert(1, 1);
        log.info(bst.toString());
        assertEquals(0, (int) bst.find(0).get());
        assertEquals(1, (int) bst.find(1).get());
        assertEquals(-1, (int) bst.min().get());
        assertEquals(2, (int) bst.max().get());
    }

}