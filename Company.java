/**
 * This class represents a car rental company.
 * @version (2023)
 * @author Ariel Arie.
 */

public class Company
{
    private RentNode _head;

    /**
     * Company constractor, Creates a new rental company
     */
    public Company()
    {
        _head = null; 
    }

    public Company(RentNode r)
    {
        _head = r; 
    }

    /**
     * Boolean method that adds rent (a Rent object) to a company (Company)
     * @param name - the client's name
     * @param car - the rented car
     * @param pickDate - the pickup date
     * @param Date returnDate - the return date
     * @return true if the new rental was successfully added, and false if such a rental already exists in the list.
     */
    public boolean addRent(java.lang.String name, Car car, Date pickDate, Date returnDate)
    {
        Rent r = new Rent (name, car, pickDate, returnDate); // Creating an object of type Rent using the parameters

        if (_head == null) // If the list is empty we will set the rental to be the first
        {
            _head = new RentNode (r,null); 
            return true;
        }

        RentNode current = _head;  
        RentNode prev = _head;

        // Checking that the rental does not exist in the list and locating the appropriate place to insert the new rental
        while (current != null && !(current.getRent().equals(r)) && !(current.getRent().getPickDate().after(pickDate)))
        {
            if (current.getRent().getPickDate().equals(pickDate)) // Checking if the pick date is the same
            {
                if (r.howManyDays() > current.getRent().howManyDays()) // If the date is the same, we will precede the rental with the longer duration 
                {
                    prev.setNext(new RentNode (r,current.getNext()));
                    return true;
                }

            }

            prev = current; // The promotion of the values in the loop
            current = current.getNext();        
        }

        if (current == null) // If current = null, it means that all the dates were before the date of the current rental
        {  
            prev.setNext(new RentNode (r,null));
            return true;
        }

        else if (current.getRent().equals(r)) // A case where the rental already exists in the list
            return false;

        else if (current.getRent().getPickDate().after(pickDate)) // Finding the right place to enter the new rental according to the date
        {
            prev.setNext(new RentNode (r,current));
            current = prev.getNext();
            return true;
        }
        else return false;

    } // End of addRent method

    /**
     * This boolean method gets a date and if there is a rental in the list with the same return date, it removes it from the list)
     * @param returnDate - the date to compare
     * @return true if a removal has taken place, and returns false if not.
     */
    public boolean removeRent(Date returnDate)
    {
        if (_head == null) // list is empty
            return false;

        RentNode current = _head;  
        RentNode prev = null;

        // Checking whether there is a rental in the list with the same return date as the date received as a parameter
        while (current != null && !(current.getRent().getReturnDate().equals(returnDate)))
        {
            prev = current; // The promotion of the values in the loop
            current = current.getNext();            
        }

        if (current == null) // there is no rental in the list with the same return date as the date received as a parameter 
            return false;

        else if (current.getRent().getReturnDate().equals(returnDate)) // There is a rental in the list with the same return date as the date received as a parameter
        {
            prev.setNext(current.getNext());
            return true;
        }

        else return false;
    } // End of removeRent method

    /**
     * This method returns the number of rentals in the company
     * @return number of rentals in the company
     */
    public int getNumOfRents()
    {
        RentNode current = _head;
        int counter = 0;
        while (current != null)
        {
            counter++;
            current = current.getNext(); // The promotion of the value in the loop
        }

        return counter;        
    } // End of getNumOfRents method

    /**
     * This method calculates the profit of all rental periods represented in the list.
     * @return the total profit amount
     */
    public int getSumOfPrices()
    {
        RentNode current = _head;
        int sum = 0;
        while (current != null)
        {
            sum = sum + current.getRent().getPrice(); // Calculating the price of each rental and adding it to sum
            current = current.getNext(); // The promotion of the valuesin the loop
        }

        return sum;        
    } // End of getSumOfPrices method

    /**
     * This method calculates the total number of rental days in the entire list
     * @return the sum of the number of rental days in all rentals
     */
    public int getSumOfDays()
    {
        RentNode current = _head;
        int sum = 0;
        while (current != null)
        {
            sum = sum + current.getRent().howManyDays(); // Calculating the the number of rent days
            current = current.getNext(); // The promotion of the value in the loop
        }

        return sum;        
    } // End of getSumOfDays method

    /**
     * This method calculates the average rental days in the entire list
     * @return the average rental days in the entire list
     */
    public double averageRent()
    {
        double avg = this.getSumOfDays()/this.getNumOfRents(); // Using existing methods to prevent repetitive code
        return avg;
    } // End of averageRent method

    /**
     * This method checks which Rent has the latest return date
     * @return the Rent Car with the latest return datet
     */
    public Car lastCarRent()
    {
        if (_head == null) // list is empty
            return null;

        RentNode current = _head.getNext();
        RentNode prev = _head;
        RentNode lastCarRent = _head; // Using this pointer to return the latest rental
        // Looping through the entire list and finding the latest rental
        while (current != null)
        {
            if (current.getRent().getReturnDate().after(lastCarRent.getRent().getReturnDate()))
                lastCarRent.setNext(prev.getNext()); 

            prev = current; // The promotion of the values in the loop
            current = current.getNext();

        }

        return lastCarRent.getRent().getCar(); // Return of the latest Car
    } // End of lastCarRent method

