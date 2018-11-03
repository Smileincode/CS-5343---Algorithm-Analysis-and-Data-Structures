// ******************PUBLIC OPERATIONS*********************
// boolean insert( x )       --> Insert x
// boolean remove( x )       --> Remove x
// boolean contains( x )     --> Return true if x is present
// void makeEmpty( )         --> Remove all items



public class MyHashTable<AnyType> {
    private static final int DEFAULT_TABLE_SIZE = 101;

    /**
     * Construct the hash table.
     */
    public MyHashTable( ) {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    private MyHashTable(int size) {
        allocateArray(size);
        makeEmpty( );
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( ) {
        currentSize = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains(AnyType x) {
        int currentPos = findPos( x );
        return isActive(currentPos);
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * @param x the item to insert.
     */
    public boolean insert(AnyType x, Boolean y) {
        int currentPos = findPos(x);
        if(isActive(currentPos))
            return false;

        array[currentPos] = new HashEntry<>(x, true, y);

        if( ++currentSize > array.length / 2 )
            rehash( );
        return true;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove(AnyType x) {
        int currentPos = findPos(x);
        if(isActive(currentPos)) {
            array[currentPos].isActive = false;
            currentSize--;
            return true;
        } else {
            return false;
        }
    }

    private static class HashEntry<AnyType> {
        public AnyType element;                 // the element
        public boolean isActive;                // false if marked deleted
        public boolean isWord;                // false if marked as prefix
        public HashEntry(AnyType e) {
            this(e, true, false);
        }
        public HashEntry(AnyType e,boolean i) {
            this(e, i, false);
        }
        public HashEntry(AnyType e, boolean i, boolean j) {
            element = e; isActive = i; isWord = j;
        }
    }

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    public boolean isWord(int currentPos) {
        return array[currentPos] != null && array[currentPos].isWord;
    }

    /**
     * Method that performs linear probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    public int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myhash(x);
        while(array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset; // Compute ith probe
            if( currentPos >= array.length )
                currentPos -= array.length;
        }
        return currentPos;
    }

    /**
     * Expand the hash table.
     */
    private void rehash( ) {
        HashEntry<AnyType> [ ] oldArray = array;

        // Create a new double-sized, empty table
        allocateArray( nextPrime( 2 * oldArray.length ) );
        currentSize = 0;

        // Copy table over
        for(HashEntry<AnyType> e : oldArray)
            if(e != null && e.isActive)

                insert(e.element, e.isWord);
    }

    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n)
    {
        if(n % 2 == 0)
            n++;

        for(; !isPrime(n); n += 2)
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n)
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    private HashEntry<AnyType> [ ] array;       // The array of elements
    private int currentSize;                    // The number of occupied cells
}
