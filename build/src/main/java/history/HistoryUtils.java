package history;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * Created by jchien on 9/6/14.
 */
public final class HistoryUtils {
    public static final String DELIMITER = "],";
    public static final String LIST_DELIMITER = ",";

    private HistoryUtils() {
        // static util classes should not be initialized
    }

    public static TreeSet<String> toSortedStringSet(String list) {
        final TreeSet<String> elements = new TreeSet<String>();
        Collections.addAll(elements, list.split(LIST_DELIMITER));
        return elements;
    }

    public static List<Integer> toNumList(String list) throws NumberFormatException {
        final String[] elements = list.split(LIST_DELIMITER);
        final List<Integer> result = new ArrayList<Integer>(elements.length);
        for (String num : elements) {
            result.add(Integer.valueOf(num));
        }
        return result;
    }

    public static String toListDelimitedString(Iterable<?> iterable) {
        final Joiner joiner = Joiner.on(LIST_DELIMITER);
        return joiner.join(iterable);
    }

    public static String toDelimitedString(Iterable<?> iterable) {
        final Joiner joiner = Joiner.on(DELIMITER);
        return joiner.join(iterable);
    }
}
