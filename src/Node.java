import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import java.util.Calendar;

public class Node {

    private Vis mainVis;

    String name;
    Integer size;
    Date lastModified;
    Boolean isDirectory;
    String fileType;
    ArrayList<Node> children;
    ArrayList<Double> childRelativeSizes;

    Color boxColor;
    String colorOption;
    Graphics g;

    public Node() {
        mainVis = new Vis();
    }

    public Node(File file) {
        mainVis = new Vis();
        boxColor = Color.BLACK;
        colorOption = "";
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
                size += n.size;
            }
        }
    }

    // Draw Rectangles
    public void draw(Graphics2D g, double x, double y, double w, double h, String orientation, String colorScheme) {
        if (this.isDirectory == false) {
            System.out.println("SIZE: " + this.size);
            chooseColorScheme(this, colorScheme);
            g.setColor(boxColor);
            g.fillRect((int) x, (int) y, (int) w, (int) h);
            g.setColor(Color.BLACK);
            g.drawRect((int) x, (int) y, (int) w, (int) h);
        } else {
            if (orientation == "Horizontal") {
                double pixelsPerBar = w / this.size;
                for (Node child : this.children) {
                    double childWidth = pixelsPerBar * child.size;
                    chooseColorScheme(child, colorScheme);
                    g.setColor(boxColor);
                    child.draw(g, x, y, childWidth, h, "Vertical", colorScheme);
                    x += childWidth;
                }
            } else {
                double pixelsPerBar = h / this.size;
                for (Node child : this.children) {
                    double childHeight = pixelsPerBar * child.size;
                    chooseColorScheme(child, colorScheme);
                    g.setColor(boxColor);
                    child.draw(g, x, y, w, childHeight, "Horizontal", colorScheme);
                    y += childHeight;
                }
            }
        }
    }

    // Methods to help determine which color scheme was selected

    public void getColorOption(String option) {
        colorOption = option;
    }

    private void chooseColorScheme(Node n, String colorTheme) {
        if (colorTheme.equals("File Type")) {
            setBoxColorByFileType(n);
        } else if (colorTheme.equals("Last Modified")) {
            setBoxColorByLastModified(n);
        } else if (colorTheme.equals("File Size")) {
            setBoxColorByFileSize(n);
        } else if (colorTheme.equals("Random")) {
            boxColor = Color.decode(getRandomHex());
        } else {
            boxColor = Color.WHITE;
        }
    }

    // Setting the color methods

    private void setBoxColorByFileType(Node n) {
        if (n.fileType == "txt") {
            boxColor = Color.BLUE;
        } else if (n.fileType.equals("sql")) {
            boxColor = Color.GREEN;
        } else if (n.fileType.equals("docx")) {
            boxColor = Color.LIGHT_GRAY;
        } else if (n.fileType.equals("js")) {
            boxColor = Color.YELLOW;
        } else if (n.fileType.equals("css")) {
            boxColor = Color.CYAN;
        } else if (n.fileType.equals("html")) {
            boxColor = Color.ORANGE;
        } else if (n.fileType.equals("svg")) {
            boxColor = Color.DARK_GRAY;
        } else if (n.fileType.equals("jpeg") || n.fileType.equals("jpg")) {
            boxColor = Color.RED;
        } else if (n.fileType.equals("png")) {
            boxColor = Color.PINK;
        } else if (n.fileType.equals("json")) {
            boxColor = Color.decode("0xff1a44");
        } else if (n.fileType.equals("pdf")) {
            boxColor = Color.decode("0x47cba9");
        } else {
            boxColor = Color.BLACK;
        }
    }

    private void setBoxColorByLastModified(Node n) {
        Date now = Calendar.getInstance().getTime();
        Date lastYear = getLastYearDate();
        Date lastSixMonths = getLastSixMonthsDate();
        Date lastMonth = getLastMonthDate();
        Date lastWeek = getLastWeekDate();
        Date yesterday = getYesterdayDate();
        if (n.lastModified.compareTo(lastYear) > 0 && n.lastModified.compareTo(lastSixMonths) < 0) {
            boxColor = Color.BLUE;
        } else if (n.lastModified.compareTo(lastSixMonths) > 0 && n.lastModified.compareTo(lastMonth) < 0) {
            boxColor = Color.GREEN;
        } else if (n.lastModified.compareTo(lastMonth) > 0 && n.lastModified.compareTo(lastWeek) < 0) {
            boxColor = Color.LIGHT_GRAY;
        } else if (n.lastModified.compareTo(lastWeek) > 0 && n.lastModified.compareTo(yesterday) < 0) {
            boxColor = Color.YELLOW;
        } else if (n.lastModified.compareTo(yesterday) > 0 && n.lastModified.compareTo(now) < 0) {
            boxColor = Color.CYAN;
        } else {
            boxColor = Color.BLACK;
        }
    }

    private void setBoxColorByFileSize(Node n) {
        Integer gigaByte = 1000000000;
        Integer megaByte = 1000000;
        if (n.size >= gigaByte) {
            boxColor = Color.BLUE;
        } else if (n.size < gigaByte && n.size >= gigaByte - (megaByte * 200)) {
            boxColor = Color.GREEN;
        } else if (n.size < gigaByte - (megaByte * 200) && n.size >= gigaByte - (megaByte * 400)) {
            boxColor = Color.LIGHT_GRAY;
        } else if (n.size < gigaByte - (megaByte * 400) && n.size >= gigaByte - (megaByte * 600)) {
            boxColor = Color.YELLOW;
        } else if (n.size < gigaByte - (megaByte * 600) && n.size >= gigaByte - (megaByte * 800)) {
            boxColor = Color.decode("0xd3bf53");
        } else if (n.size < gigaByte - (megaByte * 800) && n.size >= gigaByte - (megaByte * 900)) {
            boxColor = Color.ORANGE;
        } else if (n.size < gigaByte - (megaByte * 900) && n.size >= gigaByte - (megaByte * 950)) {
            boxColor = Color.DARK_GRAY;
        } else if (n.size < gigaByte - (megaByte * 950) && n.size >= gigaByte - (megaByte * 975)) {
            boxColor = Color.RED;
        } else if (n.size < gigaByte - (megaByte * 975) && n.size >= gigaByte - (megaByte * 999)) {
            boxColor = Color.CYAN;
        } else if (n.size < megaByte && n.size >= megaByte / 2) {
            boxColor = Color.PINK;
        } else if (n.size < megaByte / 2 && n.size >= megaByte / 4) {
            boxColor = Color.decode("0xff1a44");
        } else {
            boxColor = Color.WHITE;
        }
    }

    // Helper methods for random color and fileType

    private String getFileType(File file) {
        String name = file.getName();
        if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
            return name.substring(name.lastIndexOf(".") + 1);
        else
            return "none";
    }

    private String getRandomHex() {
        String hex = "0x";
        String[] choices = { "A", "B", "C", "D", "E", "F", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            String randomChoice = choices[rand.nextInt(choices.length)];
            hex += randomChoice;
        }
        System.out.println("rnadom hex: " + hex);
        return hex;
    }

    // DATE HELPER METHODS

    private Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        return date;
    }

    private Date getLastWeekDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date date = calendar.getTime();
        return date;
    }

    private Date getLastMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        return date;
    }

    private Date getLastSixMonthsDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date date = calendar.getTime();
        return date;
    }

    private Date getLastYearDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date date = calendar.getTime();
        return date;
    }

}
