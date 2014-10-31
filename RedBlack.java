/**

    Red-black tree
    Self-balancing BST

    MIT Introduction to Algorithms - Ch 13

    Properties:
      1. Every node is either red or black
      2. The root is black
      3. Every leaf (NIL/ null) is black
      4. If a node is red, then both of its children is black.
      5. For each node, all descendant paths have the same
         number of black nodes.

    Terms:
      o black-height(bh(x)) - the number of black nodes in a
        subtree rooted at node x (includes nils, which are leaves,
        but not x itself)

    Lemma: A red-black tree with n internal nodes has height
    at most 2lg(n+1).


*/
import java.util.*;

public class RedBlack{

    private Node root, nil;

    private enum Color{BLACK, RED}

    private class Node{
        Integer value;
        Color color;
        Node left, right, parent;


        public Node(Integer v){
            value = v;

            if(v == null)
              color = Color.BLACK;
            else
              color = Color.RED;

            parent = left = right = nil;
        }

        public Node(Integer v, Node p){
            value = v;
            color = Color.RED;
            parent = p;
            left = right = nil;
        }

        public void setValue(int v){value = v;}
        public void setColor(Color c){color = c;}
        public void setLeft(Node l){left = l;}
        public void setRight(Node r){right = r;}
        public void setParent(Node p){parent = p;}

        public Integer value(){return value;}
        public Color color(){return color;}
        public Node left(){return left;}
        public Node right(){return right;}
        public Node parent(){return parent;}
    }

    public RedBlack(){
        root = nil = new Node(null);
    }

    // Assumes x has a right child (not nil)
    // and that root.parent() == nil
    // O(1) time
    private void leftRotate(Node x){
        Node y = x.right();
        x.setRight(y.left());

        // If y's left child is not nil
        // its parent as x
        if(y.left().value() != null)
            y.left().setParent(x);

        y.setParent(x.parent());

        if(x.parent() == nil)
            root = y;
        else if (x == x.parent().left())
            x.parent().setLeft(y);
        else
            x.parent().setRight(y);

        y.setLeft(x);
        x.setParent(y);
    }

    // Assumes y has a left child (not nil)
    // and that root.parent() == nil
    // O(1) time
    private void rightRotate(Node y){
        Node x = y.left();
        y.setLeft(x.right());

        if(x.right().value() != null)
            x.right().setParent(y);

        x.setParent(y.parent());

        if(y.parent() == nil)
            root = x;
        else if(y == y.parent().left())
            y.parent().setLeft(x);
        else
            y.parent().setRight(x);

        x.setRight(y);
        y.setParent(x);
    }

    private void insertRecolor(Node z){
        while(z.parent().color() == Color.RED){
            if(z.parent() == z.parent().parent().left()){
                Node y = z.parent().parent().right();

                // Case 1: z's uncle y is red
                if(y.color() == Color.RED){
                    // Set parent and uncle as black, and grandparent as red
                    z.parent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.parent().parent().setColor(Color.RED);

                    z = z.parent().parent();
                }else{

                    // Case 2: z's uncle y is black and z is a right child
                    // Rotate z's parent to transform into case 3
                    if(z == z.parent().right()){
                        z = z.parent();
                        leftRotate(z);
                    }
                    // Case 3: z's uncle y is black and z is a left child
                    z.parent().setColor(Color.BLACK);
                    z.parent().parent().setColor(Color.RED);
                    rightRotate(z.parent().parent());
                }
            // Symmetric to the above three cases
            }else{
                Node y = z.parent().parent().left();

                if(y.color() == Color.RED){
                    z.parent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.parent().parent().setColor(Color.RED);

                    z = z.parent().parent();
                }else{
                    if(z == z.parent().left()){
                        z = z.parent();
                        rightRotate(z);
                    }
                    z.parent().setColor(Color.BLACK);
                    z.parent().parent().setColor(Color.RED);
                    leftRotate(z.parent().parent());
                }
            }
        }

        root.setColor(Color.BLACK);
    }
    // TODO: Fix null pointer exception that occurs when this is called
    private void deleteRecolor(Node x){
        while(x != root && x.color() == Color.BLACK){
            Node w;
            if(x == x.parent().left()){
                w = x.parent().right();

                // Case 1
                if(w.color() == Color.RED){
                    w.setColor(Color.BLACK);
                    x.parent().setColor(Color.RED);
                    leftRotate(x.parent());
                    w = x.parent().right();
                }
                // Case 2
                if(w.left().color() == Color.BLACK &&
                        w.right().color() == Color.BLACK){
                    w.setColor(Color.RED);
                    x = x.parent();
                }else{
                    // Case 3
                    if(w.right().color() == Color.BLACK){
                        w.left().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        rightRotate(w);
                        w = x.parent().right();
                    }
                    // Case 4
                    w.setColor(x.parent().color());
                    x.parent().setColor(Color.BLACK);
                    w.right().setColor(Color.BLACK);
                    leftRotate(x.parent());
                    x = root;
                }
            // Symmetric to above four cases
            }else{
                w = x.parent().left();

                if(w.color() == Color.RED){
                    w.setColor(Color.BLACK);
                    x.parent().setColor(Color.RED);
                    rightRotate(x.parent());
                    w = x.parent().left();
                }

                if(w.right().color() == Color.BLACK &&
                          w.left().color() == Color.BLACK){
                    w.setColor(Color.RED);
                    x = x.parent();
                }else{

                    if(w.left().color() == Color.BLACK){
                        w.right().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        leftRotate(w);
                        w = x.parent().left();
                    }

                    w.setColor(x.parent().color());
                    x.parent().setColor(Color.BLACK);
                    w.right().setColor(Color.BLACK);
                    rightRotate(x.parent());
                    x = root;
                }
            }
        }
        x.setColor(Color.BLACK);
    }

