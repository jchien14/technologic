package history;

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

    // -T arg
    public int printTimeSpentInGallery(String name) {
        final Integer time = state.getTimeSpentInGallery(name);
        if (time != null) {
            System.out.println(time.toString());
        }
        return 0;
    }
}
