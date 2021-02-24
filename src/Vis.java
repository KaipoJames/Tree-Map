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
    }

    public void getRootNode(Node rootNode) {
        root = rootNode;
        drawn = true;
        repaint();
    }

    public void setData(ArrayList<Node> data) {
        content.clear();
        content = data;
        double max = 0;
        for (Node a : content) {
            Integer bytes = a.size;
            System.out.println("\nSize: " + bytes);
            if (bytes > max) {
                max = bytes;
            }
        }
        for (Node a : content) {
            Double relativeSize = a.size / max;
            relativeContent.add(relativeSize);
            System.out.println("\nRelative Size: " + relativeSize);
        }
        drawn = true;
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

        final int h = getHeight();
        final int w = getWidth();

        if (drawn == true) {
            root.draw(g, 0, 0, getWidth(), getHeight(), "Horizontal");
            // g.setColor(Color.BLUE);
            // int boxCount = relativeContent.size();
            // int xSpacing = w / (boxCount + 1);
            // int x = 0;
            // int childIndex = 0;
            // for (Double a : relativeContent) {
            // double rectHeight = h;
            // double rectWidth = a * 50;
            // System.out.println("\nWidth: " + w);
            // System.out.println("\nrectWidth: " + rectWidth);
            // g.setColor(Color.BLUE);
            // g.fillRect(x, 0, (int) rectWidth, (int) rectHeight);
            // g.setColor(Color.BLACK);
            // g.drawRect(x, 0, (int) rectWidth, (int) rectHeight);
            // x += rectWidth;
            // }
            // for (Node a : content){
            // System.out.println("Node Size: " + a.size);
            // double rectHeight = h / 2;
            // double rectWidth = a.size / getWidth();
            // childIndex++;
            // System.out.println("\nWidth: " + w);
            // System.out.println("\nrectWidth: " + rectWidth);
            // g.setColor(Color.BLUE);
            // g.fillRect(x, 0, (int) rectWidth, (int) rectHeight);
            // g.setColor(Color.BLACK);
            // g.drawRect(x, 0, (int) rectWidth, (int) rectHeight);
            // x += rectWidth;
            // }
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
