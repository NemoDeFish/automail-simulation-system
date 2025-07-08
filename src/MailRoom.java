/**
 * Skeleton written by SMD Teaching Team.
 * Modified by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

import java.util.*;

import static java.lang.String.format;

public abstract class MailRoom {
    public enum Mode {CYCLING, FLOORING}
    private List<Deliverable>[] waitingForDelivery;

    protected Queue<Robot> idleRobots;
    protected List<Robot> activeRobots;
    protected List<Robot> deactivatingRobots; // Don't treat a robot as both active and idle by swapping directly

    /**
     * The general constructor, initializes a mailroom with set number of floors and empty robot arrays
     * @param numFloors number of floors in the building
     */
    MailRoom(int numFloors) {
        waitingForDelivery = new List[numFloors];
        for (int i = 0; i < numFloors; i++) {
            waitingForDelivery[i] = new LinkedList<>();
        }

        idleRobots = new LinkedList<>();
        activeRobots = new ArrayList<>();
        deactivatingRobots = new ArrayList<>();
    }

    /**
     * Check if there are any items in a mailroom waiting to be delivered
     * @return true if there are any, otherwise false
     */
    public boolean someItems() {
        for (int i = 0; i < Building.getBuilding().NUMFLOORS; i++) {
            if (!waitingForDelivery[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get floor that has a mail item with the earliest arrival time
     * @return floor number that waits for delivery longer that any other one
     */
    protected int floorWithEarliestItem() {
        int floor = -1;
        int earliest = Simulation.now() + 1;
        for (int i = 0; i < Building.getBuilding().NUMFLOORS; i++) {
            if (!waitingForDelivery[i].isEmpty()) {
                int arrival = waitingForDelivery[i].getFirst().myArrival();
                if (earliest > arrival) {
                    floor = i;
                    earliest = arrival;
                }
            }
        }
        return floor;
    }

    /**
     * Log incoming mail items
     * @param items incoming mail items
     */
    void arrive(List<Deliverable> items) {
        for (Deliverable item : items) {
            waitingForDelivery[item.myFloor()-1].add(item);
            System.out.printf("Item: Time = %d Floor = %d Room = %d Weight = %d\n",
                    item.myArrival(), item.myFloor(), item.myRoom(), item.myWeight());
        }
    }

    /**
     * Dispatch robot(s) according to the operating mode
     * Is overwritten by each subclass of a MailRoom according to their rules of dispatching
     */
    abstract void robotDispatch();

    /**
     * Tick, dispatch and reactivate robots for this unit of time in simulation
     */
    public void tick() { // Simulation time unit
        for (Robot activeRobot : activeRobots) {  // Tick all active robots
            activeRobot.tick();
        }

        robotDispatch();  // dispatch robot(s) if conditions are met

        // These are returning robots who shouldn't be dispatched in the previous step
        ListIterator<Robot> iter = deactivatingRobots.listIterator();
        while (iter.hasNext()) {  // In timestamp order
            Robot robot = iter.next();
            iter.remove();
            activeRobots.remove(robot);
            idleRobots.add(robot);
        }
    }

    /**
     * Return a robot back to a mailroom and deactivate it
     * @param robot a robot that has returned
     */
    void robotReturn(Robot robot) {
        Building building = Building.getBuilding();
        int floor = robot.getFloor();
        int room = robot.getRoom();
        assert floor == 0 && room == building.NUMROOMS+1: format("robot returning from wrong place - floor=%d, room ==%d", floor, room);
        assert robot.isEmpty() : "robot has returned still carrying at least one item";
        building.remove(floor, room);
        deactivatingRobots.add(robot);
    }

    /**
     * Load first idle robot with mail items and make it active
     * @param floor floor where to deliver
     * @return loaded and ready to go robot
     */
    protected Robot loadAndActivateRobot(int floor) {
        assert idleRobots.isEmpty() : "no robots are available at this moment";
        Robot robot = idleRobots.remove ();
        waitingForDelivery[floor].removeIf(robot::add);
        activeRobots.add (robot);
        System.out.println("Dispatch @ " + Simulation.now() +
                " of Robot " + robot.getId() + " with " + robot.numItems() + " item(s)");
        return robot;
    }
}
