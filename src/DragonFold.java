import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DragonFold
{
    private JFrame frame = new JFrame("Dragon Curve");

    private static ArrayList<Integer> directions = new ArrayList<>();

    private static final int U = 0;
    private static final int D = 1;
    private static final int L = 2;
    private static final int R = 3;

    private static final int LINE_LENGTH = 3;
    private static final int LINE_WIDTH = 3;

    private static final int COLOR_CAP = 255;
    private static final int RAIN_RINGS = 6;

    private static final Color RED = new Color(COLOR_CAP, 0, 0);
    private static final Color ORANGE = new Color(COLOR_CAP, COLOR_CAP / 2, 0);
    private static final Color YELLOW = new Color(COLOR_CAP, COLOR_CAP, 0);
    private static final Color GREEN = new Color(0, COLOR_CAP, 0);
    private static final Color BLUE = new Color(0, 0, COLOR_CAP);
    private static final Color INDIGO = new Color(COLOR_CAP / 3, 0, COLOR_CAP / 2);
    private static final Color VIOLET = new Color(COLOR_CAP / 2, 0, COLOR_CAP);

    public static void main(String[] args)
    {
        new DragonFold();
        directions.add(R);
        directionGenerator(16);
    }

    private DragonFold()
    {
        DrawDragon dragon = new DrawDragon();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(dragon);
        dragon.setBackground(Color.BLACK);
        frame.setVisible(true);
    }

    private static void directionGenerator(int num)
    {
        ArrayList<Integer> flip = new ArrayList<>();

        for (int i = 0; i < num; i++)
        {
            flip.clear();
            flip.addAll(0, directions);

            for (int j = flip.size() - 1; j >= 0; j--)
            {
                switch (flip.get(j))
                {
                    case U:
                        directions.add(R);
                        break;
                    case D:
                        directions.add(L);
                        break;
                    case L:
                        directions.add(U);
                        break;
                    case R:
                        directions.add(D);
                        break;
                }
            }
        }
    }

    public class DrawDragon extends JPanel
    {
        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            int[] temp = {frame.getWidth() / 3, (int) (frame.getHeight() / 1.7)}; //Where first point is put

            int dirSize = directions.size();
            int chunk = dirSize / RAIN_RINGS;

            for (int i = 0; i < dirSize; i++)
            {
                if (i <= chunk)
                {
                    g.setColor(colorFade(RED, ORANGE, i, chunk)); // Red to Orange
                }
                else if (i <= 2 * chunk)
                {
                    g.setColor(colorFade(ORANGE, YELLOW, i - chunk, chunk)); //Orange to Yellow
                }
                else if (i <= 3 * chunk)
                {
                    g.setColor(colorFade(YELLOW, GREEN, i - 2 * chunk, chunk)); //Yellow to Green
                }
                else if (i <= 4 * chunk)
                {
                    g.setColor(colorFade(GREEN, BLUE, i - 3 * chunk, chunk)); //Green to Blue
                }
                else if (i <= 5 * chunk)
                {
                    g.setColor(colorFade(BLUE, INDIGO, i - 4 * chunk, chunk)); //Blue to Indigo
                }
                else if (i <= 6 * chunk)
                {
                    g.setColor(colorFade(INDIGO, VIOLET, i - 5 * chunk, chunk)); //Indigo to Violet
                }

                //g.setColor(colorFade(Color.BLACK, Color.RED, i, dirSize)); //To Red
                //g.setColor(colorFade(Color.BLACK, Color.GREEN, i, dirSize)); //To Green
                //g.setColor(colorFade(Color.BLACK, Color.GREEN, i, dirSize)); //To Blue

                //g.setColor(colorFade(RED, ORANGE, i, dirSize)); // Red to Orange
                //g.setColor(colorFade(ORANGE, YELLOW, i, dirSize)); //Orange to Yellow
                //g.setColor(colorFade(YELLOW, GREEN, i, dirSize)); //Yellow to Green
                //g.setColor(colorFade(GREEN, BLUE, i, dirSize)); //Green to Blue
                //g.setColor(colorFade(BLUE, INDIGO, i, dirSize)); //Blue to Indigo
                //g.setColor(colorFade(INDIGO, VIOLET, i, dirSize)); //Indigo to Violet
                //g.setColor(colorFade(VIOLET, RED, i, dirSize)); //Violet to Red

                switch (directions.get(i))
                {
                    case U:
                        g.drawLine(temp[0], temp[1], temp[0], temp[1] - LINE_LENGTH);
                        //g.fillOval(temp[0] - LINE_WIDTH / 2, temp[1] - LINE_LENGTH / 2, LINE_WIDTH, LINE_LENGTH);
                        temp[1] -= LINE_LENGTH;
                        break;
                    case D:
                        g.drawLine(temp[0], temp[1], temp[0], temp[1] + LINE_LENGTH);
                        //g.fillOval(temp[0] - LINE_WIDTH / 2, temp[1] - LINE_LENGTH / 2, LINE_WIDTH, -LINE_LENGTH);
                        temp[1] += LINE_LENGTH;
                        break;
                    case L:
                        g.drawLine(temp[0], temp[1], temp[0] - LINE_WIDTH, temp[1]);
                        //g.fillOval(temp[0] - LINE_WIDTH / 2, temp[1] - LINE_LENGTH / 2, -LINE_WIDTH, LINE_LENGTH);
                        temp[0] -= LINE_WIDTH;
                        break;
                    case R:
                        g.drawLine(temp[0], temp[1], temp[0] + LINE_WIDTH, temp[1]);
                        //g.fillOval(temp[0] - LINE_WIDTH / 2, temp[1] - LINE_LENGTH / 2, LINE_WIDTH, LINE_LENGTH);
                        temp[0] += LINE_WIDTH;
                        break;
                }
            }
        }

        private Color colorFade(Color a, Color b, int i, int size)
        {
            int aRed = a.getRed();
            int aGreen = a.getGreen();
            int aBlue = a.getBlue();
            int bRed = b.getRed();
            int bGreen = b.getGreen();
            int bBlue = b.getBlue();

            return new Color(aRed + (i * (bRed - aRed) / size),
                    aGreen + (i * (bGreen - aGreen) / size),
                    aBlue + (i * (bBlue - aBlue) / size));
        }
    }
}