    /**
     * This method checks what is the longest rental in the list
     * @return the Rent with the longest rental in the list
     */
    public Rent longestRent()
    {
        if (_head == null) // list is empty
            return null;

        RentNode current = _head.getNext();
        RentNode prev = _head;
        RentNode longestRent = _head; // Using this pointer to return the longest rental
        // Looping through the entire list and finding the longest rental
        while (current != null)
        {
            if (current.getRent().howManyDays() > (longestRent.getRent().howManyDays()))
                longestRent.setNext(prev.getNext()); 

            prev = current; // The promotion of the values in the loop
            current = current.getNext();

        }

        return longestRent.getRent(); // Return of the latest rental 
    } // End of longestRent method

    /**
     * This method checks the popular rating among all rentals
     * @return the popular rating among all rentals
     */
    public char mostCommonRate()
    {
        if (_head == null) // list is empty
            return 'N';

        RentNode current = _head;

        int RateCounterA = 0;
        int RateCounterB = 0;
        int RateCounterC = 0;
        int RateCounterD = 0;

        // Looping through the entire list and finding the longest rental
        while (current != null)
        {
            if (current.getRent().getCar().getType() == 'A')
                RateCounterA++;
            else if (current.getRent().getCar().getType() == 'B')
                RateCounterB++;
            else if (current.getRent().getCar().getType() == 'C')
                RateCounterC++;
            else if (current.getRent().getCar().getType() == 'D')
                RateCounterD++;

            current = current.getNext(); // The promotion of the value in the loop

        }

        // checking whether there are ratings with the same popularity,
        // and we will prioritize the rating that is considered higher
        if (RateCounterA == RateCounterB || RateCounterA == RateCounterC || RateCounterA == RateCounterD)
            RateCounterA = -1;

        if (RateCounterB == RateCounterC || RateCounterB == RateCounterD)
            RateCounterB = -1;

        if (RateCounterC == RateCounterD)
            RateCounterC = -1;
        // End of checking

        int mostCommonRate = Math.max(Math.max(RateCounterA, RateCounterB),Math.max(RateCounterC, RateCounterD));

        // This section returns the most popular rating
        if (RateCounterA == mostCommonRate)
            return 'A';

        if (RateCounterB == mostCommonRate)
            return 'B';

        if (RateCounterC == mostCommonRate)
            return 'C';

        else return 'D'; 
    } // End of mostCommonRate method

    /**
     * This method checks whether all the rentals that exist in the list received as a parameter
     * to the method exist identically in the list on which the method was invoked.
     * @param r - the new list to chack
     * @return true if each and every one of the rentals appearing in the list
     * also appears in the same way in the list on which the method was applied, and false if not.
     */
    public boolean includes(Company other)
    {
        RentNode r = other._head;
        if (r == null)
            return true;

        if (_head == null)
            return false;

        RentNode ptrOfNewList = r;
        RentNode ptrOfOldList = _head; 

        // The outer loop runs through the entire list received as a parameter
        // The inner loop checks for each element in the outer loop whether it 
        // exists in the list on which the method is invoked 
        while (ptrOfNewList != null)
        {
            while (ptrOfOldList != null && !(ptrOfOldList.getRent().equals(ptrOfNewList)))
            {
                ptrOfOldList = ptrOfOldList.getNext();
            }
            if (ptrOfOldList == null) // Reaching the end of the list means that no identical rental was found
                return false;

            ptrOfNewList = ptrOfNewList.getNext(); // The promotion of the values in the loop       
        }

        return true;
    } // End of includes method

    /**
     * This method receives a list of rentals, and inserts all the rentals into the list on which the method was invoked.
     * While maintaining the sorting according to the date of receipt of the car
     * @param r - the new list to add
     * @return the merged list
     */
    public void merge(Company other)
    {
        RentNode r = other._head;
        // With the help of a loop we will add each rental to the list on which the method was applied
        while (r != null)
        {
            this.addRent(r.getRent().getName(), r.getRent().getCar(), r.getRent().getPickDate(), r.getRent().getReturnDate());
            r = r.getNext();
        }

    } // End of includes method

    /**
     * The method returns a character string that describes all the existing rentals in the company 
     * according to a fixed format
     * @return Fixed format printing of all methods in the list
     */
    public String toString()
    {
        RentNode current = this._head;

        if (current == null) // list is empty
            return ("The company has 0 rents."); 

        // Collecting all rental strings into a variable of type string:
        String str = "";
        while (current != null)
        {
            str = str + current.getRent().toString() + "\n";
            current = current.getNext();
        }
        return ("The company has " +this.getNumOfRents()+ " rents: "+ "\n" + str);

    } // End of includes method
    } // End of Company class
