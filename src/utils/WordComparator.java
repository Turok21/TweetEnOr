package utils;

/**
 * Created by Ari� on 14/10/2015.
 */
public abstract class WordComparator implements Comparable {

    public static boolean wordCompare(String alpha, String beta) {
        return alpha.equalsIgnoreCase(beta);
    }
}