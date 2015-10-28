import java.util.ArrayList;

/**
 * Created by Jimmy on 3/18/2015 in PACKAGE_NAME
 * 109259420
 * Homework 4
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */

/**
 * Bus class that stores passengers
 */

public class Bus {
    private final static int MAX_CAPACITY = 20;
    private int capacity;
    private String busType;
    private int busNumber;
    private int toNextStop;
    private int timeToRest;
    private int currentStop;
    private int nextStop;
    private int currentNumberOfPeople;
    private int[] route;
    private boolean lastStop;
    private ArrayList <Passenger> bus;

    /**
     * returns the number of passengers in the bus
     * @return the number of passengers in the bus
     */
    public int size() {
        return bus.size();
    }

    /**
     * sets the rest time
     * @param timeToRest is what sets the rest time
     */

    public void setTimeToRest(int timeToRest) {
        this.timeToRest = timeToRest;
    }

    /**
     * Translate integer value of stop to String
     * @param stop is integer value of stop
     * @return string value of stop
     */
    public static String getStop(int stop) {
        switch(stop) {
            case 0: return "South P";
            case 1: return "In Route West";
            case 2: return "In Route SAC";
            case 3: return "In Route Chapin";
            case 4: return "South P";
            case 5: return "Out Route PathMart";
            case 6: return "Out Route Walmart";
            case 7: return "Out Route Target";
            default: return "Invalid Stop";
        }
    }

    /**
     * Bus constructor that creates the bus
     * @param busType creates the bus type
     * @param capacity sets the capacity for the bus
     * @param busNumber is the bus number
     */
    public Bus (String busType, int capacity, int busNumber) {
        this.busType = busType;
        this.capacity = capacity;
        bus = new ArrayList<Passenger>();
        initializeRoute();
        currentStop = route[0];
        nextStop = route[0];
        this.busNumber = busNumber;
    }

    /**
     * Initializes the route that determines if the bus is an inRoute bus or an outRoute bus
     */
    public void initializeRoute() throws IllegalArgumentException {
        if (busType.equals("inRoute"))
            route = new int[]{0, 1, 2, 3};
        else if (busType.equals("outRoute"))
            route = new int[] {4 ,5 ,6 ,7};
        else
            throw new IllegalArgumentException("Invalid input");
    }

    /**
     * returns if the bus is at the last stop of its route
     * @return if the bus is at the last stop of its route
     */
    public boolean getLastStop() {
        return this.lastStop;
    }

    /**
     * Goes to the next stop on its route
     * If the bus is resting, the bus will pick up whatever is in South P and go on its route
     * @throws IllegalArgumentException if the bus is off track i.e. in route bus goes to out route bus route
     */
    public void goToNextStop() throws IllegalArgumentException {
        if(busType.equals("inRoute")) {
            if((currentStop  < 3 && !lastStop)) {
                currentStop++;
                nextStop++;
                lastStop=false;
                toNextStop = 20;
            } else if (currentStop == 3) {
                nextStop = 0;
                currentStop = 0;
                lastStop = true;
                toNextStop = 20;
            } else if (lastStop) {
                unload(0);
                timeToRest=30;
                lastStop=false;
                toNextStop = 0;
            }
            else {
                throw new IllegalArgumentException("Bus is off track!");
            }
        } else {
            if(currentStop < 7 && !lastStop) {
                currentStop++;
                nextStop++;
                lastStop=false;
                toNextStop = 20;
            } else if (currentStop == 7) {
                currentStop = 4;
                nextStop = 4;
                lastStop = true;
                toNextStop = 20;
            } else if (lastStop) {
                unload(4);
                timeToRest=30;
                lastStop=false;
                toNextStop = 0;
            } else {
                throw new IllegalArgumentException("Bus is off track!");
            }
        }
    }

    /**
     * returns the bus type i.e. in route or out route
     * @return the bus type
     */
    public String getBusType() {
        return busType;
    }

    /**
     * returns the capacity of the bus
     * @return the capacity of the bus
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This unloads the passengers that are at their intended destination
     * @param dest is the destination that the bus is currently at
     * @return the amount of individual passengers that have gotten off the bus
     */
    public int unload (int dest) {
        int counter = 0;
        for(int i = 0; i < size(); i++) {
            if (bus.get(i).getDestination() == dest) {
                counter += bus.get(i).getNumberOfPeople();
                currentNumberOfPeople -= bus.get(i).getNumberOfPeople();
                bus.remove(i);
                i--;
            }
        }
        if(counter != 0)
            System.out.println(counter+ " passengers have gotten off Bus " +busType+ " " +(busNumber+1));
        return counter;
    }

    /**
     * Allows the parameter p to board the bus
     * @param p is the passenger to board the bus
     * @param currentTime is the current time that the passenger is boarding the bus
     */
    public void board(Passenger p, int currentTime) {
        currentNumberOfPeople += p.getNumberOfPeople();
        p.setBusArrivalTime(currentTime);
        bus.add(p);
    }

    /**
     * Peek the last passenger to be added to the bus
     * @return the last passenger to be added to the bus
     */
    public Passenger peek() {
       return bus.get(size()-1);
    }

    /**
     * obtains the current number of people on the bus
     * @return the current number of people on the bus
     */
    public int getCurrentNumberOfPeople() {
        return this.currentNumberOfPeople;
    }

    /**
     * Returns if the bus is resting or not (at south p)
     * @return if the bus is resting or not
     */
    public boolean isResting() {
        return timeToRest > 0;
    }

    /**
     * Returns the current stop that the bus is on
     * @return the current stop that the bus is on
     */
    public int getCurrentStop() {
        return this.currentStop;
    }

    /**
     * Reduces the rest time of a resting bus
     */
    public void reduceRestTime() {
        if (timeToRest > 0)
            this.timeToRest--;
    }

    /**
     * Reduces the time until the next stop
     */
    public void reduceTimeUntilNextStop() {
        if(toNextStop > 0)
            this.toNextStop--;
    }

    /**
     * returns the bus's next stop
     * @return the bus's next stop
     */
    public int getToNextStop() {
        return this.toNextStop;
    }

    /**
     * Returns the bus number
     * @return the bus number
     */
    public int getBusNumber() {
        return this.busNumber;
    }

    /**
     * Returns the String representation of the bus as in if it is resting or if it is in motion
     * @return the String representation of the bus
     */
    public String toString() {
        if(!isResting())
            return getBusType()+ " Bus " +(busNumber+1)+ " is currently heading toward " +getStop(nextStop)+ ". Arrives in " +toNextStop;
        else
            return getBusType()+ " Bus " +(busNumber+1)+ " is resting for " +timeToRest;
    }

}
