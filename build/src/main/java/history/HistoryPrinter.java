package history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Methods in this class assume that {@link GalleryState} methods will return elements in a sorted order.
 *
 * Created by jchien on 9/6/14.
 */
public final class HistoryPrinter {

    private final GalleryState state;

    public HistoryPrinter(GalleryState state) {
        this.state = state;
    }

    // -R arg
    public int printRoomsVisited(String name) {
        final List<Integer> rooms = state.getRoomsVisited(name);

        System.out.println(HistoryUtils.toListDelimitedString(rooms));
        return 0;
    }

    // -R arg html
    public int printRoomsVisitedHTML(String name) {
        final StringBuilder builder = new StringBuilder("<html><body><table><tr><th>Rooms</th></tr>");

        final List<Integer> rooms = state.getRoomsVisited(name);
        for (Integer room : rooms) {
            builder.append("<tr><td>");
            builder.append(Integer.toString(room));
            builder.append("</td></tr>");
        }
        builder.append("</table></body></html>");
        System.out.println(builder.toString());
        return 0;
    }

    // -S arg
    public int printState() {
        final Map<Integer, Set<String>> roomOccupancies = state.getRoomOccupancies();
        System.out.println(HistoryUtils.toListDelimitedString(state.getEmployeesInsideGallery()));
        System.out.println(HistoryUtils.toListDelimitedString(state.getGuestsInsideGallery()));
        for (Map.Entry<Integer, Set<String>> entry : roomOccupancies.entrySet()) {
            final Integer room = entry.getKey();
            final Set<String> inside = entry.getValue();
            System.out.println(room.toString() + ": " + HistoryUtils.toListDelimitedString(inside));
        }
        return 0;
    }

    // -R arg html
    public int printStateHTML() {
        final StringBuilder builder = new StringBuilder("<html><body><table><tr><th>Employee</th><th>Guest</th></tr>");
        final Map<Integer, Set<String>> roomOccupancies = state.getRoomOccupancies();
        final ArrayList<String> employees = new ArrayList(state.getEmployeesInsideGallery());
        final ArrayList<String> guests = new ArrayList(state.getGuestsInsideGallery());

        int i = 0;
        if (employees.size() < guests.size()) {
            for ( ; i < employees.size(); i++) {
                builder.append("<tr><td>");
                builder.append(employees.get(i));
                builder.append("</td><td>");
                builder.append(guests.get(i));
                builder.append("</td></tr>");
            }
            for ( ; i < guests.size(); i++) {
                builder.append("<tr><td>");
                // no more employees
                builder.append("</td><td>");
                builder.append(guests.get(i));
                builder.append("</td></tr>");
            }
        }
        else {
            for ( ; i < guests.size(); i++) {
                builder.append("<tr><td>");
                builder.append(employees.get(i));
                builder.append("</td><td>");
                builder.append(guests.get(i));
                builder.append("</td></tr>");
            }
            for ( ; i < employees.size(); i++) {
                builder.append("<tr><td>");
                builder.append(employees.get(i));
                builder.append("</td><td>");
                // no more guests
                builder.append("</td></tr>");
            }
        }

        builder.append("</table><table><tr><th>Room ID</th><th>Occupants</th></tr>");

        for (Map.Entry<Integer, Set<String>> entry : roomOccupancies.entrySet()) {
            final Integer room = entry.getKey();
            final Set<String> inside = entry.getValue();
            builder.append("<tr><td>");
            builder.append(room.toString());
            builder.append("</td><td>");
            builder.append(HistoryUtils.toListDelimitedString(inside));
            builder.append("</td></tr>");
        }
        builder.append("</table></body></html>");

        System.out.println(builder.toString());
        return 0;
    }

    // -T arg
    public int printTimeSpentInGallery(String name) {
        final Integer time = state.getTimeSpentInGallery(name);
        if (time != null) {
            System.out.println(time.toString());
        }
        return 0;
    }
}
