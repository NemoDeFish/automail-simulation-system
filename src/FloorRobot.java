/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

import java.util.ListIterator;

public class FloorRobot extends Robot {
    private final MailRoomFlooring mailRoom;
    private boolean shallMoveRight;
    private Building.Direction heading;

    /**
     * The constructor of a floor robot
     * @param mailroom building's mailroom
     */
    FloorRobot(MailRoomFlooring mailroom) {
        super(mailroom);
        this.mailRoom = mailroom;
        this.heading = null;
    }

    /**
     * Transfer items from a column robot to this floor robot
     * @param robot column robot to take items from
     */
    void transfer(Robot robot) {  // Transfers every item assuming receiving robot has capacity
        ListIterator <Deliverable> iter = robot.getDeliverables().listIterator();
        while(iter.hasNext()) {
            Deliverable deliverable = iter.next();
            if (this.add(deliverable)) {  // Hand it over
                robot.load -= deliverable.myWeight();
                iter.remove();
            }
        }
    }

    /**
     * An override of ticking method from an abstract parent class
     * Delivers items if robot has any
     * Otherwise checks for waiting column robots and heading towards the one of them to get mail items from it
     */
    @Override
    public void tick() {
        System.out.println("About to tick: " + this);
        // No items to deliver
        if (getDeliverables().isEmpty()) {
            // Get left and right column robots
            ColumnRobot columnRobotLeft = mailRoom.LeftCR;
            ColumnRobot columnRobotRight = mailRoom.RightCR;

            // Check if robots are at the proper floors
            if (columnRobotLeft.getTargetFloor() != getFloor() || columnRobotLeft.getFloor() != getFloor()) {
                columnRobotLeft = null;
            }
            if (columnRobotRight.getTargetFloor() != getFloor() || columnRobotRight.getFloor() != getFloor()) {
                columnRobotRight = null;
            }

            // Choose the column robot with the earliest arrival time and move towards it
            if (heading == Building.Direction.LEFT || (columnRobotLeft != null && (columnRobotRight == null ||
                    columnRobotLeft.getEarliestItem() <= columnRobotRight.getEarliestItem()))) {
                // The floor robot is adjacent to a column robot, transfer items
                if (getRoom () == 1) {
                    transfer(columnRobotLeft);
                    this.heading = null;
                    this.shallMoveRight = true;
                }
                // The floor robot is not adjacent to a column robot, move towards it
                else {
                    this.heading = Building.Direction.LEFT;
                    move(heading);
                }
            }
            else if (heading == Building.Direction.RIGHT || columnRobotRight != null) {
                // The floor robot is adjacent to a column robot, transfer items
                if (getRoom() == Building.getBuilding().NUMROOMS) {
                    transfer(columnRobotRight);
                    this.heading = null;
                    this.shallMoveRight = false;
                }
                // The floor robot is not adjacent to a column robot, move towards it
                else {
                    this.heading = Building.Direction.RIGHT;
                    move(heading);
                }
            }
        }
        // Items to deliver
        else {
            // Deliver all relevant items to that room
            if (getRoom() == getDeliverables().getFirst().myRoom()) {
                do {
                    Deliverable deliverable = getDeliverables().removeFirst();
                    load -= deliverable.myWeight();
                    Simulation.deliver(deliverable);
                } while (!getDeliverables().isEmpty() && getRoom() == getDeliverables().getFirst().myRoom());
            }
            // Move towards next delivery
            else {
                if (shallMoveRight) { move(Building.Direction.RIGHT); }  // safeguards ??
                else { move(Building.Direction.LEFT); }
            }
        }
    }
}
