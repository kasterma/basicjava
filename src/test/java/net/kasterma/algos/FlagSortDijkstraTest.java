package net.kasterma.algos;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class FlagSortDijkstraTest {
    @Test
    void sort1() {
        int[] xs = {0, 0, 1, 1, 2, 2};
        FlagSortDijkstra fsd = new FlagSortDijkstra(xs);
        fsd.sort(true, false);
        assertTrue(fsd.sorted());
    }
    @Test
    void sort2() {
        int[] xs = {0, 1};
        FlagSortDijkstra fsd = new FlagSortDijkstra(xs);
        fsd.sort(true, true);
        assertTrue(fsd.sorted());
    }

    @Test
    void sort3() {
        int[] xs = {1, 0, 2};
        FlagSortDijkstra fsd = new FlagSortDijkstra(xs);
        fsd.sort(true, true);
        assertTrue(fsd.sorted());
    }

    @Test
    void testGenerator() {
        for (int round = 0; round < 10_000; round++) {
            Random random = new Random();
            int len = Math.abs(random.nextInt(300)) + 2;
            int[] xs = new int[len];
            for (int idx = 0; idx < len; idx++) {
                xs[idx] = random.nextInt(3);
            }
            FlagSortDijkstra fs = new FlagSortDijkstra(xs);
            //log.info(fs.xsString());
            fs.sort(true, false);
            assertTrue(fs.sorted());
        }
    }
}