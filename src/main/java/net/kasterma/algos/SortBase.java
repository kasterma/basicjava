package net.kasterma.algos;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Base class for experimenting with sorting algorithms.
 */
@Log4j2
abstract class SortBase {
    @Getter
    protected final int[] xs;
    private final int[] xs_orig;

    SortBase(final int[] xs) {
        this.xs = xs.clone();
        this.xs_orig = xs;
    }

    final void sort() {
        sort(false, false);
    }

    abstract void sort(boolean check, boolean trace);

    void exchange(int i, int j) {
        int temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }

    /**
     * Check if sort sorted the original array.
     */
    final boolean sorted() {
        // same number of elements
        if (xs.length != xs_orig.length) {
            log.error("Lost of gained elements");
            return false;
        }

        // same elements
        boolean[] xs_used = new boolean[xs_orig.length];
        for (long x : xs_orig) {
            int idx;
            for (idx = 0; idx < xs.length; idx++) {
                if (!xs_used[idx] && xs[idx] == x) {
                    xs_used[idx] = true;
                    break;
                }
            }
            if (idx == xs.length) {
                log.error("element {} not found in sorted", x);
                return false;
            }
        }

        // order correct
        if (!IntStream.range(0, xs.length - 1)
                .allMatch(idx -> xs[idx] <= xs[idx + 1])) {
            log.error("not in order {}", xsString());
            return false;
        }

        return true;
    }

    /**
     * Give a string representation of the array that is being sorted.
     */
    final String xsString() {
        return Arrays.stream(xs)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
    }
}
