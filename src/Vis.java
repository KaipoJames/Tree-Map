import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    private String textToDisplay;
    private Ellipse2D.Double seth;
    private Rectangle box;
    private Point corner;
    private ArrayList<Node> content;
    private ArrayList<Double> relativeContent;
    private Node root;

    private Boolean drawn;
    private String colorScheme;

    public Vis() {
        super();
        textToDisplay = "Tree Map Program";
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        addMouseListener(this);
        addMouseMotionListener(this);
        box = null;
        content = new ArrayList<Node>();
        relativeContent = new ArrayList<Double>();
        drawn = false;
        colorScheme = "File Type";
    }

    public void getRootNode(Node rootNode) {
        root = rootNode;
        drawn = true;
        repaint();
    }

    public void repaintCanvas() {
        repaint();
    }

    public void getColorScheme(String scheme) {
        colorScheme = scheme;
        repaint();
    }

    public void setText(String t) {
        textToDisplay = t;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        // draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // render visualization
        g.setColor(Color.BLACK);
        g.drawString(textToDisplay, 10, 20);

        System.out.println("we");
        if (drawn == true) {
            System.out.println("us");
            root.draw(g, 0, 0, getWidth(), getHeight(), "Horizontal", colorScheme);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        corner = new Point(e.getX(), e.getY());
        // create a new rectangle anchored at "corner"
        box = new Rectangle(corner);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        box.setFrameFromDiagonal(corner.x, corner.y, x, y);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (seth.contains(x, y)) {
            // System.out.println("Hello, Seth's ellipse!");
            setToolTipText("Hello from " + x + "," + y);
        } else {
            setToolTipText(null);
        }
    }
}
