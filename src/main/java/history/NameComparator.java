package history;

import java.util.Comparator;

/**
 * Compares two names in the order AaBb...Zz, assumes no other letters are present
 */
public class NameComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        if (b.length() < a.length()) {
            return compare(b, a);
        }

        for (int i = 0; i < a.length(); i++) {
            char ac = a.charAt(i);
            char bc = b.charAt(i);
            char alower = lower(ac);
            char blower = lower(bc);
            if (alower == blower) {
                if (ac != bc) {
                    return ac - bc;
                }
            } else {
                return alower - blower;
            }
        }

        return 0;
    }

    private static char lower(char c) {
        if (c < 'a') {
            return (char) (c + ('a' - 'A'));
        }
        return c;
    }
}
