package net.kasterma.algos;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class MergeSortTest {
    @Test
    void sort1() {
        long[] xs = {4, 3, 2, 1};
        MergeSortLong msi = new MergeSortLong(xs);
        msi.sort();
        assertTrue(msi.sorted());
    }

    @Test
    void sort2() {
        long[] xs = {1, 2, 3, 4};
        MergeSortLong msi = new MergeSortLong(xs);
        msi.sort();
        assertTrue(msi.sorted());
    }

    @Test
    void sortGen() {
        Random random = new Random();
        for (int iter = 0; iter < 100; iter++) {
            log.info("iteration {}", iter);
            int len = random.nextInt(100) + 1;
            long[] xs = new long[len];
            for (int idx = 0; idx < len; idx++) {
                xs[idx] = random.nextInt(200);
            }
            MergeSortLong msi = new MergeSortLong(xs);
            msi.sort();
            assertTrue(msi.sorted());
        }
    }

    @Test
    void sortGenDouble() {
        Random random = new Random();
        for (int iter = 0; iter < 100; iter++) {
            log.info("iteration {}", iter);
            int len = random.nextInt(100) + 1;
            double[] xs = new double[len];
            for (int idx = 0; idx < len; idx++) {
                xs[idx] = random.nextDouble();
            }
            MergeSortDouble msi = new MergeSortDouble(xs);
            msi.sort();
            assertTrue(msi.sorted());
        }
    }

    @Test
    void sortGenGeneric() {
        Random random = new Random();
        for (int iter = 0; iter < 100; iter++) {
            log.info("iteration {}", iter);
            int len = random.nextInt(100) + 1;
            Double[] xs = new Double[len];
            for (int idx = 0; idx < len; idx++) {
                xs[idx] = random.nextDouble();
            }
            MergeSort<Double> msi = new MergeSort<>(xs);
            msi.sort();
            assertTrue(msi.sorted());
        }
    }
}