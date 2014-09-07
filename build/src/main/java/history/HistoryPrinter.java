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

    // -R arg html
    public int printRoomsVisitedHTML(String name) {
        final StringBuilder builder = new StringBuilder("<html><body><table><tr><th><Rooms</th></tr>");
        final Joiner joiner = Joiner.on("</td></tr><tr><td>");

        final List<Integer> rooms = state.getRoomsVisited(name);
        if (rooms.size() > 0){
            builder.append("<tr><td>");
            builder.append(joiner.join(rooms));
            builder.append("</td></tr>");
        }
        builder.append("</table></body></html>");
        System.out.print(builder.toString());
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
