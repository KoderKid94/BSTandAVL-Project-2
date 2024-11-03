package kds240002;
public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        Entry<T> left;
        Entry<T> right;
        int height;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
        super();
    }

    // TO DO
    @Override
    public Boolean add(BinarySearchTree.Entry<T> t, T x) {
        Boolean added = super.add(t, x);
        if (added) {
            root = balance((Entry<T>) root);
        }
        return added;
    }
    @Override
    public AVLTree.Entry<T> createEntry(){//Method needed to prevent inheritance methods between bst and avl classes
        return new AVLTree.Entry<>(null, null, null);
    }

    private Entry<T> balance(Entry<T> t){
        if(t == null){
            return null;
        }
        t.height = 1 + Math.max(height(t.left), height(t.right));//Update height

        int balanceFactor = getBalance(t);//Get balance factor for current node
        //Below we check for imbalance cases
        if(balanceFactor > 1 && getBalance(t.left) >= 0){//LL imbalance
            return rotateRight(t);
        }
        if(balanceFactor > 1 && getBalance(t.left) < 0){//LR imbalance
            t.left = rotateLeft(t.left);
            return rotateRight(t);
        }
        if(balanceFactor < -1 && getBalance(t.right) <= 0){//RR imbalance
            return rotateLeft(t);
        }
        if(balanceFactor < -1 && getBalance(t.right) > 0){//RL imbalance
            t.right = rotateRight(t.right);
        }
        return t;//Return balanced node
    }

    private int getBalance(Entry<T> t){//Used to get height of node
        if(t == null){
            return 0;
        }
        return height(t.left) - height(t.right);
    }

    private int height(Entry<T> t){
        if(t == null){
            return 0;
        }
        return t.height;
    }

    private Entry<T> rotateLeft(Entry<T> t){//Method to rotate left
        Entry<T> one = t.right;
        Entry<T> two = one.left;

        one.left = t;//Now we rotate
        t.right = two;

        t.height = Math.max(height(t.left), height(t.right)) + 1;//Update heights
        one.height = Math.max(height(one.left), height(one.right)) + 1;
        return one;//Return new root node
    }

    private Entry<T> rotateRight(Entry<T> t){//Method to rotate right
        Entry<T> one = t.left;
        Entry<T> two = one.right;

        one.right = t;//Now we rotate
        t.left = two;

        t.height = Math.max(height(t.left), height(t.right)) + 1;//Update heights
        one.height = Math.max(height(one.left), height(one.right)) + 1;
        return one;//Return new root node
    }

    public class Result{//Helper class used to evaluate each node
        Boolean isTrue;
        int height;

        public Result(Boolean isTrue, int height) {//Constructor that will associate if each node passed/failed conditions
            this.isTrue = isTrue;
            this.height = height;
        }

        public Result evaluate(Entry<T> node){
            if(node == null){//Base case of reaching a null node
                return new Result(isTrue, -1);
            }

            Result left = evaluate(node.left);//Recursively evaluate left and right subtrees, creating Result objects that
            Result right = evaluate(node.right);//will be used to evaluate if they meet conditions of bst and avl
            //Below we evaluate for the conditions
            if(!left.isTrue || !right.isTrue){//We have met a node that failed conditions
                return new Result(false, 0);
            }
            if((node.left != null && node.left.element.compareTo(node.element) >= 0) || //We verify that the child node is != null and that each child
                    (node.right != null && node.right.element.compareTo(node.element) <= 0)){//is correctly valued respective to the parent
                return new Result(false, 0);
            }
            if(Math.abs(left.height - right.height) > 1){//Evaluating nodes for avl height parameters
                return new Result(false, 0);
            }
            int nodeHeight = (Math.max(left.height, right.height) + 1);//To find the height of a node we must the max height of the two subtrees + 1
            return new Result(true, nodeHeight);
        }

        public Boolean verify(Entry<T> node){
            return evaluate(node).isTrue;
        }
    }
}
