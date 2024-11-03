package kds240002;

import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;// We can use this to traverse the entire tree
    int size;
    Deque<Entry<T>> path = new ArrayDeque<>();//Stack used to store the path we traverse while searching for x

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    private Entry<T> find(Entry<T> t, T x){//Helper method used to trace the tree to find an element
        if(t == null || t.element == x){
            return t;
        }
        //path.push(null);
        while(true){
            if(x.compareTo(t.element) < 0){//If the number we need to find is less than the current, we need to traverse to the left
                if(t.left == null){//This means we have reached the bottom of the most left tree and need to exit while loop
                    break;
                }
                path.push(t);//Push t onto the stack to remember the path
                t = t.left;//Update t to the left child
                }
            else if(x.compareTo(t.element) == 0){//We have found x and need to exit
                break;
            }
            else if(t.right == null){
                break;
            }
            else{//x > t so we need to traverse right
                path.push(t);//Push parent onto the stack
                t = t.right;//Update t to the right child
            }
        }
        return t;//We have exited the while loop and now return t;
    }

    public Boolean contains(T x){
        Entry<T> t = find(root, x);
        //Evaluating t==null first prevents a null pointer exception thrown by referencing t.element
        return t != null && t.element == x;
    }

    public Boolean add(Entry<T> t, T x){ //O(height of the tree)
        if(size == 0){//The tree must be empty
            root = t;//Add new bst node as the root
            size++;//Increase size of the tree
            return true;//The value has been added, return true
        }
        Entry<T> node = find(root, x);//node will hold either the value we searched for or the node where the search failed
        if(node.element == x){//We must already have the value within the tree
            return false;//So we return false to indicate the second occurrence was not added
        }
        if(x.compareTo(node.element) < 0){//x is less than the node we failed the search at
            node.left = t;//Add x as a left child
        }
        else{//x is greater than the node we failed the search at
            node.right = t;//Add x as a right child
        }
        size++;//We have increased the size of the tree
        return true;//We have added the new value to the tree so return true
    }

    public Entry<T> createEntry(){//Method needed to prevent inheritance methods between bst and avl classes
         return new Entry<>(null, null, null);
    }

    public T remove(T x){//Method used to remove value from tree
        if(size ==0){//Our tree must be empty so we return null
            return null;
        }
        Entry<T> t = find(root, x);//Find x and return it as node t
        if(t.element != x){//We did not find x in the tree so return null
            return null;
        }
        if(t.left == null || t.right == null){//We have at most one child and need to splice()
            splice(t);
        }
        else{//We have found our node and it has two children
            path.push(t);//We need to push the parent onto the stack to remember the path
            Entry<T> minRight = find(t.right, x);
            t.element = minRight.element;//Copied the min element and moved to t
            splice(minRight);//We have one child so we splice minRight
        }
        size --;
        return x;
    }

    private void splice(Entry<T> t){
        Entry<T> parent = path.peek();//We peek from the stack to get the parent node
        Entry<T> child = (t.left == null ? t.right : t.left);////Used to determine if left or right child is being spliced
        if(parent == null){
            root = child;
        }
        else if(parent.left == t){
            parent.left = child;
        }
        else if(parent.right == t){
            parent.right = child;
        }
    }
    public Entry<T> getRoot(){
        return root;
    }


}
