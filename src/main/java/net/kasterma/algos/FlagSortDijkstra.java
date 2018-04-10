package net.kasterma.algos;

import lombok.extern.log4j.Log4j2;

import java.util.stream.IntStream;

@Log4j2
final class FlagSortDijkstra extends SortBase {

    FlagSortDijkstra(final int[] xs) {
        super(xs);
    }

    void sort(boolean check, boolean trace) {
        int lt = 0;
        int idx = lt;
        int gt = xs.length - 1;

        int len = xs.length;

        while (true) {
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

    /**
     * Check the invariant for the dijkstra flag sort.
     */
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
}
