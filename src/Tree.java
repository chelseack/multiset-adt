import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Tree {
    private Object _root;
    private List<Tree> _subtrees;

    public Tree(Object root, List<Tree> subtrees) {
        this._root = root;
        this._subtrees = subtrees;
    }

    public Tree(Object root) {
        this._root = root;
        this._subtrees = null;
    }

    public boolean is_empty() {
        return this._root == null;
    }

    public int __len__() {
        if (is_empty()) {
            return 0;
        }
        else {
            int size = 1; // count the root
            for (Tree subtree : _subtrees) {
                size += subtree.__len__(); // could also do len(subtree) here
            }
            return size;
        }
    }

    public int count(Object item) {
        if (is_empty()) {
            return 0;
        } else {
            int num = 0;
            if (_root.equals(item)) {
                num += 1;
            }
            for (Tree subtree : _subtrees) {
                num += subtree.count(item);
            }
            return num;
        }
    }

    public String __str__() {
        return _str_indented(0);
    }

    private String _str_indented(int depth) {
        if (is_empty()) {
            return "";
        } else {
            //String r = " ".repeat(depth);    can run in java 11
            String r = "";
            if (depth > 0) {
                for (int i = 0; i < depth; i++){
                    r = r + "  ";
                }
            }
            StringBuilder s = new StringBuilder(r + _root + "\n");
            for (Tree subtree : _subtrees) {
                // Note that the 'depth' argument to the recursive call is
                // modified.
                s.append(subtree._str_indented(depth + 1));
            }
            return s.toString();
        }
    }


    public float average() {
        if (is_empty()) {
            return 0.0f;
        } else {
            int[] averageResult = _average_helper();
            return (float) averageResult[0] / averageResult[1];
        }
    }

    private int[] _average_helper() {
        if (is_empty()) {
            return new int[]{0, 0};
        } else {
            int total = (int) _root;
            int size = 1;
            for (Tree subtree : _subtrees) {
                int[] subtreeAverage = subtree._average_helper();
                total += subtreeAverage[0];
                size += subtreeAverage[1];
            }
            return new int[]{total, size};
        }
    }

    public boolean __eq__(Tree other) {
        if (is_empty() && other.is_empty()) {
            return true;
        } else if (is_empty() || other.is_empty()) {
            return false;
        } else {
            if (_root != other._root) {
                return false;
            }
            if (_subtrees.size() != other._subtrees.size()) {
                return false;
            }
            return _subtrees == other._subtrees;
        }
    }

    public boolean __contains__(Object item) {
        if (is_empty()) {
            return false;
        }
        // item may in root, or subtrees
        if (_root == item) {
            return true;
        } else {
            for (Tree subtree : _subtrees) {
                if (subtree.__contains__(item)) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<Object> leaves() {
        if (is_empty()) {
            return Collections.emptyList();
        } else if (_subtrees.isEmpty() || _subtrees == null) { // The elif condition is equivalent to this: self._subtrees == []
            List<Object> worklist = new ArrayList<>();
            worklist.add(_root);
            return worklist;
        } else {
            List<Object> leaves = new ArrayList<>();
            for (Tree subtree : _subtrees) {
                leaves.add(subtree.leaves());
            }
            return leaves;
        }
    }




    // -------------------------------------------------------------------------
    // Mutating methods
    // -------------------------------------------------------------------------

    public boolean delete_item(Object item) {
        if (is_empty()) { // The item is not in the tree.
            return false;
        } else if (_root == item) { // We've found the item: now delete it.
            _delete_root();
            return true;
        } else { // Loop through each subtree, and stop the first time
            // the item is deleted. (This is why a boolean is returned!)
            for (Tree subtree : _subtrees) {
                boolean deleted = subtree.delete_item(item);
                if (deleted && subtree.is_empty()) {
                    // The item was deleted and the subtree is now empty.
                    // We should remove the subtree from the list of subtrees.
                    // Note that mutate a list while looping through it is
                    // EXTREMELY DANGEROUS!
                    // We are only doing it because we return immediately
                    // afterwards, and so no more loop iterations occur.
                    _subtrees.remove(subtree);
                    return true;
                } else if (deleted){
                    return true;
                } else {
                    continue;
                }

            }
            return false;
        }
    }


    private void _delete_root(){
        if (_subtrees.isEmpty() || _subtrees == null) {
            _root = null;
        } else {
            Tree chosen_subtrees = null;
            for (Tree subtree : _subtrees){
                chosen_subtrees = subtree;  //get the last subtree.  Python: chosen_subtree = self._subtrees.pop()
            }
            _root = chosen_subtrees._root;
            _subtrees.addAll(chosen_subtrees._subtrees);

        }
    }

    public Object _extractLeaf() {
        if (_subtrees.isEmpty() || _subtrees == null) {
            Object old_root = _root;
            _root = null;
            return old_root;
        } else {
            Object leaf = _subtrees.get(0)._extractLeaf();
            if (_subtrees.get(0).is_empty()) {
                _subtrees.remove(0);
            }
            return leaf;
        }
    }

    public void insert(Object item) {
        if (this.is_empty()) {
            _root = item;
        } else if (_subtrees.isEmpty() || _subtrees == null) {
            _subtrees.add(new Tree(item));
        } else {
            if (new Random().nextInt(3) == 2) {
                _subtrees.add(new Tree(item));
            } else {
                int subtree_Index = new Random().nextInt(_subtrees.size());
                _subtrees.get(subtree_Index).insert(item);
            }
        }
    }

    public boolean insertChild(Object item, Object parent) {
        if (this.is_empty()) {
            return false;
        } else if (_root == parent) {
            _subtrees.add(new Tree(item));
            return true;
        } else {
            for (Tree subtree : _subtrees) {
                if (subtree.insertChild(item, parent)) {
                    return true;
                }
            }
            return false;
        }
    }
}

