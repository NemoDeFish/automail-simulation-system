/**
 * Skeleton written by SMD Teaching Team.
 * Modified by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

// Letter - deliverable with no weight
public class Letter extends Deliverable {
    /**
     * The constructor, initializes a letter with a given data
     * @param floor floor where this letter needs to be delivered to
     * @param room  floor where this letter needs to be delivered to
     * @param arrival arrival time of this letter to the mailroom
     */
    Letter(int floor, int room, int arrival) {
        super (floor, room, arrival);
    }

    /**
     * Get weight of this letter
     * @return weight of this letter (always zero)
     */
    @Override
    int myWeight () { return 0; }
}
