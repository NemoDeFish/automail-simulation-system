/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

// Parcel - deliverable with a set weight
public class Parcel extends Deliverable {
    private final int weight;

    /**
     * The constructor, initializes a parcel with given data
     * @param floor floor where this parcel needs to be delivered to
     * @param room  room where this parcel needs to be delivered to
     * @param arrival arrival time of this parcel to the mailroom
     * @param weight the weight of this parcel
     */
    Parcel(int floor, int room, int arrival, int weight) {
        super (floor, room, arrival);
        this.weight = weight;
    }

    /**
     * Get the weight of this parcel
     * @return weight of this parcel
     */
    @Override
    public int myWeight () { return weight; }
}