    // Takes O(lg n) time
    public void insert(Integer value){
        Node x = root;
        Node y = nil;
        Node z = new Node(value);

        // Traverse tree
        while(x != nil){
            y = x;
            if(z.value() < y.value())
                x = x.left();
            else
                x = x.right();
        }

        z.setParent(y);

        if(y == nil)
            root = z;
        else if(z.value() < y.value())
            y.setLeft(z);
        else
            y.setRight(z);

        insertRecolor(z);
    }

    // TODO: Deletion / deleteRecolor methods
    public void delete(Integer value){
        Node z = find(value);
        Node y = z;
        Node x;

        Color y_original = y.color();

        // If z has less than 2 children
        if(z.left() == nil){
            x = z.right();
            transplant(z, z.right());
        }else if(z.right() == nil){
            x = z.left();
            transplant(z, z.left());
        // If z has 2 children
        }else{
            // Get the Node of min value in z's right subtree
            // y is the successor of z now
            y = min(z.right());
            y_original = y.color();

            x = y.right();
            if(y.parent() == z)
                x.setParent(y);
            else{
                if(y != nil) transplant(y, y.right());
                y.setRight(z.right());
                y.right().setParent(y);
            }

            transplant(z,y);
            y.setLeft(z.left());
            y.left().setParent(y);
            y.setColor(z.color());
        }

        if(y_original == Color.BLACK)
            deleteRecolor(x);
    }

    // Swap node x and y
    private void transplant(Node x, Node y){
        if(x.parent() == nil)
            root = y;
        else if(x == x.parent().left())
            x.parent().setLeft(y);
        else
            x.parent().setRight(y);

        y.setParent(x.parent());
    }

    // Returns node with given value
    // and nil otherwise
    private Node find(int value){
        Node x = root;

        while(x != nil){
            if(x.value() == value)
                return x;
            else if(x.value() > value)
                x = x.left();
            else
                x = x.right();
        }

        return nil;
    }


    public Node root(){return root;}

    private Node min(Node x){

        while(x != nil)
            x = x.left();

        return x;
    }

    private Node max(Node x){

        while(x != nil)
            x = x.right();

        return x;
    }

    // Returns maximum integer in tree
    public Integer max(){
        Node x = root;
        Integer m = null;

        while(x != nil){
            m = x.value();
            x = x.right();
        }

        return m;
    }

    // Returns minimum integer in tree
    public Integer min(){
        Node x = root;
        Integer m = null;

        while(x != nil){
            m = x.value();
            x = x.left();
        }

        return m;
    }

    // Preorder traversal
    private void preorder(Node r){
        if(r == nil) return;
        else{
            System.out.printf("Value: %4d\tColor: %5s\n", r.value(), r.color());
            preorder(r.left());
            preorder(r.right());
        }
    }

    // Inorder traversal
    private void inorder(Node r){
        if(r == nil) return;
        else{
          inorder(r.left());
          System.out.printf("Value: %4d\tColor: %5s\n", r.value(), r.color());
          inorder(r.right());
        }
    }

    // Postorder traversal
    private void postorder(Node r){
        if(r == nil) return;
        else{
            postorder(r.left());
            postorder(r.right());
            System.out.printf("Value: %4d\tColor: %5s\n", r.value(), r.color());
        }
    }

    public void print(String option){

        System.out.println(option + " Traversal");

        switch(option){
            case "Preorder":
                preorder(root);
                break;
            case "Inorder":
                inorder(root);
                break;
            case "Postorder":
                postorder(root);
                break;
            default:
                System.out.println("Invalid option.");
        }

        System.out.println(" ");

    }

    public static void main(String[] args){

        RedBlack rb = new RedBlack();

        Random rand = new Random();

        System.out.println("Inserting values...\n");
        for(int i = 1; i < 100; i++){
            int n = rand.nextInt(100) + 1;
            rb.insert(n);
        }

        //rb.print("Preorder");
        rb.print("Inorder");
        //rb.print("Postorder");

        rb.delete(rb.min());

        System.out.printf("Minimum value: %4d\n", rb.min());
        System.out.printf("Maximum value: %4d\n", rb.max());
    }
}
