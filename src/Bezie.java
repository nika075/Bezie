
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.EventQueue;
import java.awt.*;
public class Bezie {

    public static void main(String[] args) throws FileNotFoundException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyFrame();
                } catch (Exception e) {; }
            }
        });
    }
}
//-------
class MyFrame extends JFrame  {
    public MyFrame() throws FileNotFoundException {
        super("Inicjaly");
        JPanel panel = new MyPanel();
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }
}
//--------
class MyPanel extends JPanel {

    private int x[] = new int[4];
    private int y[] = new int[4];
    private int x_last;
    private int y_last;

    private int i;
    private int j;
    private int xy;
    private double t;
    private double zmienna_x;
    private double zmienna_y;

    public MyPanel() throws FileNotFoundException
    {
        setPreferredSize(new Dimension(650, 550));
        setBackground(Color.BLACK);
    }

    private void rysujPunkty(Graphics g) throws FileNotFoundException
    {
        Graphics2D g2 = (Graphics2D) g;
        File plik = new File("DS.txt");
        Scanner in = new Scanner(plik);
        GradientPaint gp = new GradientPaint(95, 95, Color.BLUE, 75, 75,
                Color.lightGray, true);
        //w pliku z danymi mam 15 linii po 4 punkty x,y w każdym wierszu (każda kreska ma poczatek-koniec i 2 pkty kontrolne)
        for(i=0;i<55;i++)
        {
            //wczytuje punkty dla jednej kreski
            for(j=0;j<4;j++)
            {
                xy = in.nextInt();
                x[j]=xy;
                xy = in.nextInt();
                y[j]=xy;
            }
            //rysowanie kolejnych kresek/punktów w małych odległościach od siebie
            //zmienna z wzoru - t od 0 do 1
            for(t=0;t<=1;t=t+0.1)
            {
                //przypadek 1 (z wzoru)
                if(t==0) {
                    x_last = x[0]; //przed załadowaniem następnej kreski musimy zapamiętywać jej ostatnie położenie w x, y
                    y_last = y[0];
                    zmienna_x= x[0]; //gdy t=0 bierzemy współrzędne x,y punktu pierwszego z obecnie wczytanej kreski jako nowe wartości x,y
                    zmienna_y=y[0];
                    g2.setPaint(gp);
                    g2.drawLine( x_last , y_last , (int)zmienna_x ,(int)zmienna_y  ); //rysowanie od ostatniego położenia do nowych wartości x,y
                }
                //przypadek 2 z wzoru
                else if(t==1)
                {
                    x_last = x[3]; //przed załadowaniem następnej kreski musimy zapamiętywać jej ostatnie położenie w x, y
                    y_last = y[3];
                    zmienna_x= x[3]; //gdy t=1 bierzemy współrzędne x,y punktu czwartego z obecnie wczytanej kreski jako nowe wartości x,y
                    zmienna_y=y[3];
                    g2.setPaint(gp);
                    g.drawLine( x_last , y_last , (int)zmienna_x ,(int)zmienna_y  ); //rysowanie od ostatniego położenia do nowych wartości x,y
                }
                //przypadek 3 z wzoru
                else
                    {
                    //gdy t różne od 0 i od 1 to liczymy x,y z wzoru
                    zmienna_x = Math.pow((1-t),3) * x[0] + 3 * (1 - t) * (1 - t) * t * x[1] + 3 * (1 - t) * t * t * x[2] + t * t * t * x[3];
                    zmienna_y = (1 - t) * (1 - t) * (1 - t) * y[0] + 3 * (1 - t) * (1 - t) * t * y[1] + 3 * (1 - t) * t * t * y[2] + t * t * t * y[3];
                    g2.setPaint(gp);
                    g.drawLine( x_last , y_last , (int)zmienna_x ,(int)zmienna_y  ); //rysowanie od ostatniego położenia do nowych wartości x,y

                    x_last= (int)zmienna_x; //przed załadowaniem następnej kreski musimy zapamiętywać jej ostatnie położenie w x, y
                    y_last= (int)zmienna_y;
                    }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));

        try
        {
            rysujPunkty(g);
        }
        catch (Exception e) {;}
    }
}

