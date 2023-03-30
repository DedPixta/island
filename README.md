# Island v1.0

Simulator of life and survival.
An island with a size of **100x20** (customizable) cells with its inhabitants. Each creature can move, eat, reproduce and starve to death.

## Finished goals
- [x] Simultaneous action of creatures
- [x] Defining an Iteration Stage/Cycle
- [x] Displaying a lot of information in a compact way.

## To run project

1. Clone the project;
2. Run the project from Runner.class in your IDE from the main method;

To change the configuration, make appropriate changes int the file in **config.yaml**

![scr](https://user-images.githubusercontent.com/101488434/228868725-ab30bdba-6fcd-48a8-95e2-9f25f8625b69.png)

## Technologies used
- Concurrent package (CountDownLatch, ConcurrentLinkedQueue, ScheduledExecutorService)
- Pattern factory method
- Lombok
- Jackson
