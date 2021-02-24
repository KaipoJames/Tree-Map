import java.io.File;
import java.util.ArrayList;

public class Node {

    String name;
    Integer size;
    Boolean isDirectory;
    ArrayList<Node> children;
    ArrayList<Double> childRelativeSizes;

    public Node(File file) {
        name = file.getName();
        children = new ArrayList<Node>();
        if (file.isFile() == true) {
            isDirectory = false;
            size = (int) file.length();
        } else {
            isDirectory = true;
            File[] kids = file.listFiles();
            for (File kid : kids) {
                Node n = new Node(kid);
                children.add(n);
                size += (int) kid.length();
            }
        }
    }

    public void childSizesRel(ArrayList<Node> kids) {
        double max = 0;
        for (Node a : kids) {
            Integer bytes = a.size;
            // System.out.println("\nSize: " + bytes);
            if (bytes > max) {
                max = bytes;
            }
        }
        for (Node a : kids) {
            Double relativeSize = a.size / max;
            childRelativeSizes.add(relativeSize);
            // System.out.println("\nRelative Size: " + relativeSize);
        }
    }

    public ArrayList<Integer> childSizes(Node a) {
        if (a.children.size() > 0) {
            ArrayList<Integer> sizes = new ArrayList<Integer>();
            for (Node n : a.children) {
                sizes.add(n.size);
            }
            return sizes;
        } else {
            return null;
        }
    }

}
