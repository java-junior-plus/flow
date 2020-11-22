package edu.junior;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Author: eukovko
 * Date: 11/23/2020
 */
class DivisibilityExclusionTest {

    DivisibilityExclusion exclusion;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @BeforeEach
    void setUp() {
        exclusion = new DivisibilityExclusion();
    }

    @ParameterizedTest
    @MethodSource("intArraySource")
    void testRemoveEven(int[] numbers) {
        exclusion.setNumbers(numbers);
        int[] actual = exclusion.removeEven();
        int[] expected = removeDivisible(numbers, 2, false);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("intArraySource")
    void testRemoveOdd(int[] numbers) {
        exclusion.setNumbers(numbers);
        int[] actual = exclusion.removeOdd();
        int[] expected = removeDivisible(numbers, 2, true);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("intArraySource")
    void testRemoveEvenOrOdd(int[] numbers) {
        boolean odd = random.nextBoolean();
        exclusion.setNumbers(numbers);
        int[] actual = exclusion.removeEvenOrOdd(odd);
        int[] expected = removeDivisible(numbers, 2, odd);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("intArraySource")
    void testRemoveDivisibleByThree(int[] numbers) {
        exclusion.setNumbers(numbers);
        int[] actual = exclusion.removeDivisibleByThree();
        int[] expected = removeDivisible(numbers, 3, false);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("intArraySource")
    void testRemoveDivisible(int[] numbers) {
        int divisor = random.nextInt(1, 20);
        exclusion.setNumbers(numbers);
        int[] actual = exclusion.removeDivisible(divisor);
        int[] expected = removeDivisible(numbers, divisor, false);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> intArraySource() {

        int sample = random.nextInt(20, 50);
        ArrayList<Arguments> arguments = new ArrayList<>();

        for (int j = 0; j < sample; j++) {
            int length = random.nextInt(1, 1000);
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = random.nextInt();
            }
            arguments.add(Arguments.of(array));
        }

        return arguments.stream();
    }

    private static int[] removeDivisible(int[] numbers, int divisor, boolean negate) {

        int[] temporaryResult = new int[numbers.length];
        int length = 0;

        for (int number : numbers) {
            int reminder = number % divisor;
            if (reminder == 0 != negate) {
                continue;
            }
            temporaryResult[length] = number;
            length++;
        }

        int[] result = new int[length];
        System.arraycopy(temporaryResult, 0, result, 0, length);
        return result;
    }
}