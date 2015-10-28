import java.util.ArrayList;

/**
 * Created by Jimmy on 3/18/2015 in PACKAGE_NAME
 * 109259420
 * Homework 4
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */

/**
 * Class extends ArrayList for easy coding
 */
class PassengerQueue extends ArrayList<Passenger> {

    public PassengerQueue() {}

    /**
     * Adds the passenger into the end of the list
     * @param p is the passenger added
     */
    public void enqueue(Passenger p) {
        super.add(p);
    }

    /**
     * Removes the passenger from the head of the list
     * @return the head of the list
     */
    public Passenger dequeue() {
        Passenger passenger = get(0);
        remove(get(0));
        return passenger;
    }

    /**
     * Peeks at the head of the list
     * @return the passenger at the head of the list
     */
    public Passenger peek() {
        return get(0);
    }

    /**
     * overridden method that returns size of the list
     * @return size of the queue
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * overridden method that returns of the queue is empty or not
     * @return if the queue is empty or not
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * removes the passenger from the list regardless of position
     * @param p is the passenger to be removed
     * @return if the passenger had been successfully removed
     */
    public boolean remove(Passenger p) {
        return super.remove(p);
    }

    /**
     * String representation of the PassengerQueue
     * @return the string representation of the passenger queue
     */
    public String toString() {

        for (int i = 0; i < size(); i++) {
            System.out.print(get(i)+ " ");
        }
        return "";
    }

}
