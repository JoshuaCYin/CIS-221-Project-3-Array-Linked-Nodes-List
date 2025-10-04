import java.util.Iterator;

// Implements a list using array with linked nodes.
// The list is not sorted. Indexing starts at 0.
public class ArrayNodeList<T> implements ListInterface<T> {
    protected final int DEFCAP = 100; // Default capacity
    protected IndexNode[] elements;   // Array to hold this list's elements, which are of type IndexNode
    protected int numElements = 0;    // Number of elements in this list
    protected int maxCap;             // Maximum array capacity

    // Set by find method
    protected boolean found;    // Is true if target found, otherwise false
    protected int location;     // Indicates location of target if found
    protected int targetIndex;  // List index of target, if found
    protected int previous;     // Indicates location of node before target if found

    // Value used to represent a null next index
    protected final int NUL = -1;

    // Variables to track array indices of the free and non-free list
    protected int first = NUL;  // Indicates the index of the first non-free node
    protected int last = NUL;   // Indicates the index of the last non-free node
    protected int free = 0;     // Indicates the index of the first free node in the array

    // Constructor
    public ArrayNodeList() {
        maxCap = DEFCAP;
        elements = new IndexNode[DEFCAP];

        // Populate the array with nodes
        for (int i = free; i < DEFCAP; i++) {
            IndexNode<T> newNode = new IndexNode<T>(null);
            elements[i] = newNode;
            if (i != DEFCAP - 1) {  // Link each node to the next, except for the last, which stays at the default (NUL)
                newNode.setNext(i + 1);
            }
        }
    }

    // Constructor with custom max capacity
    public ArrayNodeList(int customCap) {
        maxCap = customCap;
        elements = new IndexNode[maxCap];

        // Populate the array with nodes
        for (int i = free; i < maxCap; i++) {
            IndexNode<T> newNode = new IndexNode<T>(null);
            elements[i] = newNode;
            if (i != maxCap - 1) {  // Link each node to the next, except for the last, which stays at the default (NUL)
                newNode.setNext(i + 1);
            }
        }
    }

    // ======= Helper Functions =======

    // Searches list for an occurence of an element e such that e.equals(target).
    // Preconditions: target must not be null
    // Postconditions:
    //  If successful, sets the variables found to true,
    //  location to the node containing the specified target,
    //  previous to the node before the location,
    //  and targetIndex to the index of the location.
    //  If not successful, found is false.
    protected void find(T target) {
        location = first;
        found = false;
        previous = -1;
        targetIndex = -1;

        while (location != NUL) {
            targetIndex++;
            if (elements[location].getData().equals(target)) { // If data of node at location matches target
                found = true;
                return;
            }
            else {
                previous = location;    // Update previous to location
                location = elements[location].getNext();    // Update location to the next index
            }
        }
    }

    // Allocates a new node from the list of free nodes.
    // Precondition: there must be at least one free node
    // Postcondition: returns the array index of a newly allocated node and updates the free list
    protected int getNode() {
        int temp = free;                    // temp variable to store node to return
        free = elements[free].getNext();    // Update front of free list
        elements[temp].setNext(NUL);        // Default state of node has no next node (setting here just to be safe)
        return temp;                        // Return the newly allocated slot
    }

    // Deallocates a node, returning it to the list of free nodes.
    // Precondition: node must be a valid index and not in the free list, and list is not empty
    // Postcondition: resets the node at the index and adds the node back to the free list
    // Note: decrementing numElements is left for the calling method to execute
    protected void freeNode(int index) {
        if (index == first) // If freeing the first node
            first = elements[index].getNext();
        if (index == last)  // If freeing the last node
            last = previous;

        elements[index].setNext(free);   // Link removed node to rest of free list
        free = index; // Update head of free list
        elements[index].setData(null);  // Reset removed node's data
    }

    // ======= Basic Methods =======

    // Adds an element to the end of the list. Returns true if successful, false otherwise.
    // Preconditions: list should not be full
    // Postconditions: adds element to the list, updating first, last, and free if necessary, and increments numElements
    public boolean add(T element) {
        if (isFull()) { return false; }
        
        int newNode = getNode();    // Allocate a new node
        elements[newNode].setData(element);

        if (numElements == 0) { // If list was empty
            first = newNode;
            last = newNode;
        }
        else {  // Add node at end of list
            elements[last].setNext(newNode);
            last = newNode;
        }

        numElements++;
        return true;
    }
    
