package net.kasterma.flagsort;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class for Flagsort.
 */
@Log4j2
final class Flagsort {
    private final ArrayWrap xs;
    private int zero_insertion;
    private int left;
    private int left_color;
    private int right_color;
    private int right;
    private int two_insertion;

    /**
     * Make constructor inaccessible.
     */
    Flagsort(ArrayWrap xs) {
        this.xs = xs;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(xs.toString());
        sb.append("; (")
                .append(zero_insertion).append(", ")
                .append(left)
                .append("{").append(left_color).append("}, ")
                .append(right)
                .append("{").append(right_color).append("}, ")
                .append(two_insertion)
                .append(")");
        return sb.toString();
    }

    void sort() {
        zero_insertion = 0;
        two_insertion = xs.length() - 1;

        left_color = xs.getColor(zero_insertion);
        right_color = xs.getColor(two_insertion);

        left = zero_insertion;
        right = two_insertion;

        assert xs.invariant(zero_insertion, left, left_color, right_color, right, two_insertion);

        boolean update_left_color = false;
        boolean update_right_color = false;

        while (left < right) {
            // in the loop only update when still needed, otherwise we do one
            // superfluous read when left and right have become equal.  So in
            // place of reading, we set the intention to update below.
            if (update_left_color) {
                left_color = xs.getColor(left);
                update_left_color = false;
            }

            if (update_right_color) {
                right_color = xs.getColor(right);
                update_right_color = false;
            }
            log.trace(toString());
            assert xs.invariant(zero_insertion, left, left_color, right_color, right, two_insertion);

            if (left_color == 0) {
                log.trace("left_color == 0");
                if (left > zero_insertion) {
                    xs.exchange(zero_insertion, left);
                    zero_insertion++;
                    left_color = 1; // since only way to get left > zero_insertion
                } else {
                    zero_insertion++;
                    left = zero_insertion;
                    update_left_color = true;
                }
            } else if (left_color == 1) {
                log.trace("left_color == 1");
                left++;
                assert left > zero_insertion;
                update_left_color = true;
                if (left == right) {
                    if (right_color == 0) {
                        xs.exchange(zero_insertion, right);
                    }
                }
            } else if (right_color == 2) {
                log.trace("right_color == 2");
                if (right < two_insertion) {
                    xs.exchange(two_insertion, right);
                    two_insertion--;
                    right_color = 1; // since only way to get right < two_insertion
                } else {
                    two_insertion--;
                    right = two_insertion;
                    update_right_color = true;
                }
            } else if (right_color == 1) {
                log.trace("right_color == 1");
                right--;
                assert right < two_insertion;
                update_right_color = true;
                if (left == right) {
                    if (left_color == 2)
                        xs.exchange(left, two_insertion);
                }
            } else if (left_color == 2) {
                log.trace("left_color == 2");
                xs.exchange(left, two_insertion);
                if (right == two_insertion) {
                    two_insertion--;
                    right = two_insertion;
                    left_color = right_color;
                    update_right_color = true;
                } else {
                    two_insertion--;
                    left_color = 1;
                }
            } else if (right_color == 0) {
                log.trace("right_color == 0");
                xs.exchange(zero_insertion, right);
                if (left == zero_insertion) {
                    zero_insertion++;
                    left = zero_insertion;
                    right_color = left_color;
                    update_left_color = true;
                } else {
                    zero_insertion++;
                    right_color = 1;
                }
            }
        }
        if (zero_insertion < left) {
            if (left_color == 0)
                xs.exchange(zero_insertion, left);
        }
    }
}

/**
 * Wrapper class for an array of ints.
 *
 * <p>Through this wrapper we ensure all accesses and exchanges can be counted.
 */
@Log4j2
class ArrayWrap {
    /**
     * Wrapped array.
     */
    private final int[] xs;

    /**
     * Count of number of color requests.
     */
    @Getter
    int ctColor = 0;

    /**
     * Count of number of exchanges.
     */
    @Getter
    int ctExchange = 0;

