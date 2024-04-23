package edu.iu.c212.programs;

import java.util.*;

public class SawPrimePlanks {

    /**
     * Returns a list of prime plank lengths obtained by sawing a long plank.
     *
     * @param longPlankLength The length of the long plank.
     * @return A list of prime plank lengths.
     */
    public static List<Integer> getPlankLengths(int longPlankLength){
        List<Integer> primePlankLengths = new ArrayList<>();
        int result = sawPlank(longPlankLength);
        primePlankLengths.add(result);
        return primePlankLengths;
    }

    /**
     * Recursively computes the prime factors of a plank length.
     *
     * @param plankLength The length of the plank.
     * @return The product of the prime factors.
     */
    public static int sawPlank(int plankLength){
            if(plankLength == 1){
                return 1;
            }
            int smallestPrimeFactor = findSmallestPrimeFactor(plankLength);

            int result = sawPlank(plankLength / smallestPrimeFactor) * smallestPrimeFactor;

            return result;
        }

    /**
     * Finds the smallest prime factor of a number.
     *
     * @param n The number to find the smallest prime factor of.
     * @return The smallest prime factor of the number.
     */
    private static int findSmallestPrimeFactor(int n){
        for(int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0){
                return i;
            }
        }
        return n;
    }
}
