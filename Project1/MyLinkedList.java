/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }

    private void clear( )
    {
        doClear( );
    }

    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }

    public boolean isEmpty( )
    {
        return size( ) == 0;
    }

    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );
        return true;
    }

    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }

    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }


    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }

    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;

        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );

        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        }

        return p;
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }

    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Swaps the item at position idx1 and idx2.
     * @param idx1 the first index to swap.
     * @param idx2 the second index to swap.
     * @throws IndexOutOfBoundsException if either idx1 or idx2 is not between 0 and size( ) - 1, inclusive.
     */
    public void swap( int idx1, int idx2 ) {
        int temp = 0;
        if (idx1 == idx2) return;
        if (idx1 > idx2) {
            temp = idx1;
            idx1 = idx2;
            idx2 = temp;
        }
        Node<AnyType> p = getNode(idx1);
        Node<AnyType> q = getNode(idx2);
        if (idx2 == idx1 + 1) {
            p.next = q.next;
            q.prev = p.prev;
            if (q.next != null) {
                q.next.prev = p;
            } else {
                endMarker.prev = p;
            }
            if (p.prev != null) {
                p.prev.next = q;
            } else {
                beginMarker.next = q;
            }
            q.next = p;
            p.prev = q;
        } else {
            Node<AnyType> m = getNode(idx2).prev;
            Node<AnyType> n = getNode(idx2).next;
            q.prev = p.prev;
            q.next = p.next;
            p.next.prev = q;
            if (p.prev != null) {
                p.prev.next = q;
            } else {
                beginMarker.next = q;
            }
            p.prev = m;
            p.next = n;
            m.next = p;
            if (n != null) {
                n.prev = p;
            } else {
                endMarker.prev = p;
            }
        }
        modCount++;
    }

    /**
     * Shifts the list many positions forward or backward.
     * @param steps the number of positions the list shifts.
     */
    public void shift( int steps ) {
        steps = steps % size();
        if (steps == 0) return;
        if (steps < 0) steps += size();
        Node<AnyType> newBegin = getNode(steps);
        Node<AnyType> oldBegin = beginMarker.next;
        Node<AnyType> oldEnd = endMarker.prev;
        endMarker.prev = newBegin.prev;
        newBegin.prev.next = endMarker;
        beginMarker.next = newBegin;
        newBegin.prev = beginMarker;
        oldEnd.next = oldBegin;
        oldBegin.prev = oldEnd;
        modCount++;
    }

    /**
     * Removes elements beginning at the index position for the number of elements specified.
     * @param idx the beginning position.
     * @param num the number of elements.
     * @throws IndexOutOfBoundsException if either idx or idx + num - 1 is not between 0 and size( ) - 1, inclusive.
     */
    public void erase( int idx, int num ) {
        if(num == 0) return;
        if(idx == 0 && num == size()) {
            doClear();
        } else if(idx == 0){
            Node<AnyType> newBegin = getNode(num);
            beginMarker.next = newBegin;
            newBegin.prev = beginMarker;
            theSize -= num;
            modCount++;
        } else {
            Node<AnyType> beforeRemove = getNode(idx - 1);
            Node<AnyType> lastRemove = getNode(idx + num - 1);
            beforeRemove.next = lastRemove.next;
            if (lastRemove.next != null) {
                lastRemove.next.prev = beforeRemove;
            } else {
                endMarker.prev = beforeRemove;
            }
            theSize -= num;
            modCount++;
        }
    }

    /**
     * Copies the passed list into the list at the specified position.
     * @param list the passed list.
     * @param idx the specified position to insert.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    public void insertList( MyLinkedList<AnyType> list, int idx ) {
        if (list.size() == 0) return;
        MyLinkedList<AnyType> copy = new MyLinkedList<AnyType>();
        for (int i = 0; i < list.size(); i++){
            copy.add(list.get(i));
        }
        Node<AnyType> curr = getNode(idx);
        Node<AnyType> prev = curr.prev;
        Node<AnyType> insertBegin = copy.getNode(0);
        Node<AnyType> insertEnd = copy.getNode(copy.size() - 1);
        prev.next = insertBegin;
        insertBegin.prev = prev;
        insertEnd.next = curr;
        curr.prev = insertEnd;
        theSize += list.size();
        modCount++;
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext( )
        {
            return current != endMarker;
        }

        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( );

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );

            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }

        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }

    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++) {
            lst.add(i);
        }
        System.out.println("Demonstrate the swap method.");
        System.out.println("Original LinkedList = " + lst );
        lst.swap(5,5);
        System.out.println("lst.swap(5,5) = " + lst );
        lst.swap(5,5);
        lst.swap(0,1);
        System.out.println("lst.swap(0,1) = " + lst );
        lst.swap(0,1);
        lst.swap(1,2);
        System.out.println("lst.swap(1,2) = " + lst );
        lst.swap(1,2);
        lst.swap(8,9);
        System.out.println("lst.swap(8,9) = " + lst );
        lst.swap(8,9);
        lst.swap(3,7);
        System.out.println("lst.swap(3,7) = " + lst );
        lst.swap(3,7);
        lst.swap(0,6);
        System.out.println("lst.swap(0,6) = " + lst );
        lst.swap(0,6);
        lst.swap(0,9);
        System.out.println("lst.swap(0,9) = " + lst );
        lst.swap(0,9);
        System.out.println("");

        System.out.println("Demonstrate the shift method.");
        System.out.println("Original LinkedList = " + lst );
        lst.shift(-4);
        System.out.println("lst.shift(-4) = " + lst);
        lst.shift(4);
        lst.shift(0);
        System.out.println("lst.shift(0) = " + lst );
        lst.shift(0);
        lst.shift(5);
        System.out.println("lst.shift(5) = " + lst );
        lst.shift(-5);
        lst.shift(9);
        System.out.println("lst.shift(9) = " + lst );
        lst.shift(-9);
        lst.shift(-9);
        System.out.println("lst.shift(-9) = " + lst );
        lst.shift(9);
        System.out.println("");

        System.out.println("Demonstrate the erase method.");
        System.out.println("Original LinkedList = " + lst );
        lst.erase(0, 10);
        System.out.println("lst.erase(0, 10) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        lst.erase(0, 6);
        System.out.println("lst.erase(0, 6) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        lst.erase(3, 6);
        System.out.println("lst.erase(3, 6) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        lst.erase(3, 7);
        System.out.println("lst.erase(3, 7) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        System.out.println("");

        System.out.println("Demonstrate the insertList method.");
        System.out.println("Original LinkedList = " + lst );
        MyLinkedList<Integer> lstInserted = new MyLinkedList<>( );
        for( int i = 0; i < 2; i++) {
            lstInserted.add(0);
        }
        lst.insertList(lstInserted, 9);
        System.out.println("lst.insertList(" + lstInserted + ", 9) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        lstInserted = new MyLinkedList<>( );
        lst.insertList(lstInserted, 7);
        System.out.println("lst.insertList(" + lstInserted + ", 7) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        lstInserted = new MyLinkedList<>( );
        for( int i = 0; i < 5; i++) {
            lstInserted.add(0);
        }
        lst.insertList(lstInserted, 0);
        System.out.println("lst.insertList(" + lstInserted + ", 0) = " + lst + "; size = " + lst.size());
        lst = new MyLinkedList<>( );
        for( int i = 0; i < 10; i++)
            lst.add(i);
        System.out.println("");

//        for( int i = 0; i < 10; i++ )
//            lst.add( i );
//        for( int i = 20; i < 30; i++ )
//            lst.add( 0, i );
//
//        lst.remove( 0 );
//        lst.remove( lst.size( ) - 1 );
//
//        System.out.println( lst );
//
//        java.util.Iterator<Integer> itr = lst.iterator( );
//        while( itr.hasNext( ) )
//        {
//            itr.next( );
//            itr.remove( );
//            System.out.println( lst );
//        }
    }
}