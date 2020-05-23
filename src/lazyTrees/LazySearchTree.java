package lazyTrees;

import java.util.*;

/**
 * Implementation of the binary search tree that enables lazy deletion.
 *
 * @author Foothill College, Hamza Alam
 */

public class LazySearchTree <E extends Comparable<? super E >>
        implements Cloneable {
    protected int mSize;
    protected LazySTNode mRoot;
    protected int mSizeHard;

    /**
     * Basic constructor
     */
    public LazySearchTree(){
        clear();
    }

    /**
     * Checks if tree is empty
     * @return true if tree is empty
     */
    public boolean empty(){
        return (mSize == 0);
    }

    /**
     * Returns the number of soft nodes in tree
     * @return int of size of nodes
     */
    public int size(){
        return mSize;
    }

    /**
     * Returns the number of hard nodes in tree
     * @return int of size of nodes
     */
    public int sizeHard(){
        return mSizeHard;
    }

    /**
     * Resets all values of tree
     */
    public void clear(){
        mSize = 0;
        mSizeHard = 0;
        mRoot = null;
    }

    /**
     * Shows how height of the tree
     * @return the height of the tree
     */
    public int showHeight(){
        return findHeight(mRoot,-1);
    }

    public boolean collectGarbage(){
        int oldSize = mSize;
        mRoot = collectGarbage(mRoot);
        return (mSize != oldSize);
    }

    /**
     * Finds the smallest value of the tree that has
     * not been deleted
     * @return smallest value
     */
    public E findMin(){
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMin(mRoot).data;
    }

    /**
     * Finds the smallest value of all the trees
     * @return data
     */
    public E findMinHard(){
        if(mRoot == null)
            throw new NoSuchElementException();
        return findMinHard(mRoot).data;
    }

    /**
     * Finds the largest value of the that hasn't
     * been deleted
     * @return data
     */
    public E findMax(){
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMax(mRoot).data;
    }

    /**
     * Finds the largest value from all the trees
     * @return data
     */
    public E findMaxHard(){
        if(mRoot == null)
            throw new NoSuchElementException();
        return findMaxHard(mRoot).data;
    }

    /**
     * Method that searches for data in the tree
     * @param x the item in the tree
     * @return data
     */
    public E find( E x ){
        LazySTNode resultNode;
        resultNode = find(mRoot, x);
        if (resultNode == null)
            throw new NoSuchElementException();
        return resultNode.data;
    }

    /**
     * Method that searches for data in all trees
     * @param x the item in the tree
     * @return data
     */
    public E findHard( E x )
    {
        LazySTNode resultNode;
        resultNode = findHard(mRoot, x);
        if (resultNode == null)
            throw new NoSuchElementException();
        return resultNode.data;
    }

    /**
     * Uses find() to search for data in
     * undeleted trees
     * @param x the data to be found
     * @return true if data is in the tree
     */
    public boolean contains(E x){
        return find(mRoot, x) != null;
    }

    /**
     * call find() to search for data in
     * all trees
     * @param x the data to be found
     * @return true if the tree contains data
     */
    public boolean containsHard(E x){
        return findHard(mRoot, x) != null;
    }

    /**
     * Inserts item into the tree
     * @param x inserts into tree
     * @return true if the new size is not equal to old size
     */
    public boolean insert(E x){
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Removes item from the tree
     * @param x the item to be removed
     * @return true if the size of the tree is not the same after item
     * is removed
     */
    boolean remove(E x){
        int oldSize = mSize;
        remove(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Removes item from the tree
     * @param x the item to be removed
     * @return true if the tree size is different after removal
     */
    public boolean removeHard(E x){
        int oldSize = mSize;
        mRoot = removeHard(mRoot, x);
        return (mSize != oldSize);
    }


    /**
     * Traverses tree and removes nodes that are soft deleted
     * @param root object from LazySTNode
     * @return the object
     */
    protected LazySTNode collectGarbage(LazySTNode root)
    {
        if(root == null)
            return null;

        root.lftChild = collectGarbage(root.lftChild);
        root.rtChild = collectGarbage(root.rtChild);

        if(root.deleted)
            root = removeHard(root, root.data);

        return root;
    }

    /**
     * Traverses the undeleted nodes of the tree
     * takes in an argument to search through the tree
     * @param func
     * @param <F>
     */
    public < F extends Traverser<? super E > > void traverseSoft(F func)
    {
        traverseSoft(func, mRoot);
    }

    /**
     * Traverses the undeleted nodes of the tree
     * takes in an argument to search through the tree
     * @param func
     * @param <F>
     */
    public < F extends Traverser<? super E > > void traverseHard(F func)
    {
        traverseHard(func, mRoot);
    }

    /**
     * Clones tree and makes copy of it.
     */
    public Object clone() throws CloneNotSupportedException{
        LazySearchTree<E> newObject = (LazySearchTree<E>)super.clone();
        //prevent pointing towards other data
        newObject.clear();
        newObject.mRoot = cloneSubtree(mRoot);
        newObject.mSize = mSize;

        return newObject;
    }

    //private helper methods

    /**
     * Finds the minimum from the undeleted nodes
     * @param root
     * @return an object from LazySTNode
     */
    protected LazySTNode findMin(LazySTNode root){
        if (root == null)
            return null;
        LazySTNode val = findMin(root.lftChild);

        if (val != null)
            return val;
        if (!root.deleted)
            return root;

        return findMin(root.rtChild);
    }

    /**
     * Find minimum of the all nodes in the tree
     * @param root node of interest
     * @return the minimum node
     */

    protected LazySTNode findMinHard(LazySTNode root){
        if (root == null)
            return null;
        if (root.lftChild == null)
            return root;
        return findMinHard(root.lftChild);
    }

    /**
     * Find the maximum node of the tree
     * @param root node of interest
     * @return the max node
     */
    protected LazySTNode findMax(LazySTNode root){
        if (root == null)
            return null;

        LazySTNode val = findMax(root.rtChild);
        if (val != null)
            return val;
        if (!root.deleted)
            return root;

        return findMax(root.lftChild);
    }

    /**
     * Find the maximum of all nodes of the tree
     * @param root node of interest
     * @return the max node
     */
    protected LazySTNode findMaxHard(LazySTNode root){
        if (root == null)
            return null;
        if (root.rtChild == null)
            return root;
        return findMaxHard(root.rtChild);
    }

    /**
     * Adds an item to the tree
     * @param root the node of interest
     * @param x item to be added
     * @return the node
     */
    protected LazySTNode insert(LazySTNode root, E x){
        int compareResult;

        if (root == null){
            mSize++;
            mSizeHard++;
            return  new LazySTNode(x, null, null);
        }

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            root.lftChild = insert(root.lftChild, x);
        else if (compareResult > 0)
            root.rtChild = insert(root.rtChild, x);
        else if (root.deleted){
            root.deleted = false;
            mSize++;
        }
        return root;
    }

    /**
     * Removes an item from the tree
     * @param root the node of interest
     * @param x the item to be removed
     * @return the node
     */
    protected LazySTNode remove(LazySTNode root, E x){
        int compareResult;

        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            root.lftChild = remove(root.lftChild, x);
        else if (compareResult > 0)
            root.rtChild = remove(root.rtChild, x);

        else{
            root.deleted = true;
            mSize--;
        }
        return root;
    }

    /**
     * Removes item from the tree as well as
     * adjusting the number of the deleted and
     * undeleted nodes
     * @param root the node of interest
     * @param x the node to be removed
     * @return the node
     */
    protected LazySTNode removeHard(LazySTNode root, E x){
        int compareResult;
        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            root.lftChild = removeHard(root.lftChild, x);
        else if (compareResult > 0)
            root.rtChild = removeHard(root.rtChild, x);

        // found the node
        else if (root.lftChild != null && root.rtChild != null)
        {
            root.data = findMin(root.rtChild).data;
            root.rtChild = removeHard(root.rtChild, root.data);
        }
        else
        {
            root =
                    (root.lftChild != null)? root.lftChild : root.rtChild;
            mSize--;
            mSizeHard--;
        }
        return root;


    }

    /**
     * Traverses through both deleted and reamining nodes
     * @param func fucntion for the object to traverse
     * @param treeNode the node
     */
    protected <F extends Traverser<? super E>> void traverseHard(F func, LazySTNode treeNode){
        if (treeNode == null)
            return;

        traverseHard(func, treeNode.lftChild);
        func.visit(treeNode.data);
        traverseHard(func, treeNode.rtChild);
    }

    /**
     * Traverse through the nodes of the tree
     * @param func function for the object ot traverse
     * @param treeNode the node
     */
    protected <F extends Traverser<? super E>> void traverseSoft(F func, LazySTNode treeNode){
        if (treeNode == null)
            return;

        traverseSoft(func, treeNode.lftChild);
        if (!treeNode.deleted)
            func.visit(treeNode.data);
        traverseSoft(func, treeNode.rtChild);
    }

    /**
     * Finds the specific nodes that aren't deleted
     * Also recursive function that makes it so that it continually
     * searches deeper through the tree
     * @param root the node that shows what subtree to look for the item
     * @param x the item to be searched
     * @return the node
     */
    protected LazySTNode find(LazySTNode root, E x){
        int compareResult;
        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            return find(root.lftChild, x);
        if (compareResult > 0)
            return find(root.rtChild, x);
        if (!root.deleted)
            return root;
        return null;
    }

    /**
     * Finds the specific nodes through all of the tree
     * @param root the node
     * @param x item to be searched
     * @return the node
     */
    protected LazySTNode findHard(LazySTNode root, E x){
        int compareResult;
        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            return findHard(root.lftChild, x);
        if (compareResult > 0)
            return findHard(root.rtChild, x);
        return root;
    }

    /**
     * Clones the tree by creating copies of both right and left subtrees
     * @param root the node that corresponds with the subtree that is selected
     * @return the corresponding subtree
     */
    protected LazySTNode cloneSubtree(LazySTNode root){
        LazySTNode newNode;
        if (root == null)
            return null;

        newNode = new LazySTNode(
                root.data,
                cloneSubtree(root.lftChild),
                cloneSubtree(root.rtChild),
                root.deleted
        );
        return newNode;
    }

    /**
     * Find height of tree
     * @param treeNode LazySTNode node
     * @param height level of the node
     * @return height of the tree
     */
    protected int findHeight( LazySTNode treeNode, int height )
    {
        int leftHeight, rightHeight;
        if (treeNode == null)
            return height;
        height++;
        leftHeight = findHeight(treeNode.lftChild, height);
        rightHeight = findHeight(treeNode.rtChild, height);
        return (leftHeight > rightHeight)? leftHeight : rightHeight;
    }

    //inner class LazySTNode~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Class that allows the tree to implement lazy deletion for the binary search tree.
     * Each node stores some data and lets it be known if a node is "deleted" or not.
     * @author hamzaalam
     */
    private class LazySTNode {
        // use public access so the tree or other classes can access members
        public LazySTNode lftChild, rtChild;
        public E data;
        public LazySTNode myRoot;  // needed to test for certain error
        public boolean deleted;

        public LazySTNode(E d, LazySTNode lft, LazySTNode rt) {
            lftChild = lft;
            rtChild = rt;
            data = d;
            deleted = false;
        }

        public LazySTNode(E x, Object o, Object o1, boolean b) {
            this(null, null, null);
        }
    }
}//end LazyStNode~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

interface Traverser<E>
{
    public void visit(E x);
}

class PrintObject<E> implements Traverser<E>{
    public void visit(E x){
        System.out.print(x + " ");
    }
};
