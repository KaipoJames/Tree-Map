import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.util.ArrayList;

public class App {

    private Vis mainPanel;
    private JFrame frame;
    private ArrayList<Node> nodes;
    private JFileChooser chooseFile;
    private Node mainNode;

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
        mainNode = new Node();
    }

    private JMenuBar setupMenu() {
        // instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Choose File");

        JMenu colorMenu = new JMenu("Change Color");
        JMenuItem item2 = new JMenuItem("By File Type");
        JMenuItem item3 = new JMenuItem("By Last Modified");
        JMenuItem item4 = new JMenuItem("By File Size");
        JMenuItem item5 = new JMenuItem("No Color");
        JMenuItem item6 = new JMenuItem("Random Color");

        // setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile = new JFileChooser();
                chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = chooseFile.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = chooseFile.getSelectedFile();
                    System.out.println("Chosen File: " + chosenFile.getName());
                    File file = new File(chosenFile.getAbsolutePath());
                    if (file.canExecute() == true) {
                        frame.setTitle("Tree Map for " + file);
                        Node root = new Node(file);
                        String[] path_children = file.list();
                        Integer parent_size = 0;
                        System.out.println("\nFILE Located\nCONTENTS:");
                        for (String x : path_children) {
                            String child_path = file + "/" + x;
                            File child_file = new File(child_path);
                            Node child = new Node(child_file);
                            System.out.println("FILE TYPE: " + child.fileType);
                            System.out.println("LAST MODIFIED: " + child.lastModified);
                            nodes.add(child);
                            parent_size += child.size;
                            System.out.println(child.name + " | " + child.size + " bytes");
                        }
                        mainPanel.getRootNode(root);
                        mainNode.getColorOption("File Type");
                        mainPanel.getColorScheme("File Type");
                        System.out.println("Total File Size: " + (parent_size / 1000) + " KB");
                    } else {
                        System.out.println("File/Path Not Found");
                    }
                } else {
                    System.out.println("Command Canceled");
                    // label.setText("Open command canceled");
                }
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked File Type");
                mainNode.getColorOption("File Type");
                mainPanel.getColorScheme("File Type");
                // mainPanel.repaint(10000L);
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked last Modified");
                mainNode.getColorOption("Last Modified");
                mainPanel.getColorScheme("Last Modified");
                // mainPanel.repaint(10000L);
            }
        });
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked File Size");
                mainNode.getColorOption("File Size");
                mainPanel.getColorScheme("File Size");
                // mainPanel.repaint();
            }
        });
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked No Color");
                mainNode.getColorOption("No Color");
                mainPanel.getColorScheme("No Color");
                // mainPanel.repaint();
            }
        });
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Random Color");
                mainNode.getColorOption("Random");
                mainPanel.getColorScheme("Random");
                // mainPanel.repaint();
            }
        });

        // now hook them all together
        fileMenu.add(item1);
        menuBar.add(fileMenu);
        colorMenu.add(item2);
        colorMenu.add(item3);
        colorMenu.add(item4);
        colorMenu.add(item5);
        colorMenu.add(item6);
        menuBar.add(colorMenu);

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}