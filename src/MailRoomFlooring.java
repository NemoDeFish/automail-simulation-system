/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

public class MailRoomFlooring extends MailRoom {
    private final int numRooms;
    ColumnRobot LeftCR, RightCR;

    /**
     * The constructor for a mailroom that operates in flooring mode
     * @param numFloors number of floors in a building
     * @param numRooms number of rooms on each floor
     */
    MailRoomFlooring (int numFloors, int numRooms) {
        super(numFloors);
        this.numRooms = numRooms;

        LeftCR = new ColumnRobot(this);
        RightCR = new ColumnRobot(this);
        for (int i = 0; i < numFloors; i++) {
            FloorRobot robot = new FloorRobot(this);
            robot.place(i+1, 1);
            activeRobots.add(robot);
        }
        idleRobots.add(LeftCR);
        idleRobots.add(RightCR);
    }

    /**
     * An override of dispatching method from an abstract parent class
     * Dispatches all column robots from a mailroom, logging only in case of success
     */
    @Override
    void robotDispatch() {
        // Need an idle robot, no traffic jam expected since we have robots operating in individual columns
        while (!idleRobots.isEmpty()) {
            int fwei = floorWithEarliestItem();
            if (fwei >= 0) {  // Need an item or items to deliver, starting with earliest
                System.out.println("Dispatch at time = " + Simulation.now());
                Robot robot = loadAndActivateRobot(fwei);
                if (robot == LeftCR) {  // Room order for left to right delivery
                    robot.sortLeft();
                    robot.place(0, 0);
                } else if (robot == RightCR) {  // Room order for right to left delivery
                    robot.sortRight();
                    robot.place(0, numRooms + 1);
                }
            }
            else break;
        }
    }
}
