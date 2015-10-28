/**
 * Created by Jimmy on 3/18/2015 in PACKAGE_NAME
 * 109259420
 * Homework 4
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */

/**
 * This is the passenger class that holds the number of people, the destination, time arrived at the stop, bus arrival time, and wait time
 */
public class Passenger {
    private int numberOfPeople;
    private int destination;
    private int timeArrived;
    private int busArrivalTime;
    private int waitTime;


    /**
     * constructor for passenger
     * @param numberOfPeople is the number of people
     * @param destination is the intended destination
     * @param timeArrived is the time arrived at the bus stop
     */
    public Passenger(int numberOfPeople, int destination, int timeArrived) {
        this.numberOfPeople = numberOfPeople;
        this.destination = destination;
        this.timeArrived = timeArrived;

    }

    /**
     * Translate integer value of stop to String
     * @param stop is integer value of stop
     * @return string value of stop
     */
    public static String getStop(int stop) {
        switch(stop) {
            case 0: return "In Route South P";
            case 1: return "In Route West";
            case 2: return "In Route SAC";
            case 3: return "In Route Chapin";
            case 4: return "Out Route South P";
            case 5: return "Out Route PathMart";
            case 6: return "Out Route Walmart";
            case 7: return "Out Route Target";
            default: return "Invalid Stop";
        }
    }

    /**
     * return integer value of intended destination
     * @return integer value of intended destination
     */
    public int getDestination() {
        return destination;
    }

    /**
     * return number of people in passenger group
     * @return number of people in passenger group
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * return time arrived at bus stop
     * @return time arrived at bus stop
     */
    public int getTimeArrived() {
        return timeArrived;
    }

    /**
     * sets the bus arrival time and the wait time
     * @param busArrivalTime is the time arrived at the bus
     */

    public void setBusArrivalTime(int busArrivalTime) {
        this.busArrivalTime = busArrivalTime;
        this.waitTime = busArrivalTime-timeArrived;
    }

    /**
     * return the passenger wait time
     * @return the passenger wait time
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * String representation of a passenger
     * @return String representation of a passenger
     */
    public String toString() {
        return "[" + getNumberOfPeople()+ " , " + getStop(getDestination()) + " ," + getTimeArrived()+ " ]";
    }
}


