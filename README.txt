// TODO: For every project create a README file at the top-level directory of 
//       your project. Your README must include a list of .java files you are 
//       submitting and a brief description of each. You may ignore IDE specific 
//       files such as .gitignore 

// NOTE: Alternatively you can create this file in Markdown format. 
//       See the tutorial:
//       https://guides.github.com/features/mastering-markdown/

src/lazyTrees/Item.java
    - Stores the name of the inventory item and the corresponding count of the item

src/lazyTrees/LazySearchTree.java
    - Binary search tree which implements lazy deletion
    - Includes both a "hard" and "soft" variant for functions
    - Marks nodes removed as "deleted" instead of removing it entirely
    - Includes class Traverser which aids in navigating the tree for data
    - Includes class printObject which prints the object of interest in a string

src/lazyTrees/SuperMarket.java
    - contains main()
    - Acts like a super market to that allows items to be picked and checked out

resources/inventory_log.txt
    - Input file that tests insertion and deletion

resources/inventory_short.txt
    - A smaller input file that tests insertion and deletion

resources/inventory_invalid_removal.txt
    - Input file that tests the deletion of items that don't exist

resources/inventory_delete_all.txt
    - Input that tests deletion of nodes with no children

resources/inventory_clear.txt
    - Just a blank inventory

resources/inventory_add.txt
    - Input file that tests only insertion
