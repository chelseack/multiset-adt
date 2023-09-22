abstract class MultiSet {
    /**
     * An abstract class representing the MultiSet ADT, which supports the
     * add, remove, is_empty, count, and contains operations.
     * This class itself does not handle how the underlying data is stored,
     * so it just inherits Object.__init__.
     */
    public abstract boolean add(Object item);
    public abstract void remove(Object item);
    public abstract boolean contains(Object item);
    public abstract boolean is_empty();
    public abstract int count(Object item);
    public abstract int size();
}