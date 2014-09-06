package history;

import com.google.common.base.Joiner;

import java.util.List;

/**
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
        final Joiner joiner = Joiner.on(",");

        // TODO(jchien): Should this have a trailing newline?
        System.out.print(joiner.join(rooms));
        return 0;
    }

    // -S arg
    public int printState() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -T arg
    public int printTimeSpentInGallery(String name) {
        final Integer time = state.getTimeSpentInGallery(name);
        if (time != null) {
            // TODO(jchien): Should this have a trailing newline?
            System.out.print(time.toString());
        }
        return 0;
    }
}
