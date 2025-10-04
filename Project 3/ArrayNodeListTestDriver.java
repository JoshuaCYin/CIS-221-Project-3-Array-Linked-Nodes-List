/*
 * Name: Joshua Yin
 * Course Number: CIS-221-82A
 * Code Description: The code in ArrayNodeList.java is meant to implement a list according to the ListInterface interface using an array of linked nodes.
 *                   ArrayNodeList implements the interface ListInterface.java, which extends the classes CollectionInterface<T> and Iterable<T>.
 *                   The code in IndexNode.java defines a class indexNode which are the nodes that ArrayNodeList uses.
 *                   The code in ArrayNodeListTestDriver.java is meant to test each of the methods in ArrayNodeList.java.
 * Integrity Statement: I did not copy code from other people or sources other than our CIS-221 textbook. I did not use any AI software to help write code.
*/

import java.util.Iterator;

// Tests methods in ArrayNodeList.java. Indexing starts at 0.
public class ArrayNodeListTestDriver {
    public static void main(String[] args) {
        // Integer variables for testing
        int testIntA = 1;
        int testIntB = 2;
        int testIntC = 3;
        int testIntD = 4;

        int customCap = 30; // Custom list capacity

        ListInterface<Integer> testList = new ArrayNodeList<>(customCap);   // Create list with custom capacity

        // ======= Test basic methods =======
        System.out.println("======= Start of basic methods tests =======");

        /*
         * Test Case 1: add
         */
        System.out.println("Test Case 1: add method:");
        System.out.println("Filling list to one less than maximum capacity..." );
        for (int i = 1; i < customCap; i++) {
            testList.add(i);
        }
        System.out.println("Adding one more element to list (expecting true): " + testList.add(customCap));  // Check what add returns when successful
        System.out.println("List state: " + testList);
        System.out.println("Adding an element to the full list (expecting false): " + testList.add(testIntC)); // Check what add returns when list is full
        
        /*
         * Test Case 2: get
         */
        System.out.println("\nTest Case 2: get method:");

        // Get when list is empty
        System.out.println("Using get when list is empty:");
        resetList(testList, 0); // Empty list
        System.out.println("\t Getting " + testIntA + " (expecting null):\t" + testList.get(testIntA));

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using get on interior elements:");
        System.out.println("\tGetting 2 (expecting 2): \t" + testList.get(2));
        System.out.println("\tGetting 4 (expecting 4): \t" + testList.get(4));

        // Test on ends
        System.out.println("Using get on end elements:");
        System.out.println("\tGetting 1 (expecting 1): \t" + testList.get(1));
        System.out.println("\tGetting 10 (expecting 10): \t" + testList.get(10));

        // Test elements not in the list
        System.out.println("Using get on elements not in the list:");
        System.out.println("\tGetting 11 (expecting null): \t" + testList.get(11));
        System.out.println("\tGetting -1 (expecting null): \t" + testList.get(-1));

        /*
         * Test Case 3: contains
         */
        System.out.println("\nTest Case 3: contains method:");

        // Use contains when list is empty
        System.out.println("Using contains when list is empty:");
        resetList(testList, 0); // Empty list
        System.out.println("\tContains " + testIntB + " (expecting false): \t" + testList.contains(testIntB));

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using contains on interior elements:");
        System.out.println("\tContains 3 (expecting true): \t" + testList.contains(3));
        System.out.println("\tContains 7 (expecting true): \t" + testList.contains(7));

        // Test on ends
        System.out.println("Using contains on end elements:");
        System.out.println("\tContains 1 (expecting true): \t" + testList.contains(1));
        System.out.println("\tContains 10 (expecting true): \t" + testList.contains(10));

        // Test elements not in the list
        System.out.println("Using contains on elements not in the list:");
        System.out.println("\tContains 11 (expecting false): \t" + testList.contains(11));
        System.out.println("\tContains -1 (expecting false): \t" + testList.contains(-1));

        /*
         * Test Case 4: remove
         */
        System.out.println("\nTest Case 4: remove method:");

        // Remove when list is empty
        System.out.println("Using remove when list is empty:");
        resetList(testList, 0); // Empty list
        System.out.println("\tRemove " + testIntC + " (expecting false): \t" + testList.remove(testIntC));

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using remove on interior elements:");
        System.out.println("\tRemoving 2 (expecting true): \t" + testList.remove(2));
        System.out.println("\tRemoving 9 (expecting true): \t" + testList.remove(9));

        // Test on ends
        System.out.println("Using remove on end elements:");
        System.out.println("\tRemoving 1 (expecting true): \t" + testList.remove(1));
        System.out.println("\tRemoving 10 (expecting true): \t" + testList.remove(10));

        // Test elements not in the list
        System.out.println("Using remove on elements not in the list:");
        System.out.println("\tRemove 11 (expecting false): \t" + testList.remove(11));
        System.out.println("\tRemove -1 (expecting false): \t" + testList.remove(-1));

        System.out.println("List state after remove: " + testList);

        /*
         * Test Case 5a, b, and c: isFull, isEmpty, size
         */
        // Test Case 5a: isEmpty method with both a non-empty list and an empty list
        System.out.println("\nTest Case 5a: isEmpty method:");
        resetList(testList, 10); // Reset and fill list
        System.out.println("\tisEmpty (expecting false): \t" + testList.isEmpty());

        System.out.println("Emptying list...");
        while (!testList.isEmpty()) {
            testList.removeIndex(0);
        }
        System.out.println("\tisEmpty (expecting true): \t" + testList.isEmpty());
        
        // Test Case 5b: isFull with previous list state (which is empty) and then full list
        System.out.println("\nTest Case 5b: isFull method:");
        System.out.println("List state: " + testList);
        System.out.println("\tisFull (expecting false): \t" + testList.isFull());

        System.out.println("Filling list to max capacity (" + customCap + ")...");
        for (int i = 0; i <= customCap; i++) {
            testList.add(i);
        }
        System.out.println("\tisFull (expecting true): \t" + testList.isFull());

        // Test Case 5c: size with previous list state (which is 30)
        System.out.println("\nTest Case 5c: size method:");
        System.out.println("List state: " + testList);
        System.out.println("\tSize (expecting 30): \t\t" + testList.size());

        // End of basic method tests
        System.out.println("\nEnd of basic methods tests.");

        // ======= Test index methods =======
        System.out.println("\n======= Start of index methods tests =======");
        System.out.println("(Note that indexing starts at 0.)");

        /*
         * Test Case 6: indexed add
         */
        // Test on interior
        System.out.println("\nTest Case 6: indexed add method:");

        // Reset list
        resetList(testList, 0);

        // Add when list is empty
        System.out.println("Using indexed add when list is empty:");
        System.out.println("Adding " + testIntA + " at index 0:");
        testList.add(0, testIntA);
        System.out.println("\tList state: " + testList);
        System.out.println("Adding " + testIntA + " at index 15 (expecting exception):");
        try {
            testList.add(15, testIntA);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        // Test on interior
        System.out.println("Using indexed add on interior of list:");
        resetList(testList, 10); // Reset list to not be empty
        System.out.println("Adding " + testIntB + " at index 1:");
        testList.add(1, testIntB);
        System.out.println("\tList state: " + testList);
        System.out.println("Adding " + testIntC + " at index 9:");
        testList.add(9, testIntC);
        System.out.println("\tList state: " + testList);

        // Test on ends
        System.out.println("\nUsing indexed add on ends of list:");
        System.out.println("Adding " + testIntA + " at index 0 (start of list):");
        testList.add(0, testIntA);
        System.out.println("\tList state: " + testList);

        System.out.println("Adding " + testIntD + " at index " + testList.size() + " (end of list): ");
        testList.add(testList.size(), testIntD);
        System.out.println("\tList state: " + testList);

        // Test invalid inputs
        System.out.println("\nUsing indexed add on invalid indices:");
        System.out.println("Adding " + testIntA + " at index -1:");
        try {
            testList.add(-1, testIntA);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }
        System.out.println("Adding " + testIntA + " at index 15:");
        try {
            testList.add(15, testIntA);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        // Add when list is full
        System.out.println("Using indexed add when list is full:");
        resetList(testList, 30);
        System.out.println("Adding " + testIntA + " at index 0 when list is full:");
        try {
            testList.add(0, testIntA);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        /*
         * Test Case 7: indexed set
         */
        System.out.println("\nTest Case 7: indexed set method:");

        // Set when list is empty
        System.out.println("Using indexed set when list is empty:");
        resetList(testList, 0); // Empty list
        try {
            testList.set(0, testIntD);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        // Reset list
        resetList(testList, 10);
        
        // Test on interior
        System.out.println("Using indexed set on interior of list:");
        System.out.println("Setting index 1 to " + testIntD + ":");
        testList.set(1, testIntD);
        System.out.println("\tList state: " + testList);
        System.out.println("Setting index 8 to " + testIntD + ":");
        testList.set(8, testIntD);
        System.out.println("\tList state: " + testList);

        // Test on ends
        System.out.println("\nUsing indexed set on ends of list:");
        System.out.println("Setting index 0 to " + testIntD + ":");
        testList.set(0, testIntD);
        System.out.println("\tList state: " + testList);
        System.out.println("Setting index " + (testList.size() - 1) + " to " + testIntD + ":");
        testList.set(testList.size() - 1, testIntD);
        System.out.println("\tList state: " + testList);

        // Test invalid inputs
        System.out.println("Using indexed set on invalid indices:");
        System.out.println("Setting index -1 to " + testIntA + ":");
        try {
            testList.set(-1, testIntA);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }
        System.out.println("Setting index 14 to " + testIntA + ":");
        try {
            testList.set(14, testIntA);
            System.out.println("\tList state: " + testList);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        /*
         * Test Case 8: indexed get
         */
        System.out.println("\nTest Case 8: indexed get method:");

        // Get when list is empty
        System.out.println("Using indexed get when list is empty:");
        resetList(testList, 0); // Empty list
        try {
            testList.getIndex(0);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using indexed get on interior of list:");
        System.out.println("\tGetting element at index 3 (expecting 4): " + testList.getIndex(3));
        System.out.println("\tGetting element at index 7 (expecting 8): " + testList.getIndex(7));

        // Test on ends
        System.out.println("Using indexed get on ends of list:");
        System.out.println("\tGetting element at index 0 (expecting 1): " + testList.getIndex(0));
        System.out.println("\tGetting element at index 9 (expecting 10): " + testList.getIndex(9));

        // Test invalid inputs
        System.out.println("Using indexed get on invalid indices:");
        System.out.println("Getting element at index -1:");
        try {
            testList.getIndex(-1);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }
        System.out.println("Getting element at index 10:");
        try {
            testList.getIndex(10);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        /*
         * Test Case 9: indexOf
         */
        System.out.println("\nTest Case 9: indexOf method:");

        // indexOf when list is empty
        System.out.println("Using indexOf when list is empty:");
        resetList(testList, 0); // Empty list
        System.out.println("\tIndex of an element (expecting -1): " + testList.indexOf(testIntA));

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using indexOf on interior of list:");
        System.out.println("\tIndex of 3 (expecting 2): " + testList.indexOf(3));
        System.out.println("\tIndex of 9 (expecting 8): " + testList.indexOf(9));

        // Test on ends
        System.out.println("Using indexOf on ends of list:");
        System.out.println("\tIndex of 1 (expecting 0): " + testList.indexOf(1));
        System.out.println("\tIndex of 10 (expecting 9): " + testList.indexOf(10));

        // Test on element not in list
        System.out.println("Using indexOf on element not in list:");
        System.out.println("\tIndex of 100 (expecting -1): " + testList.indexOf(100));
        System.out.println("\tIndex of -100 (expecting -1): " + testList.indexOf(-100));

        /*
         * Test Case 10: removeIndex
         */
        System.out.println("\nTest Case 10: removeIndex method:");

        // Remove when list is empty
        System.out.println("Using indexed remove when list is empty:");
        resetList(testList, 0); // Empty list
        try {
            testList.removeIndex(0);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        // Reset list
        resetList(testList, 10);

        // Test on interior
        System.out.println("Using removeIndex on interior of list:");
        System.out.println("\tRemoving element at index 4 (expecting 5): " + testList.removeIndex(4));
        System.out.println("\tList state: " + testList);
        System.out.println("\tRemoving element at index 5 (expecting 7): " + testList.removeIndex(5));
        System.out.println("\tList state: " + testList);

        // Test on ends
        System.out.println("Using removeIndex on ends of list:");
        System.out.println("\tRemoving element at index 0 (expecting 1): " + testList.removeIndex(0));
        System.out.println("\tList state: " + testList);
        System.out.println("\tRemoving element at index 6 (expecting 10): " + testList.removeIndex(6));
        System.out.println("\tList state: " + testList);

        // Test on invalid indices
        System.out.println("Using removeIndex on invalid indices:");
        System.out.println("Removing element at index -1:");
        try {
            testList.removeIndex(-1);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }
        System.out.println("Removing element at index 6:");
        try {
            testList.removeIndex(6);
        } catch (Exception e) {
            System.out.println("\tException successfully caught: " + e);
        }

        System.out.println("End of indexed methods tests.");

        // ======= Test iterator =======
        System.out.println("\n======= Start of iterator tests =======");

        /*
         * Test Case 11: iterator
         */
        System.out.println("\nTest Case 11: iterator methods (next, hasNext, remove):");

        // Reset list
        resetList(testList, 10);

        // Test iterator object
        System.out.println("Printing list with iterator object:");
        int hold;
        Iterator<Integer> iter = testList.iterator();
        while (iter.hasNext()) {
            hold = iter.next();
            System.out.println(hold);
            iter.remove();
        }

        System.out.println("Iterator hasNext() value (expecting false): " + iter.hasNext());

        System.out.println("\nEnd of iterator methods tests.");

        // ======= Final list state =======

        System.out.println("\n======= Final list state =======\n" + testList);
    }

    // Resets the list to contain integers 1 to count
    public static void resetList(ListInterface<Integer> list, int count) {
        System.out.print("[ Resetting list...");
        while (!list.isEmpty()) {   // Remove all elements
            list.removeIndex(0);
        }
        for (int i = 1; i <= count; i++) {   // Add elements
            list.add(i);
        }
        System.out.println(" Reset list: " + list + " ]");
    }
}