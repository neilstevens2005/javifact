package org.javifact.buildsrc;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Created by neil on 30/12/14.
 */
public class GeneralUtils {

    private final static Set<Character> VOWLES = ImmutableSet.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');

    public static String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    public static boolean isVowel(char c) {
        return VOWLES.contains(c);
    }

    public static <T> boolean allEqual(Collection<T> collection) {
        T firstElement = null;
        for (T element : collection) {
            if (firstElement == null) {
                firstElement = element;
            } else if (!firstElement.equals(element)){
                return false;
            }
        }

        return true;
    }

    /*public static void deleteRecursivelyIfExists(Path directory) {
        if (Files.exists(directory)) {
            if (Files.isDirectory(directory)) {

            }
        }
    }*/

}
