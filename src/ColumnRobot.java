/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

import java.util.ListIterator;

public class ColumnRobot extends Robot {
    /**
     * The constructor of a column robot
     * @param mailroom building's mailroom
     */
    ColumnRobot(MailRoom mailroom) {
        super(mailroom);
    }

    /**
     * Get item with the earliest arrival time that robot is carrying
     * @return the arrival time of the earliest item
     */
    public int getEarliestItem() {  // Find an item with the earliest arrival time
        int min = getDeliverables().getFirst().myArrival();
        for (int i = 1; i < getDeliverables().size(); i++) {
            if (min <= getDeliverables().get(i).myArrival()) {
                min = getDeliverables().get(i).myArrival();
            }
        }
        return min;
    }

    /**
     * Get the floor number this robot is heading to
     * @return target floor number
     */
    public int getTargetFloor() {
        return getDeliverables().isEmpty() ? 0 : getDeliverables().getFirst().myFloor();
    }

    /**
     * An override of ticking method from an abstract parent class
     * Delivers items to a target floor, once transferred comes back to the mailroom for new ones
     */
    @Override
    public void tick() {
        if (getDeliverables().isEmpty()) { // Return to MailRoom
            move(Building.Direction.DOWN);  //move towards mailroom
        } else { // Items to deliver
            if (getFloor() != getTargetFloor()) {
                move(Building.Direction.UP); // move towards floor
            }
        }
    }
}
