import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class AVLTree {
    class Node {
        int key;
        String data;
        int height;
        Node left, right;

        Node(int key, String data) {
            this.key = key;
            this.data = data;
            this.height = 1;
        }
    }

    private Node root;

    // Insert method
    public void insert(int key, String data) {
        root = insert(root, key, data);
    }

    private Node insert(Node node, int key, String data) {
        if (node == null) {
            return new Node(key, data);
        }
        if (key < node.key) {
            node.left = insert(node.left, key, data);
        } else if (key > node.key) {
            node.right = insert(node.right, key, data);
        } else {
            throw new IllegalArgumentException("Duplicate keys are not allowed.");
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    // Balance the tree
    private Node balance(Node node) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Range Query
    public List<String> rangeQuery(int startKey, int endKey) {
        List<String> result = new ArrayList<>();
        rangeQuery(root, startKey, endKey, result);
        return result;
    }

    private void rangeQuery(Node node, int startKey, int endKey, List<String> result) {
        if (node == null) {
            return;
        }
        if (startKey < node.key) {
            rangeQuery(node.left, startKey, endKey, result);
        }
        if (startKey <= node.key && endKey >= node.key) {
            result.add("ID: " + node.key + ", Data: " + node.data);
        }
        if (endKey > node.key) {
            rangeQuery(node.right, startKey, endKey, result);
        }
    }

    // Save Query Results to File
    public void saveRangeQueryToFile(int startKey, int endKey, String fileName) {
        List<String> results = rangeQuery(startKey, endKey);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Records in range [" + startKey + ", " + endKey + "]:\n");
            for (String record : results) {
                writer.write(record);
                writer.newLine();
            }
            System.out.println("Results saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Load Records from File
    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int key = Integer.parseInt(parts[0].trim());
                    String data = parts[1].trim();
                    insert(key, data);
                }
            }
            System.out.println("Records loaded successfully from: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid record format in file: " + e.getMessage());
        }
    }
}

 class AVLTreeGUI extends JFrame {
    private AVLTree avlTree = new AVLTree();

    public AVLTreeGUI() {
        setTitle("AVL Tree Database");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Insert Section
        JPanel insertPanel = new JPanel();
        insertPanel.add(new JLabel("Key:"));
        JTextField keyField = new JTextField(5);
        insertPanel.add(keyField);
        insertPanel.add(new JLabel("Data:"));
        JTextField dataField = new JTextField(10);
        insertPanel.add(dataField);
        JButton insertButton = new JButton("Insert");
        insertPanel.add(insertButton);
        add(insertPanel);

        // Range Query Section
        JPanel rangePanel = new JPanel();
        rangePanel.add(new JLabel("Start Key:"));
        JTextField startKeyField = new JTextField(5);
        rangePanel.add(startKeyField);
        rangePanel.add(new JLabel("End Key:"));
        JTextField endKeyField = new JTextField(5);
        rangePanel.add(endKeyField);
        JButton queryButton = new JButton("Range Query");
        rangePanel.add(queryButton);
        add(rangePanel);

        // Load and Save Section
        JPanel loadPanel = new JPanel();
        JButton loadButton = new JButton("Load from File");
        loadPanel.add(loadButton);
        JButton saveButton = new JButton("Save Query to File");
        loadPanel.add(saveButton);
        add(loadPanel);

        // Display Section
        JTextArea displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane);

        // Button Actions
        insertButton.addActionListener(e -> {
            try {
                int key = Integer.parseInt(keyField.getText());
                String data = dataField.getText();
                avlTree.insert(key, data);
                displayArea.append("Inserted: Key=" + key + ", Data=" + data + "\n");
            } catch (NumberFormatException ex) {
                displayArea.append("Invalid key format!\n");
            }
        });

        queryButton.addActionListener(e -> {
            try {
                int startKey = Integer.parseInt(startKeyField.getText());
                int endKey = Integer.parseInt(endKeyField.getText());
                List<String> results = avlTree.rangeQuery(startKey, endKey);
                displayArea.append("Range Query [" + startKey + ", " + endKey + "]:\n");
                for (String record : results) {
                    displayArea.append(record + "\n");
                }
            } catch (NumberFormatException ex) {
                displayArea.append("Invalid range key format!\n");
            }
        });

        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                avlTree.loadFromFile(fileName);
                displayArea.append("Loaded records from file: " + fileName + "\n");
            }
        });

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                avlTree.saveRangeQueryToFile(0, Integer.MAX_VALUE, fileName);
                displayArea.append("Saved query results to file: " + fileName + "\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AVLTreeGUI gui = new AVLTreeGUI();
            gui.setVisible(true);
        });
    }
}