    // Returns an element e from this list such that e.equals(target).
    // Preconditions: target must not be null
    // Postconditions: returns the element if it exists in the list. Otherwise, returns null.
    public T get(T target) {
        find(target);
        if (found)
            return (T) elements[location].getData();
        else
            return null;
    }

    // Returns true if this collection contains an element e such that e.equals(target); otherwise returns false.
    // Preconditions: target must not be null
    // Postconditions: returns true if the element exists in the list, otherwise false.
    public boolean contains(T target) {
        find(target);
        return found;
    }

    // Removes an element e from this collection such that e.equals(target).
    // Preconditions: target must not be null
    // Postconditions: if target is found, removes the first occurence of target, decrements numElements, and returns true.
    //                 If target isn't found, returns false.
    public boolean remove (T target) {
        find(target);
        if (found) {
            if (first == location) {    // If target is the first node
                int temp = first;
                first = elements[first].getNext();
                freeNode(temp);
            }
            else if (last == location) {    // If target is the last node
                elements[previous].setNext(NUL);
                freeNode(location);
                last = previous;
            }
            else {  // If target is not the first or last node
                elements[previous].setNext(elements[location].getNext());
                freeNode(location);
            }
            
            if (first == NUL)   // List is empty
                last = NUL;

            numElements--;
        }
        return found;
    }
    
    // ======= Index Methods =======

    // Adds an element to the list at the specified index.
    // Preconditions: index must be within bounds and the list must not be full
    // Postconditions: inserts element at index, adjusts "next" indices and positions of nodes if necessary, and increments numElements.
    //                 Throws IndexOutOfBoundsException if passed an index argument such that index < 0 or index > size().
    //                 Throws IllegalStateException if array is full.
    public void add(int index, T element) {
        if ((index < 0) || (index > size()))
            throw new IndexOutOfBoundsException("Illegal index of " + index + " passed to ArrayNodeList add method.\n");

        if (isFull())
            throw new IllegalStateException("Cannot add element. ArrayNodeList is full.");

        int newNode = getNode();    // Allocate a new node
        elements[newNode].setData(element);

        if (index == 0) { // Adding to the front of the list
            if (first == NUL) { // If list is empty
                first = newNode;
                last = newNode;
            }
            else { // List is not empty
                elements[newNode].setNext(first);
                first = newNode;
            }
        }
        else {
            if (index == numElements) {  // If adding to rear
                elements[last].setNext(newNode);
                last = newNode;
            }
            else {  // Adding to an interior part of list
                int currPos = first;
                int prevPos = -1;
                for (int i = 0; i < index; i++) {   // Traverse until currPos is at index position
                    prevPos = currPos;
                    currPos = elements[currPos].getNext();
                }
                // Insert node
                elements[newNode].setNext(currPos);
                elements[prevPos].setNext(newNode);
            }
        }
        numElements++;
    }

    // Replaces an element in the list with a new element.
    // Preconditions: index must be within bounds
    // Postconditions: replaces the element at index with newElement and returns the replaced element
    //                 Throws IndexOutOfBoundsException if passed an index argument such that index < 0 or index >= size().
    public T set(int index, T newElement) {
        if ((index < 0) || (index >= size()))
            throw new IndexOutOfBoundsException("Illegal index of " + index + " passed to ArrayNodeList set method.\n");

        // Traverse to index position
        int currPos = first;
        for (int i = 0; i < index; i++) {
            currPos = elements[currPos].getNext();
        }
        T hold = (T) elements[currPos].getData();   // Temporarily store the data being replaced
        elements[currPos].setData(newElement);      // Replace the data
        return hold;
    }

    // Returns the data of the node in this list at the specified list index.
    // Preconditions: index must be within bounds.
    // Postconditions: returns the element at index.
    //                 Throws IndexOutOfBoundsException if passed an index argument such that index < 0 or index >= size().
    public T getIndex(int index) {
        if ((index < 0) || (index >= size()))
            throw new IndexOutOfBoundsException("Illegal index of " + index + " passed to ArrayNodeList getIndex method.\n");

        // Traverse to index position
        int currPos = first;
        for (int i = 0; i < index; i++) {
            currPos = elements[currPos].getNext();
        }
        return (T) elements[currPos].getData(); // Return the data of the node at the index
    }

