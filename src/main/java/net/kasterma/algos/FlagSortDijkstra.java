package net.kasterma.algos;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
final class FlagSortDijkstra {
    @Getter
    private final int[] xs;
    private final int[] xs_orig;

    public FlagSortDijkstra(int[] xs) {
        this.xs = xs.clone();
        this.xs_orig = xs;
    }

    void sort(boolean check, boolean trace) {
        int lt = 0;
        int idx = lt;
        int gt = xs.length - 1;

        int len = xs.length;

        while (true) {
            if (trace)
                log.info("lt {} idx {} gt {} xs {}",
                        lt, idx, gt, xsString());
            while (lt < len && xs[lt] == 0) lt++;
            while (gt > 0 && xs[gt] == 2) gt--;
            if (idx < lt) idx = lt;
            while (idx < len && xs[idx] == 1) idx++;

            assert !check || invariant(lt, idx, gt);
            if (trace)
                log.info("2: lt {} idx {} gt {} xs {}",
                        lt, idx, gt, xsString());
            if (idx < len && lt < idx && xs[idx] == 0) exchange(lt, idx);
            if (idx < gt && xs[idx] == 2) exchange(gt, idx);
            if (idx >= gt) break;
        }
    }

    private void exchange(int i, int j) {
        assert i != j;
        int temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }

    private boolean invariant(int lt, int idx, int gt) {
        if (lt <= idx && idx <= gt + 1) {
            if (IntStream.range(0, lt)
                    .allMatch(i -> xs[i] == 0)) {
                if (IntStream.range(lt, idx)
                        .allMatch(i -> xs[i] == 1)) {
                    if (IntStream.range(gt + 1, xs.length)
                            .allMatch(i -> xs[i] == 2)) {
                        return true;
                    } else {
                        log.error("non-two between gt {} and end: {}",
                                gt, xsString());
                    }
                } else {
                    log.error("non-one between lt {} and idx {}: {}",
                            lt, idx, xsString());
                }
            } else {
                log.error("non-zero before lt {}: {}",
                        lt, xsString());
            }
        } else {
            log.error("order incorrect {} {} {}", lt, idx, gt);
        }
        return  false;
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
            log.error("not in order {}", xsString());
            return false;
        }

        return true;
    }

    String xsString() {
        return Arrays.stream(xs)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
    }
}
