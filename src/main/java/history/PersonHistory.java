package history;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jchien on 9/6/14.
 */
final class PersonHistory {
    private final String name;
    private final List<Integer> roomsVisited;
    private boolean insideRoom;
    private int timeSpentInGallery;

    private PersonHistory(String name, List<Integer> roomsVisited, boolean insideRoom, int timeSpentInGallery) {
        this.name = name;
        this.roomsVisited = roomsVisited;
        this.insideRoom = insideRoom;
        this.timeSpentInGallery = timeSpentInGallery;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(name);
        builder.append(HistoryUtils.DELIMITER);

        for (int i = 0; i < roomsVisited.size(); i++) {
            builder.append(roomsVisited.get(i));
            if (i == roomsVisited.size() - 1) {
                if (insideRoom) {
                    builder.append("*");
                }
                builder.append(HistoryUtils.DELIMITER);
            } else {
                builder.append(HistoryUtils.LIST_DELIMITER);
            }
        }

        builder.append(timeSpentInGallery);
        return builder.toString();
    }

    public static Map<String, PersonHistory> fromStringParts(String[] decryptedParts) {
        assert decryptedParts.length % 3 == 0;

        final int numPersons = decryptedParts.length / 3;
        final Map<String, PersonHistory> histories = new HashMap<String, PersonHistory>(numPersons);
        for (int i = 0; i < numPersons; i++) {
            final boolean inside = decryptedParts[i * 3 + 1].endsWith("*");
            final String dirtyRooms = decryptedParts[i * 3 + 1];
            final String cleanRooms = inside ? dirtyRooms.substring(0, dirtyRooms.length() - 1) : dirtyRooms;
            histories.put(decryptedParts[i * 3],
                    new PersonHistory(decryptedParts[i * 3], HistoryUtils.toNumList(cleanRooms),
                            inside, Integer.valueOf(decryptedParts[i * 3 + 2])));
        }
        return histories;
    }

    public static String toString(Map<String, PersonHistory> histories) {
        return HistoryUtils.toDelimitedString(histories.values());
    }

    public String getName() {
        return name;
    }

    public List<Integer> getRoomsVisited() {
        return roomsVisited;
    }

    public boolean isInsideRoom() {
        return insideRoom;
    }

    public int getTimeSpentInGallery() {
        return timeSpentInGallery;
    }

    public Integer getMostRecentRoom() {
        return roomsVisited.get(roomsVisited.size() - 1);
    }

    public void incrementTimeSpentInGallery(int additionalTime) {
        timeSpentInGallery += additionalTime;
    }

    public void enterRoom(Integer room) throws IllegalArgumentException {
        Preconditions.checkArgument(!insideRoom);

        roomsVisited.add(room);
        insideRoom = true;
    }

    public void leaveRoom(Integer room) throws IllegalArgumentException {
        Preconditions.checkArgument(room.equals(getMostRecentRoom()));
        Preconditions.checkArgument(insideRoom);

        insideRoom = false;
    }
}
