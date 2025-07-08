/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

public class CycleRobot extends Robot {
    /**
     * The constructor of a robot operating in cycling mode
     * @param mailroom building's mailroom
     */
    CycleRobot(MailRoom mailroom) {
        super(mailroom);
    }

    /**
     * An override of ticking method from an abstract parent class
     * Circles between the mailroom and target floors, delivering mail items
     */
    @Override
    public void tick() {
        System.out.println("About to tick: " + this);
        Building building = Building.getBuilding();
        if (getDeliverables().isEmpty()) {
            // Return to MailRoom
            if (getRoom() == building.NUMROOMS + 1) { // in right end column
                move(Building.Direction.DOWN);  //move towards mailroom
            } else {
                move(Building.Direction.RIGHT); // move towards right end column
            }
        } else {
            // Items to deliver
            if (getFloor() == getDeliverables().getFirst().myFloor()) {
                // On the right floor
                if (getRoom() == getDeliverables().getFirst().myRoom()) { //then deliver all relevant items to that room
                    do {
                        Deliverable deliverable = getDeliverables().removeFirst();
                        load -= deliverable.myWeight ();
                        Simulation.deliver(deliverable);
                    } while (!getDeliverables().isEmpty() && getRoom() == getDeliverables().getFirst().myRoom());
                } else {
                    move(Building.Direction.RIGHT); // move towards next delivery
                }
            } else {
                move(Building.Direction.UP); // move towards floor
            }
        }
    }
}