    /**
     * Constructor to wrap array.
     *
     * @param xs array to wrap
     */
    ArrayWrap(int[] xs) {
        if (xs.length < 2) {
            throw new IllegalArgumentException("Length at least two needed.");
        }
        for (int x : xs) {
            if (x < 0 || x > 2) {
                throw new IllegalArgumentException("Inadmissible value in array");
            }
        }
        this.xs = xs;
    }

    /**
     * Get the color at given location.
     *
     * @param i location to query
     * @return color found
     */
    int getColor(int i) {
        ctColor++;
        return xs[i];
    }

    /**
     * Exchange the array values
     *
     * @param i first index of exchange
     * @param j second index of exchange
     */
    void exchange(int i, int j) {
        log.trace("exchange({}, {})", i, j);
        ctExchange++;
        int x = xs[i];
        xs[i] = xs[j];
        xs[j] = x;
    }

    /**
     * Retrieve the length of the contained array.
     */
    int length() {
        return xs.length;
    }

    /**
     * Check if the data is sorted.
     */
    boolean sorted() {
        for (int idx = 0; idx < xs.length - 1; idx++) {
            if (xs[idx] > xs[idx + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check the invariant for FlagSort.
     * <p>
     * <p>This method needs to be here b/c we need read access to the data of
     * the ArrayWrap.
     */
    boolean invariant(int zero_insertion,
                      int left, int left_color,
                      int right_color, int right,
                      int two_insertion) {
        log.trace("test invariant");
        if (xs[left] != left_color) {
            log.error(toString());
            log.error("xs[left] != left_color, left_color = {} ({}, {}, {}, {})",
                    left_color,
                    zero_insertion, left, right, two_insertion);
            return false;
        }
        if (xs[right] != right_color) {
            log.error(toString());
            log.error("xs[right] != right_color, right_color = {} ({}, {}, {}, {})",
                    right_color,
                    zero_insertion, left, right, two_insertion);
            return false;
        }
        if (left < zero_insertion) {
            log.error(toString());
            log.error("left < zero_insertion ({}, {}, {}, {})",
                    zero_insertion, left, right, two_insertion);
            return false;
        }
        if (right > two_insertion) {
            log.error(toString());
            log.error("right > two_insertion ({}, {}, {}, {})",
                    zero_insertion, left, right, two_insertion);
            return false;
        }
        for (int idx = two_insertion + 1; idx < xs.length; idx++) {
            if (xs[idx] != 2) {
                log.trace(toString());
                log.error("xs[idx] != 2, idx = {} ({}, {}, {}, {})",
                        idx,
                        zero_insertion, left, right, two_insertion);
                return false;
            }
        }
        for (int idx = right + 1; idx <= two_insertion; idx++) {
            if (xs[idx] != 1) {
                log.error(toString());
                log.error("xs[idx] != 1, idx = {} ({}, {}, {}, {})",
                        idx,
                        zero_insertion, left, right, two_insertion);
                return false;
            }
        }
        for (int idx = 0; idx < zero_insertion; idx++) {
            if (xs[idx] != 0) {
                log.error(toString());
                log.error("xs[idx] != 0, idx = {} ({}, {}, {}, {})",
                        idx,
                        zero_insertion, left, right, two_insertion);
                return false;
            }
        }
        for (int idx = zero_insertion; idx < left; idx++) {
            if (xs[idx] != 1) {
                log.error(toString());
                log.error("xs[idx] != 1, idx = {} ({}, {}, {}, {})",
                        idx,
                        zero_insertion, left, right, two_insertion);
                return false;
            }
        }
        return true;
    }

    /**
     * String representation of the ArrayWrap.
     * <p>
     * <p>This representation is ready for copy pasting into a test as an
     * initializer for an int[] to generate a test.
     *
     * @return string representation of ArrayWrap
     */
    public String toString() {
        return "{"
                + Arrays.stream(xs)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "))
                + "}";
    }
}