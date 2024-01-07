import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Point spielerPosition = new Point(22,9);
        Point schlangenPosition = new Point(30,2);
        Point goldPosition = new Point(6,6);
        Point zielPosition = new Point(2,5);
        boolean goldEingesammelt = false;
        boolean weiter = true;
        //Spielfeld 10x40
        
        while (weiter) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 40; x++) {
                Point p = new Point(x,y);
                if (p.equals(spielerPosition))
                    System.out.print("P");
                else if (p.equals(schlangenPosition))
                    System.out.print("S");
                else if (p.equals(goldPosition))
                    System.out.print("G");
                else if (p.equals(zielPosition))
                    System.out.print("Z");
                else {
                    System.out.print(".");
                }

                System.out.print(".");
            }
            System.out.println();

        }
        bewegeSpieler(spielerPosition);
        bewegeSchlange(spielerPosition, schlangenPosition);

        // Schlangen Movement

        if (schlangenPosition.equals(spielerPosition)) {
            weiter = false;
            System.out.println("Die Schlange hat dich ZzZz... \nWie kann man nur so schlecht sein?!?!");

        }
        // Gold einsammeln

        if (spielerPosition.equals(goldPosition)){
            goldEingesammelt = true;
            goldPosition = new Point(-1,-1);
            System.out.println("Gold wurde eingesammelt!!");
        }

        // Wann man gewonnen hat

        if (spielerPosition.equals(zielPosition) && goldEingesammelt)  {
            zielPosition = new Point(-1,-1);
            weiter = false;
            System.out.println("Du hast GEWONNEN!!");

        }
    }

    }

    private static void bewegeSchlange(Point spielerPosition, Point schlangenPosition) {
        if (spielerPosition.x < schlangenPosition.x) {
            schlangenPosition.x--;
        } else if (spielerPosition.x > schlangenPosition.x)
            schlangenPosition.x++;
        if (spielerPosition.y < schlangenPosition.y) {
            schlangenPosition.y--;
        } else if (spielerPosition.y > schlangenPosition.y) {
            schlangenPosition.y++;
        }
    }


    private static void bewegeSpieler(Point spielerPosition) {
        Scanner s = new Scanner(System.in);
        String input = s.next();

        //w,a,s,d movement

        if (input.equals("w")) {
            if (spielerPosition.y > 0)
                spielerPosition.y--;
        } else if (input.equals("s")) {
            if (spielerPosition.y < 9)
                spielerPosition.y++;
        } else if (input.equals("a")) {
            if (spielerPosition.x > 0)
                spielerPosition.x--;
        } else if (input.equals("d")) {
            if (spielerPosition.x < 39)
                spielerPosition.x++;
        }


    }
}