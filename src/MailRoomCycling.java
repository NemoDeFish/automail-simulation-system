/**
 * Written by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

public class MailRoomCycling extends MailRoom {
    /**
     * The constructor for a mailroom that operates in cycling mode
     * @param numFloors number of floors in a building
     * @param numRobots number of robots in simulation
     */
    MailRoomCycling(int numFloors, int numRobots) {
        super(numFloors);
        for (int i = 0; i < numRobots; i++) {
            idleRobots.add(new CycleRobot(this));  // In mailroom, floor/room is not significant
        }
    }

    /**
     * An override of dispatching method from an abstract parent class
     * Tries to dispatch one robot each simulation unit, always logging an attempt
     */
    @Override
    void robotDispatch() { // Can dispatch at most one robot; it needs to move out of the way for the next
        System.out.println("Dispatch at time = " + Simulation.now());
        // Need an idle robot and space to dispatch (could be a traffic jam)
        if (!idleRobots.isEmpty() && !Building.getBuilding().isOccupied(0, 0)) {
            int fwei = floorWithEarliestItem();
            if (fwei >= 0) {  // Need an item or items to deliver, starting with earliest
                Robot robot = loadAndActivateRobot(fwei);
                robot.sortLeft();  // Room order for left to right delivery
                robot.place(0, 0);
            }
        }
    }
}
