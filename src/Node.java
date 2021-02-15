import java.io.File;
import java.util.ArrayList;

public class Node {

    String name;
    Integer size;
    Boolean isDirectory;
    ArrayList<Node> children;

    public Node(File file) {
        name = file.getName();
        children = new ArrayList<Node>();
        if (file.isFile() == true) {
            isDirectory = false;
            size = (int) file.length();
        } else {
            isDirectory = true;
            File[] children = file.listFiles();
            for (File kid : children) {
                Node n = new Node(kid);
                // children.add(n);
                // size += n.getSize();
            }
        }
    }

}
