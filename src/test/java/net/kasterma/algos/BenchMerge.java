package net.kasterma.algos;

import lombok.Getter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Random;


public class BenchMerge {
    static int iterationsPerRun = 100;
    static int lengthBound = 10000;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Benchmark)
    public static class ToSort {
        @Getter
        MergeSortLong[] msi = new MergeSortLong[iterationsPerRun];

        {
            Random random = new Random();
            for (int i = 0; i < iterationsPerRun; i++) {
                int len = random.nextInt(lengthBound) + 1;
                long[] xs = new long[len];
                for (int idx = 0; idx < len; idx++) {
                    xs[idx] = random.nextLong();
                }
                msi[i] = new MergeSortLong(xs);
            }
        }
    }

    @Benchmark
    public void benchMSI(ToSort tos) {
        for (int i = 0; i < iterationsPerRun; i++) {
            tos.msi[i].sort();
        }
    }

    @State(Scope.Benchmark)
    public static class ToSortDouble {
        MergeSortDouble[] msd = new MergeSortDouble[iterationsPerRun];

        {
            Random random = new Random();
            for (int i = 0; i < iterationsPerRun; i++) {
                int len = random.nextInt(lengthBound) + 1;
                double[] xs = new double[len];
                for (int idx = 0; idx < len; idx++) {
                    xs[idx] = random.nextDouble();
                }
                msd[i] = new MergeSortDouble(xs);
            }
        }
    }

    @Benchmark
    public void benchMSD(ToSortDouble tos) {
        for (int i = 0; i < iterationsPerRun; i++) {
            tos.msd[i].sort();
        }
    }

    @State(Scope.Benchmark)
    public static class ToSortGenericDouble {
        MergeSort<Double>[] msd = (MergeSort<Double>[]) new Object[iterationsPerRun];

        {
            Random random = new Random();
            for (int i = 0; i < iterationsPerRun; i++) {
                int len = random.nextInt(lengthBound) + 1;
                Double[] xs = new Double[len];
                for (int idx = 0; idx < len; idx++) {
                    xs[idx] = random.nextDouble();
                }
                msd[i] = new MergeSort<>(xs);
            }
        }
    }

    @Benchmark
    public void benchMSGD(ToSortGenericDouble tos) {
        for (int i = 0; i < iterationsPerRun; i++) {
            tos.msd[i].sort();
        }
    }
}
