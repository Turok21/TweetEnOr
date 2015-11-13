package utils;

/**
 * Created by Arié on 14/10/2015.
 */
public abstract class WordComparator {

    /**
     * @param alpha {String} Word to compare from the other
     * @param beta  {String} Word to compare from the other
     * @return {boolean} Tell you if the two word are close enouth or not
     */
    public static boolean wordCompare(String alpha, String beta) {
        if (similarity(alpha, beta) >= 0.74) {
            return true;
        } else {
            return false;
        }
    }

    /** // polymorphysme sur le pourcentage de "proximité"
     * @param alpha      {String} Word to compare from the other
     * @param beta       {String} Word to compare from the other
     * @param percentage {double} tell what percentage of resemblance you want
     * @return {boolean} Tell you if the two word are close enouth or not
     */
    public static boolean wordCompare(String alpha, String beta, double percentage) {
        if (similarity(alpha, beta) >= percentage) {
            return true;
        } else {
            return false;
        }
    }

    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // tri des deux mots (plus grand et plus petit)
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return (longerLength - levenshteinDistance(longer, shorter)) / (double) longerLength;

    }

    /**
     * Utilisation de l'algorythme de la distance de levenshtein pour comparer deux mots
     * @param s1 {String} Mot a comparer
     * @param s2 {String} Mot a comparer
     * @return {int} "distance de difference" entre les 2 mots
     */
    private static int levenshteinDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}