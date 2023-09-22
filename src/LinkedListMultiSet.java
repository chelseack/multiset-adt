import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

class LinkedListMultiSet extends MultiSet {
    /**
     * Unlike the TreeMultiList, this implementation does not just "wrap" an underlying tree, it is instead a custom LinkedList implementation, which only provides the necessary MultiSet methods.
     * Representation Invariant: self._front is None represents an empty MultiSet
     */
    private _Node _front;
    private int _size;

    public LinkedListMultiSet() {
        this._front = null;
        this._size = 0;
    }

    public boolean add(Object item) {
        _Node new_node = new _Node(item);
        new_node.next = this._front;
        this._front = new_node;
        this._size += 1;
        return true;
    }

    public void remove(Object item) {
        _Node cur = this._front;
        _Node prev = null;

        while (cur != null) {
            if (cur.item == item) {
                this._size -= 1;

                if (prev != null) {
                    prev.next = cur.next;
                } else { // first item
                    this._front = cur.next;
                }
                return;
            }
            prev = cur;
            cur = cur.next;
        }
        // if here, item not found
    }

    public boolean contains(Object item) {
        _Node cur = this._front;

        while (cur != null) {
            if (cur.item == item) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public boolean is_empty() {
        return this._front == null;
    }

    public int count(Object item) {
        int num_seen = 0;
        _Node cur = this._front;

        while (cur != null) {
            if (cur.item == item) {
                num_seen += 1;
            }
            cur = cur.next;
        }
        return num_seen;
    }

    public int size() {
        return this._size;
    }

    private class _Node {
        /**
         * Internal node structure used by the LinkedListMultiSet above.
         */
        private Object item;
        private _Node next;

        public _Node(Object item) {
            this.item = item;
            this.next = null;
        }
    }

    public static void profileMultiSet(MultiSet my_input, int n) {
        /**
         * Run the timing experiment for the given <my_input> MultiSet implementation, for a problem size of <n>.
         */

        // add n random items, then remove them all; we will only time the removal step.

        List<Integer> items_added = new ArrayList<>();

        for (int i=0; i<n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 101);
            my_input.add(x);
            items_added.add(x);
        }

        // sanity check that we added n items
        assert my_input.size() == n;

        long start = System.currentTimeMillis();

        for (int x : items_added) {
            my_input.remove(x);
        }

        long end = System.currentTimeMillis();

        // sanity check that we successfully removed all the items we had added!
        assert my_input.is_empty();

        // just print a quick summary of what we just ran
        System.out.printf("%5s %37s % .6f", n, my_input.getClass(), end - start);

    }
}