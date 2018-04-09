package net.kasterma.algos;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.stream.IntStream;

@Log4j2
public class MergeSortLong {
    @Getter
    private final long[] xs;
    private final long[] xs_orig;

    MergeSortLong(long[] xs) {
        this.xs = xs.clone();
        this.xs_orig = xs;
    }

    /**
     * Sort the contained array.
     */
    void sort() {
        sort_h(0, xs.length);
    }

    private void sort_h(final int lowerBoundInclusive,
                        final int upperBoundExclusive) {
        if (upperBoundExclusive - lowerBoundInclusive == 1) {
            return;
        }

        int mid = lowerBoundInclusive
                + (upperBoundExclusive - lowerBoundInclusive) / 2;
        sort_h(lowerBoundInclusive, mid);
        sort_h(mid, upperBoundExclusive);
        merge(lowerBoundInclusive, mid, upperBoundExclusive);
    }

    private void merge(int lowerBoundInclusive,
                       int mid,
                       int upperBoundExclusive) {
        int length_temp = upperBoundExclusive - lowerBoundInclusive;
        long[] temp_xs = new long[length_temp];

        int lower_idx = lowerBoundInclusive;
        int mid_idx = mid;
        int temp_idx = 0;
        while (true) {
            if (lower_idx < mid
                    && mid_idx < upperBoundExclusive) {
                if (xs[lower_idx] < xs[mid_idx]) {
                    temp_xs[temp_idx++] = xs[lower_idx++];
                } else {
                    temp_xs[temp_idx++] = xs[mid_idx++];
                }
            } else if (lower_idx < mid) {
                temp_xs[temp_idx++] = xs[lower_idx++];
            } else if (mid_idx < upperBoundExclusive) {
                temp_xs[temp_idx++] = xs[mid_idx++];
            } else {
                break;
            }
        }

        System.arraycopy(temp_xs, 0,
                xs, lowerBoundInclusive, length_temp);
    }

    /**
     * Check if sort sorted the original array.
     */
    boolean sorted() {
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
            log.error("not in order");
            return false;
        }

        return true;
    }

}
