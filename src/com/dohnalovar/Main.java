/**
A palindromic number reads the same both ways. The largest palindrome made from the product of
 two 2-digit numbers is 9009 = 91 × 99.

Find the largest palindrome made from the product of two 3-digit numbers.
 */
package com.dohnalovar;

public class Main {

    public static void main(String[] args) {

        LargestPalindromeProduct p1 = new LargestPalindromeProduct(1);
        System.out.println(p1.toString());

        LargestPalindromeProduct p2 = new LargestPalindromeProduct(2);
        System.out.println(p2.toString());

        LargestPalindromeProduct p3 = new LargestPalindromeProduct(3);
        System.out.println(p3.toString());

        LargestPalindromeProduct p4 = new LargestPalindromeProduct(4);
        System.out.println(p4.toString());

    }

}
