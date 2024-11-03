package kds240002;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        AVLTree<Long> avl = new AVLTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        long startTime = System.nanoTime();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    BinarySearchTree.Entry<Long> t = bst.createEntry();
                    AVLTree.Entry<Long> avlTree = avl.createEntry();
                    t.element = operand;
                    if (bst.add(t, operand)) {
                        avl.add(avlTree, operand);
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
        }

        // End Time
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println(result);
        System.out.println(elapsedTime);

        printTree(bst.getRoot());

    }
//    public static void printTree() {
//        System.out.print("[" + size + "]");
//        printTree(root);
//        System.out.println();
//    }

    // Inorder traversal of tree

    public static void printTree(BinarySearchTree.Entry<Long> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
