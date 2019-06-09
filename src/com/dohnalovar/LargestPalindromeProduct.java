package com.dohnalovar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

/**
 * Created by dohnalovar on 6/8/2019
 */
class LargestPalindromeProduct {

    /** maxNum is 9 for 1-Digit, 99 for 2-Digit, 999 for 3-Digit */
    private final int maxNum;
    /** minNum is 1 for 1-Digit, 10 for 2-Digit, 100 for 3-Digit */
    private final int minNum;

    /**
     * last is the last palindrome already used, initialize to the lowest
     * palindrome > maxNum*maxNum in constructor
     */
    private List<Integer> last;
    /** results */
    private int num1 = 0;
    private int num2 = 0;
    /** how many digits palidrome is required */
    private int digit;

    public LargestPalindromeProduct(int digit) {
        this.digit = digit;

        // initialize maxNum
        int tmp = 0;
        for (int i = 0; i < digit; i++) {
            tmp += 9 * Math.pow(10,i);
        }
        this.maxNum = tmp;

        // initialize minNum
        this.minNum = (int) Math.pow(10, digit-1);

        // initialize last
        int x = maxNum * maxNum;
        this.last = new ArrayList<>();
        while (x > 0) {
            int cipher = x % 10;
            x = x / 10;
            last.add(cipher);
        }
        for (int centerIndex = last.size() / 2; centerIndex < last.size(); centerIndex++) {
            last.set(mirror.applyAsInt(centerIndex, last.size()), last.get(centerIndex));
        }

        this.evaluate();
    }


    /** product({1, 2, 3}) = 1*10^0 + 2*10^1 + 3*10^2 = 321 */
    final static ToIntFunction<List<Integer>> product = (myArray) -> {
        int result = 0;
        for (int i = 0; i < myArray.size(); i++) {
            result += myArray.get(i) * Math.pow(10, i);
        }
        return result;
    };


    final public int getLast() {
        return product.applyAsInt(last);
    }

    final public int getNum1() {
        return num1;
    }

    final public int getNum2() {
        return num2;
    }

    /** mirror index of i in array of s elements */
    final static ToIntBiFunction<Integer, Integer> mirror = (i, s) -> s - 1 - i;

    /** decrement the digit on the index and it's mirror index by 1.
        Incase of underflowing, low also neighbr indexes
    */
    final private void decrementMirrorIndexesOfLast(int index, int size) {
        // indexIsCenter - odd number of digits in the last palindrome
        boolean indexIsCenter = (((size % 2) == 1) && ((size/2) == index));
        int digitValue = last.get(index);

        int newDigitValue = (digitValue+9) % 10;
        // decrement digit with index
        last.set(index, newDigitValue);
        if (!indexIsCenter) {
            // decrement also digit with mirror index
            last.set(
                LargestPalindromeProduct.mirror.applyAsInt(index, size),
                newDigitValue
            );
        }

        // decrement newghbrs
        if ((digitValue == 0) && (index < (size-1))) {
            decrementMirrorIndexesOfLast(index+1, size);
        } else if ((digitValue == 1) && (index == (size-1))) { //no zeros on borders
            // drop the last element from list and set all element values to 9
            last.remove(last.size()-1);
            for (int i = 0; i < last.size(); i++) {
                last.set(i, 9);
            }
        }

    }

    /** returns next largest palindrome lower then last, higher or equal to min */
    final private int getNextPalindrome() {
        // array for 99999 = 9*10^0+9*10^1+9*10^2+9*10^3+9*10^4 is {9, 9, 9, 9, 9}

        // find centerIndex - to modify palindrome
        // (e. g. 998899 -> 997799, 20002 -> 19991, 10001 -> 9999)
        int centerIndex = last.size() / 2;
        decrementMirrorIndexesOfLast(centerIndex, last.size());

        return getLast();
    };

    /** returns largest palindrome last as product of two n-digit numbers num1 and num2 */
    final private void evaluate() {

        int lastPalindrome = getLast();

        while (lastPalindrome > minNum * minNum) {
            int palindrome = getNextPalindrome();

            for (int i = maxNum; i >= minNum; i--) {
                if ((palindrome % i) == 0) {
                    int j = palindrome / i;
                    if ((j >= minNum) && (j <= maxNum)) {
                        num1 = i;
                        num2 = j;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "LargestPalindromeProduct of two " +
            digit + "-digit numbers is " +
            + this.getLast() +
            " = " + this.getNum1() +
            " * " + this.getNum2();
    }
}
