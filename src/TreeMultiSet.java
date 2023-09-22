import java.util.TreeSet;

class TreeMultiSet {
    private TreeSet<Object> _tree;

    public TreeMultiSet() {
        this._tree = new TreeSet<>();
    }

    public boolean add(Object item) {
        return this._tree.add(item);
    }

    public void remove(Object item) {
        this._tree.remove(item);
    }

    public boolean contains(Object item) {
        return this._tree.contains(item);
    }

    public boolean is_empty() {
        return this._tree.isEmpty();
    }

    public int count(Object item) {
        int count = 0;
        for (Object element : this._tree) {
            if (element.equals(item)) {
                count++;
            }
        }
        return count;
    }

    public int size() {
        return this._tree.size();
    }
}