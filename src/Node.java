import java.io.File;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;

public class Node {

    String name;
    Integer size;
    Date lastModified;
    Boolean isDirectory;
    String fileType;
    ArrayList<Node> children;
    ArrayList<Double> childRelativeSizes;

    public Node(File file) {
        name = file.getName();
        children = new ArrayList<Node>();
        size = 0;
        fileType = getFileType(file);
        lastModified = new Date(file.lastModified());
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

    public void draw(Graphics2D g, double x, double y, double w, double h, String orientation) {
        if (this.isDirectory == false) {
            g.fillRect((int) x, (int) y, (int) w, (int) h);
        } else {
            if (orientation == "Horizontal") {
                double pixelsPerBar = w / this.size;
                for (Node child : this.children) {
                    double childWidth = pixelsPerBar * child.size;
                    child.draw(g, x, y, childWidth, h, "Vertical");
                    x += childWidth;
                }
            } else {
                double pixelsPerBar = h / this.size;
                for (Node child : this.children) {
                    double childHeight = pixelsPerBar * child.size;
                    child.draw(g, x, y, w, childHeight, "Horizontal");
                    y += childHeight;
                }
            }
        }
    }

    private String getFileType(File file) {
        String name = file.getName();
        if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
            return name.substring(name.lastIndexOf(".") + 1);
        else
            return "none";
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
