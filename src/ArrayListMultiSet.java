import java.util.ArrayList;
import java.util.List;

public class ArrayListMultiSet extends MultiSet {
    private List<Object> _list;

    public ArrayListMultiSet() {
        this._list = new ArrayList<>();
    }

    public boolean add(Object item) {
        this._list.add(item);
        return true;
    }

    public void remove(Object item) {
        if (this._list.contains(item)) {
            this._list.remove(item);
        }
    }

    public boolean contains(Object item) {
        return this._list.contains(item);
    }

    public boolean isEmpty() {
        return this._list.isEmpty();
    }

    public int count(Object item) {
        return this._list.size();
    }

    public int size() {
        return this._list.size();
    }

    @Override
    public boolean is_empty() {
        throw new UnsupportedOperationException("Unimplemented method 'is_empty'");
    }

}
