package history;

import com.google.common.collect.Iterables;

import java.util.*;

/**
 * Created by jchien on 9/6/14.
 */
public final class GalleryState {

    private final int time;

    private final Set<String> employeesInside;
    private final Set<String> guestsInside;
    private final Map<String, PersonHistory> histories;

    private GalleryState(int time, TreeSet<String> employees, TreeSet<String> guests,
                         Map<String, PersonHistory> histories) {
        this.time = time;
        this.employeesInside = employees;
        this.guestsInside = guests;
        this.histories = histories;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(Integer.toString(time));

        builder.append(HistoryUtils.DELIMITER);
        builder.append(HistoryUtils.toListDelimitedString(employeesInside));
        builder.append(HistoryUtils.DELIMITER);
        builder.append(HistoryUtils.toListDelimitedString(guestsInside));
        builder.append(HistoryUtils.DELIMITER);
        builder.append(PersonHistory.toString(histories));

        return builder.toString();
    }

    // TODO(jchien): Should this throw a specific checked exception when parsing fails?
    public static GalleryState fromString(String decryptedContents) {
        try {
            final String[] parts = decryptedContents.split(HistoryUtils.DELIMITER);
            assert parts.length % 3 == 0 && parts.length >= 3;
            return new GalleryState(Integer.valueOf(parts[0]), HistoryUtils.toSortedStringSet(parts[1]),
                    HistoryUtils.toSortedStringSet(parts[2]),
                    PersonHistory.fromStringParts(Arrays.copyOfRange(parts, 3, parts.length)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * No defensive copying for performance reasons. DO NOT MUTATE the returned List. Use
     * methods {@link leaveRoom()} or {@link enterRoom()} instead.
     */
    public List<Integer> getRoomsVisited(String name) {
        final PersonHistory history = histories.get(name);
        if (history == null) {
            return Collections.emptyList();
        } else {
            return history.getRoomsVisited();
        }
    }

    public Integer getTimeSpentInGallery(String name) {
        final PersonHistory history = histories.get(name);
        if (history == null) {
            return null;
        } else {
            return history.getTimeSpentInGallery();
        }
    }

    public Set<String> getEmployeesInsideGallery() {
        return employeesInside;
    }

    public Set<String> getGuestsInsideGallery() {
        return guestsInside;
    }

    public Map<Integer, Set<String>> getRoomOccupancies() {
        final Map<Integer, Set<String>> roomOccupancies = new TreeMap<Integer, Set<String>>();
        for (PersonHistory history : histories.values()) {
            if (history.isInsideRoom()) {
                Integer room = Iterables.getLast(history.getRoomsVisited());
                if (roomOccupancies.containsKey(room)) {
                    roomOccupancies.get(room).add(history.getName());
                } else {
                    final Set<String> people = new TreeSet<String>();
                    people.add(history.getName());
                    roomOccupancies.put(room, people);
                }
            }
        }
        return roomOccupancies;
    }

    // TODO: Add methods for logappend to call (e.g. leaveRoom(person, room), enterRoom(person, room))
}
