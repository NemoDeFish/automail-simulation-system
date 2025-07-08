/**
 * Skeleton written by SMD Teaching Team.
 * Modified by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.
 */

import java.util.*;

public abstract class Robot {
    private static int count = 1;
    final private String id;
    private int floor;
    private int room;
    final private MailRoom mailroom;
    final private List<Deliverable> deliverables = new ArrayList<>();
    private static int CAPACITY = 0;
    protected int load;

    /**
     * Set robots maximum capacity
     * @param capacity maximum capacity of each robot
     */
    static void setCapacity(int capacity) {
        CAPACITY = capacity;
    }

    /**
     * The general constructor of a robot, assigns ID and a mailroom and sets a load to zero
     * @param mailroom building's mailroom
     */
    Robot(MailRoom mailroom) {
        this.id = "R" + count++;
        this.mailroom = mailroom;
        this.load = 0;
    }

    /**
     * Convert a robot to its string representation
     * @return string representation of a robot
     */
    public String toString() {
        return "Id: " + id + " Floor: " + floor + ", Room: " + room + ", #items: " + numItems() + ", Load: " + load;
    }

    /**
     * Get current floor of a robot
     * @return robots current floor
     */
    int getFloor() { return floor; }

    /**
     * Get current room of a robot
     * @return robots current room
     */
    int getRoom() { return room; }

    /**
     * Check if robot has no items to deliver
     * @return true if robot has none, otherwise false
     */
    boolean isEmpty() { return deliverables.isEmpty(); }

    /**
     * Place a robot to an assigned position
     * @param floor floor to place robot in
     * @param room room to place robot in
     */
    public void place(int floor, int room) {
        Building building = Building.getBuilding();
        building.place(floor, room, id);
        this.floor = floor;
        this.room = room;
    }

    /**
     * Attempt to move robot in a given direction
     * @param direction direction to move in
     */
    public void move(Building.Direction direction) {
        Building building = Building.getBuilding();
        int dfloor, droom;
        switch (direction) {
            case UP    -> {dfloor = floor+1; droom = room;}
            case DOWN  -> {dfloor = floor-1; droom = room;}
            case LEFT  -> {dfloor = floor;   droom = room-1;}
            case RIGHT -> {dfloor = floor;   droom = room+1;}
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }
        if (!building.isOccupied(dfloor, droom)) { // If destination is occupied, do nothing
            building.move(floor, room, direction, id);
            floor = dfloor; room = droom;
            if (floor == 0) {
                System.out.printf("About to return: " + this + "\n");
                mailroom.robotReturn(this);
            }
        }
    }

    /**
     * Perform action(s) during this time unit
     */
    public abstract void tick();

    /**
     * Get robot ID
     * @return robot ID
     */
    public String getId() { return id; }

    /**
     * Get number of mail items the robot is carrying
     * @return number of mail items the robot is carrying
     */
    public int numItems () { return deliverables.size(); }

    /**
     * Attempt to load a mail item into the robot's inventory
     * @param item mail item to load
     * @return true if succeeded, otherwise false
     */
    public boolean add(Deliverable item) {
        if (load + item.myWeight () <= CAPACITY) {
            deliverables.add (item);
            load += item.myWeight ();
            return true;
        }
        return false;
    }

    /**
     * Get all mail items the robot is carrying
     * @return mail items the robot is carrying
     */
    protected List<Deliverable> getDeliverables() { return deliverables; }

    /**
     * Sort for deliveries in order from the left side of a floor to the right
     */
    void sortLeft() { deliverables.sort (new SortForLeftCR()); }

    /**
     *  Sort for deliveries in order from the right side of a floor to the left
     */
    void sortRight() { deliverables.sort (new SortForRightCR()); }
}
