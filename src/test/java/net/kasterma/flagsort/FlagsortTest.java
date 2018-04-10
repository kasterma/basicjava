package net.kasterma.flagsort;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
class FlagsortTest {
    @Test
    void test1() {
        int[] xs = {0, 0, 0, 1, 1, 1, 2, 2, 2};
        ArrayWrap a1 = new ArrayWrap(xs);
        Flagsort fs = new Flagsort(a1);
        fs.sort();
        log.info(a1.toString());
        assertTrue(a1.sorted());
        assertTrue(a1.getCtColor() <= a1.length());
        assertTrue(a1.getCtExchange() <= a1.length());
    }

    @Test
    void test2() {
        int[] xs = {2, 1, 2, 1, 0, 0, 2, 1};
        ArrayWrap a1 = new ArrayWrap(xs);
        Flagsort fs = new Flagsort(a1);
        fs.sort();
        assertTrue(a1.sorted());
        assertTrue(a1.getCtColor() <= a1.length());
        assertTrue(a1.getCtExchange() <= a1.length());
    }

    @Test
    void test3() {
        int[] xs = {1, 2, 0, 1, 0, 2, 1, 1, 0, 0, 2, 1};
        ArrayWrap a1 = new ArrayWrap(xs);
        Flagsort fs = new Flagsort(a1);
        fs.sort();
        assertTrue(a1.sorted());
        assertTrue(a1.getCtColor() <= a1.length());
        assertTrue(a1.getCtExchange() <= a1.length());
    }

    @Test
    void test4() {
        int[] xs = {2, 1};
        ArrayWrap a1 = new ArrayWrap(xs);
        Flagsort fs = new Flagsort(a1);
        fs.sort();
        assertTrue(a1.sorted());
        assertTrue(a1.getCtColor() <= a1.length());
        assertTrue(a1.getCtExchange() <= a1.length());
    }

    @Test
    void test5() {
        int[] xs = {1, 1, 2, 2, 0, 2, 0};
        ArrayWrap a1 = new ArrayWrap(xs);
        Flagsort fs = new Flagsort(a1);
        fs.sort();
        assertTrue(a1.sorted());
        assertTrue(a1.getCtColor() <= a1.length());
        assertTrue(a1.getCtExchange() <= a1.length());
    }

    @Test
    void testGenerator() {
        for (int round = 0; round < 1_000; round++) {
            Random random = new Random();
            int len = Math.abs(random.nextInt(300)) + 2;
            int[] xs = new int[len];
            for (int idx = 0; idx < len; idx++) {
                xs[idx] = random.nextInt(3);
            }
            ArrayWrap a1 = new ArrayWrap(xs);
            Flagsort fs = new Flagsort(a1);
            fs.sort();
            assertTrue(a1.sorted());
            assertTrue(a1.getCtColor() <= a1.length());
            assertTrue(a1.getCtExchange() <= a1.length());
        }
    }
}