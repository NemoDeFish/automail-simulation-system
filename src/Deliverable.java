/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

import java.util.Comparator;

// Deliverable - an abstraction for the "mail item", such as letter or parcel
public abstract class Deliverable {
    private final int floor;
    private final int room;
    private final int arrival;

    /**
     * The constructor, initializes a mail item with a given data
     * @param floor floor where this mail item needs to be delivered to
     * @param room floor where this mail item needs to be delivered to
     * @param arrival arrival time of this mail item to the mailroom
     */
    Deliverable(int floor, int room, int arrival) {
        this.floor = floor;
        this.room = room;
        this.arrival = arrival;
    }

    /**
     * Convert this mail item to its string representation
     * @return a string representation of this mail item
     */
    public String toString() {
        return "Floor: " + floor + ", Room: " + room + ", Arrival: " + arrival + ", Weight: " + myWeight();
    }

    /**
     * Get this mail item target floor
     * @return target floor of this mail item
     */
    int myFloor() { return floor; }

    /**
     * Get this mail item target room
     * @return target room of this mail item
     */
    int myRoom() { return room; }

    /**
     * Get this mail item arrival time to the mailtoom
     * @return arrival time
     */
    int myArrival() { return arrival; }

    /**
     * Get this mail item weight
     * @return weight of this mail item
     */
    abstract int myWeight();
}

// Left to right room order, earliest to latest deliverables order
// Sorting class used by cycling robots and flooring robots for the left column robot
class SortForLeftCR implements Comparator<Deliverable> {
    @Override public int compare(Deliverable a, Deliverable b) {
        int floorDiff = a.myFloor() - b.myFloor();
        int roomDiff = a.myRoom() - b.myRoom();
        return floorDiff == 0 ? (roomDiff == 0 ? a.myArrival() - b.myArrival() : roomDiff) : floorDiff;
    }
}

// Right to left room order, earliest to latest deliverables order
// Sorting class used by flooring robots for the right column robot
class SortForRightCR implements Comparator<Deliverable> {
    @Override public int compare(Deliverable a, Deliverable b) {
        int floorDiff = a.myFloor() - b.myFloor();
        int roomDiff = a.myRoom() - b.myRoom();
        return floorDiff == 0 ? (roomDiff == 0 ? a.myArrival() - b.myArrival() : -roomDiff) : floorDiff;
    }
}