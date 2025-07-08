# 📦 Automail Simulation System

Welcome to the **Automail** simulation project — an automated robotic mail delivery system developed to operate efficiently in multi-floor building environments. This project extends the base system to handle **parcel delivery** and introduces a new **FLOORING** delivery mode in addition to the default **CYCLING** mode. Automail was developed to autonomously deliver mail (letters and parcels) within a high-rise building using intelligent robots and a centralized mailroom. The system simulates robot movement, mail loading, and item delivery over time — providing trace logs and performance statistics.

In the original design, responsibilities were poorly distributed and classes were tightly coupled, making maintenance and scaling difficult. Our restructured design uses abstract classes (`MailRoom`, `Robot`, `Deliverable`) and delegates mode-specific and robot-specific behavior to subclasses. This promotes extensibility, testability, and clean separation of concerns.


---

## 🏢 Building Layout

The simulation assumes a grid-like building:

* **Mailroom** at row 0
* **Rooms** arranged by floor and room number
* **Robotic tracks** on columns `0` and `roomsPerFloor + 1`

---

## 🛠 Features

* 📬 **Support for Letters and Parcels**

  * Letters are weightless
  * Parcels have weight and affect robot capacity
* 🤖 **Two Operating Modes**

  * **Cycling Mode**: Robots follow a clockwise loop delivering to one floor at a time.
  * **Flooring Mode**: Dedicated floor robots and column robots cooperate to deliver efficiently.
* ⚙️ **Customizable Configuration**

  * Building dimensions
  * Robot count (mode-dependent)
  * Parcel delivery toggle
  * Delivery mode (`cycling` or `flooring`)
* 📊 **Simulation Engine**

  * Simulates tick-by-tick robot and mailroom behavior
  * Outputs trace logs and average delivery time

---

## 📂 Project Structure

```bash
.
├── properties/
│   ├── test.properties
│   └── testShort.properties
├── src/
│   ├── Building.java
│   ├── BuildingGrid.java
│   ├── ColumnRobot.java
│   ├── CycleRobot.java
│   ├── Deliverable.java
│   ├── FloorRobot.java
│   ├── Letter.java
│   ├── MailRoom.java
│   ├── MailRoomCycling.java
│   ├── MailRoomFlooring.java
│   ├── Main.java
│   ├── Parcel.java
│   ├── Robot.java
│   └── Simulation.java
├── documentation/
│   └── Design Class Diagram.pdf
│   └── Design Sequence Diagram.pdf
├── README.md
```

## 📐 Design Documents

- **Class Diagram:** See [`Design Class Diagram.pdf`](./documentation/Design%20Class%20Diagram.pdf)
- **Sequence Diagram:** See [`Design Sequence Diagram.pdf`](./documentation/Design%20Sequence%20Diagram.pdf)

## 🧠 Design Analysis and Justification
The Automail system has been carefully designed with solid object-oriented and GRASP principles to improve modularity, flexibility, and maintainability:

### Abstraction
- `Robot`, `MailRoom`, and `Deliverable` are abstract base classes.
- This allows the system to operate on a general level without being tightly bound to specific implementations.
- Enables mode-specific and robot-specific behavior without altering the core logic.

### Polymorphism
- Robot subclasses (`CycleRobot`, `FloorRobot`, `ColumnRobot`) override delivery behavior.
- Deliverables (`Letter`, `Parcel`) implement shared behavior in their own way.
- MailRoom subclasses (`MailRoomCycling`, `MailRoomFlooring`) control mode-specific logic while sharing a common interface.

### Inheritance
- Common behavior is defined in base classes (`Robot`, `MailRoom`, `Deliverable`) and reused in their subclasses.
- Helps avoid code duplication and improves extensibility for future robot or mode types.

### Encapsulation
- Each class encapsulates its own data and behavior (e.g., weight and type are handled in each Deliverable subclass).
- Internal logic is shielded from external components, reducing interdependencies.

### Low Coupling
- Abstract interactions between `MailRoom`, `Robot`, and `Deliverable` reduce direct dependency.
- Classes interact only through well-defined interfaces, improving testability and modularity.

### High Cohesion
- Each class has a focused responsibility:
  - `Robot` handles delivery movement,
  - `MailRoom` coordinates delivery,
  - `Simulation` manages configuration and execution.
- Sorting logic is delegated to separate utility classes to keep Robot classes clean.

### GRASP Controller
- `MailRoom` acts as a system controller, managing robots and deliverables without performing their actions directly.

### GRASP Creator
- `MailRoom` subclasses are responsible for instantiating relevant Robot subclasses for each mode.

### GRASP Information Expert
- Each object contains the data it needs to perform its own operations.
- For example, `Parcel` knows its weight, and `CycleRobot` knows its movement path.

### GRASP Protected Variation
- Robot behavior and delivery modes can be extended or modified without affecting the existing system.
- Adding a new mode or robot type involves subclassing rather than modifying core classes.

### GRASP Pure Fabrication
- Sorting utilities (`SortForLeftCR`, `SortForRightCR`) are helper classes that manage sorting logic externally to maintain Robot class cohesion.

## 🧪 How to Run

### ⏱ Run the Simulation

Make sure you're in the project root folder and compile the code:

```bash
javac src/*.java
```

Run the simulation:

```bash
java -cp src Main
```

### 🛠 Configure via `test.properties`

Edit this file to modify:

* Building size
* Robot count
* Mode (`MODE=cycling` or `MODE=flooring`)
* Parcel handling (`mail.parcels=1` to enable)

---

## 👨‍💻 Authors & Acknowledgments

Developed by Ella McKercher, Volodymyr Kazmirchuk, and Si Yong Lim.

Based on base code provided by SMD Teaching Team.