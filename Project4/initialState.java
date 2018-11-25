import javax.swing.*;
import java.awt.*;
import java.util.*;

public class initialState extends JFrame{

    private static final int sx = 50;
    private static final int sy = 50;
    private static final int w = 10;

    private initialState() {
        Graphics jg;
        Container p = getContentPane();
        setBounds(100, 100, 1000, 1000);
        setVisible(true);
        p.setBackground(Color.white);
        setLayout(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jg =  this.getGraphics();
        paintComponents(jg);
    }

    public void paintComponents(Graphics g) {
        int width, height;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter dimensions for the Maze ");
        System.out.print("Enter a width: ");
        width = s.nextInt();
        System.out.print("Enter a height: ");
        height = s.nextInt();
        s.close();

        g.setColor(Color.black);
        for(int i = 0; i <= height; i ++)
            g.drawLine(sx, sy + i * w, sx + width * w, sy + i * w);
        for(int j = 0; j <= width; j ++)
            g.drawLine(sx + (j * w), sy, sx + (j * w), sy + height * w);
        g.setColor(Color.white);
        g.drawLine(sx, sy, sx + w - 1, sy);
        g.drawLine(sx, sy, sx, sy + w - 1);
        g.drawLine(sx + width * w, sy + height * w, sx + width * w - w + 1, sy + height * w);
        g.drawLine(sx + width * w, sy + height * w, sx + width * w, sy + height * w - w + 1);

        DisjSets mazeSet = new DisjSets(width * height);
        ArrayList<Integer> wallSet = new ArrayList<>();
        for (int i = 1; i <= width * height - 1; i++) {
            if (i < width) {
                wallSet.add(i * 2 + 1);
            } else if (i % width == 0) {
                wallSet.add(i * 2);
            } else {
                wallSet.add(i * 2);
                wallSet.add(i * 2 + 1);
            }
        }
        Random r = new Random();
        int dx, dy;
        while (wallSet.size() != 0) {
            int index = r.nextInt(wallSet.size());
            int nextWall = wallSet.get(index);
            if (nextWall % 2 == 1) {
                if (mazeSet.find(nextWall / 2) != mazeSet.find(nextWall / 2 - 1)) {
                    mazeSet.union(mazeSet.find(nextWall / 2 - 1), mazeSet.find(nextWall / 2));
                    dx = sx + ((nextWall / 2) % width) * w;
                    dy = sy + ((nextWall / 2) / width) * w;
                    g.drawLine(dx, dy + 1, dx, dy + w - 1);
                }
            } else {
                if(mazeSet.find(nextWall / 2) != mazeSet.find(nextWall / 2 - width)) {
                    mazeSet.union(mazeSet.find(nextWall / 2 - width), mazeSet.find(nextWall / 2));
                    dx = sx + ((nextWall / 2) % width) * w;
                    dy = sy + ((nextWall / 2) / width) * w;
                    g.drawLine(dx + 1, dy, dx + w - 1, dy);
                }
            }
            wallSet.remove(index);
        }
    }

    public static void main(String[] args) {
        new initialState();
    }
}