    // Returns the index of a specified target if it exists in the list.
    // Preconditions: target must not be null.
    // Postconditions: returns the index of the target if it exists in the list. Otherwise, returns -1.
    public int indexOf(T target) {
        find (target);
        if (found)
            return targetIndex;
        else
            return -1;
    }

    // Removes the element in this list at the specified index and returns the removed element.
    // Preconditions: index must be within bounds.
    // Postconditions: removes the element at index, adjusts "next" indices, and decrements elements.
    //                 Throws IndexOutOfBoundsException if passed an index argument such that index < 0 or index >= size().
    public T removeIndex(int index) {
        if ((index < 0) || (index >= size()))
            throw new IndexOutOfBoundsException("Illegal index of " + index + " passed to ArrayNodeList removeIndex method.\n");

        T hold; // Variable to store data being removed
        if (index == 0) {   // Removing first node
            hold = (T) elements[first].getData();
            int temp = first;
            first = elements[first].getNext();  // Will be NUL if no other nodes
            if (numElements == 1) // Ensure last is also NUL if no other nodes
                last = NUL;
            freeNode(temp);
        }
        else {
            // Locate previous node
            int prevPos = first;
            for (int i = 0; i < (index - 1); i++) {
                prevPos = elements[prevPos].getNext();
            }
            hold = (T) elements[elements[prevPos].getNext()].getData(); // Get the data of the node we want to remove
            if (elements[prevPos].getNext() == last)                    // If removing rear node
                last = prevPos; // Set last to previous node
            int next = elements[elements[prevPos].getNext()].getNext(); // Get next node
            freeNode(elements[prevPos].getNext());                      // Free removed node
            elements[prevPos].setNext(next);                            // Link previous node to next node
        }
        numElements--;
        return hold;
    }

    // ======= Iterator =======

    // Preconditions: none.
    // Postconditions: Returns an iterator over this list.
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int prevPos = NUL;
            private int currPos = NUL;
            private int nextPos = first;

            // Returns true if the iteration has more elements; otherwise returns false.
            // Preconditions: none.
            // Postconditions: returns true if there are more elements in the list, otherwise false.
            public boolean hasNext() {
                return (nextPos != NUL);
            }

            // Returns the next element in the iteration.
            // Preconditions: there must be a next element.
            // Postconditions: returns the next element in the iteration and updates "next" indices.
            public T next() {
                if (!hasNext())
                    throw new IndexOutOfBoundsException("Illegal invocation of next in LBList iterator.\n");

                T hold = (T) elements[nextPos].getData();   // Store the data of the node at the next position
                if (currPos != NUL) prevPos = currPos;      // If not at end of list, update previous position
                currPos = nextPos;                          // Update current position
                nextPos = elements[nextPos].getNext();      // Update next position
                return hold;
            }

            // Preconditions: next() must have been called at least once in the iterator
            // Postconditions: removes the last element returned by next() from the list and adjusts "next" indices as necessary
            public void remove() {
                if (currPos == NUL) return; // Conditional will be true if next() has not been used
                else {
                    if (prevPos == NUL) {   // Removing first node
                        first = nextPos;
                        freeNode(currPos);
                        currPos = NUL;
                        if (first == NUL)   // List is empty
                            last = NUL;
                    }
                    else {  // Removing in interior of list
                        elements[prevPos].setNext(nextPos);
                        freeNode(currPos);
                        currPos = NUL;
                    }
                    numElements--;
                }
            }
        };
    }

    // ======= General Methods =======

    // Returns true if the list (array) is full, otherwise returns false
    public boolean isFull() {
        return (numElements == maxCap || free == NUL);
    }

    // Returns true if the list (array) is empty, otherwise returns false
    public boolean isEmpty() {
        return (numElements == 0);
    }

    // Returns the number of elements in the list
    public int size() {
        return numElements;
    }

    // Returns a string representation of the list, with elements separated by spaces
    public String toString() {
        if (isEmpty())
            return "List is empty.";
        
        String listString = "";
        int currPos = first;
        for (int i = 0; i < numElements; i++) {
            listString = listString + elements[currPos].getData();
            if (elements[currPos].getNext() != NUL) // Add a space as long as current node is not the last
                listString = listString + " ";
            currPos = elements[currPos].getNext();
        }
        return listString;
    }
}