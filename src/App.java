import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.ArrayList;

public class App {

    private Vis mainPanel;
    private JFrame frame;
    private ArrayList<Node> nodes;

    public App() {

        JMenuBar mb = setupMenu();

        frame = new JFrame();
        frame.setJMenuBar(mb);

        mainPanel = new Vis();
        frame.setContentPane(mainPanel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tree Map");
        frame.setVisible(true);

        nodes = new ArrayList<Node>();
    }

    private JMenuBar setupMenu() {
        // instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Choose File");

        // setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Just clicked menu item 1");
                String path = "/Users/kaipojames/Documents/Development";
                File file = new File(path);
                if (file.canExecute() == true) {
                    frame.setTitle("Tree Map for " + path);
                    Node root = new Node(file);
                    String[] path_children = file.list();
                    Integer parent_size = 0;
                    System.out.println("\nFILE Located\nCONTENTS:");
                    for (String x : path_children) {
                        String child_path = path + "/" + x;
                        File child_file = new File(child_path);
                        String file_type = getFileType(child_file);
                        Node child = new Node(child_file);
                        nodes.add(child);
                        parent_size += child.size;
                        System.out.println(child.name + " | " + child.size + " bytes | " + "Type: " + file_type);
                    }
                    mainPanel.getRootNode(root);
                    System.out.println("Total File Size: " + (parent_size / 1000) + " KB");
                } else {
                    System.out.println("File/Path Not Found");
                }
            }
        });

        // now hook them all together
        fileMenu.add(item1);
        menuBar.add(fileMenu);

        return menuBar;
    }

    private String getFileType(File file) {
        String name = file.getName();
        if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
            return name.substring(name.lastIndexOf(".") + 1);
        else
            return "none";
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}