# AVL-TREE
This repository contains a Java implementation of an AVL tree, a self-balancing binary search tree. The tree automatically rebalances itself during insertions and deletions to ensure O(log n) time complexity for search, insertion, and deletion operations.



An AVL Tree is a self-balancing binary search tree (BST) that maintains its balance by ensuring that, for every node, the heights of its left and right subtrees differ by no more than one. This balance property guarantees that the tree remains approximately balanced, leading to efficient operations such as insertion, deletion, and search, all with a time complexity of O(log n).

Key Characteristics of AVL Trees:

Balance Factor: Each node in an AVL tree has a balance factor, defined as the difference between the heights of its left and right subtrees. The balance factor can be -1, 0, or 1. If, after an insertion or deletion, a node's balance factor becomes less than -1 or greater than 1, the tree performs rotations to restore balance.

Rotations: To maintain balance, AVL trees perform rotations during insertions and deletions. The primary types of rotations are:

Right Rotation (Single Rotation): Used when a left-heavy subtree needs balancing.
Left Rotation (Single Rotation): Used when a right-heavy subtree needs balancing.
Left-Right Rotation (Double Rotation): Applied when the left child of the left subtree is heavy.
Right-Left Rotation (Double Rotation): Applied when the right child of the right subtree is heavy.
Insertion in AVL Trees:

Inserting a new node into an AVL tree involves the following steps:

Standard BST Insertion: Insert the new node following the standard binary search tree insertion procedure.

Update Heights: After insertion, update the height of each ancestor node to reflect the change in the tree's structure.

Check Balance Factors: For each ancestor node, calculate the balance factor. If any node has a balance factor of -2 or 2, it indicates that the subtree rooted at that node is unbalanced.

Perform Rotations: Depending on the balance factor and the structure of the unbalanced subtree, perform the appropriate rotation(s) to restore balance.

Deletion in AVL Trees:

Deleting a node from an AVL tree follows these steps:

Standard BST Deletion: Remove the node using the standard binary search tree deletion procedure.

Update Heights: After deletion, update the height of each ancestor node to reflect the change in the tree's structure.

Check Balance Factors: For each ancestor node, calculate the balance factor. If any node has a balance factor of -2 or 2, it indicates that the subtree rooted at that node is unbalanced.

Perform Rotations: Depending on the balance factor and the structure of the unbalanced subtree, perform the appropriate rotation(s) to restore balance.

Advantages of AVL Trees:

Efficient Operations: Due to their balanced nature, AVL trees provide efficient O(log n) time complexity for search, insertion, and deletion operations.

Predictable Performance: The strict balancing criteria ensure that the tree remains balanced, leading to consistent performance even in the worst-case scenarios.
