package BartoszSadowski264483;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForestP {

    private String[][] map;
    private int size;
    private double forestation;
    private Random rand = new Random();
    private List<Cords> cordB = new ArrayList<>();

    private int count = 0;

    public ForestP(int size, double forestation) {
        size = size+2;                                    //+2 bo niżej robie border dla tablicy
        this.size = size;
        this.forestation = forestation;
        map = new String[size][size];
    }

    public void map_initialization() {
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                double chance1 = rand.nextDouble();
                if(i==0 || j==0 || i==size-1 || j==size-1){             //border z "0"
                    map[i][j] = "0";
                }
                else if(chance1 < forestation) {
                    map[i][j] = "T";
                }
                else {
                    map[i][j] = "X";
                }
            }
        }
        print_map();
    }

    public void fire_initialization() {
        for(int i=0; i<size; i++)
        {
            if(map[1][i].equals("T")){
                map[1][i] = "B";
                cordB.add(new Cords(1, i));         //dodawanie podpalonych drzew do listy obiektów
            }
        }
       print_map();
    }

    public void checkArea(int x, int y)
    {
        int tempCount = 0;
        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                if(map[x + i][y + j].equals("T")) {
                    spreadFire(x + i, y + j);
                }
                else{
                    tempCount =  tempCount + 1;
                }
            }
        }
        if(tempCount == 9){
            count = count + 1;
        }
    }

    public void spreadFire(int x, int y)
    {
        double chance2 = rand.nextDouble();
        if(chance2 < 0.5){                                     //50% na podpalenie sie drzewa

            cordB.add(new Cords(x, y));                         //dodawanie podpalonych drzew do listy obiektów
            map[x][y] = "B";
        }
    }

    public void print_map() {
        System.out.print("\033[H\033[2J");  	    //Czyszczenie konsoli
        System.out.flush();

        for(int i=1; i<size-1; i++)                 //od 1 do -1 żeby wyświetlić bez bordera
        {
            for(int j=1; j<size-1; j++)
            {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        try{									    //Delay miedzy wyswietlaniem
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }


    public void make_simulation() {
        int roz = cordB.size();
        for(int i=0; i<roz; i++){
            checkArea(cordB.get(i).getX(), cordB.get(i).getY());
        }
        print_map();
        if(!(roz == count)){
            count = 0;
            make_simulation();
        }
    }

    public static void main(String[] args) {
            ForestP forest = new ForestP(10, 0.5);         //rozmiar i forestation
            forest.map_initialization();
            forest.fire_initialization();
            forest.make_simulation();
    }
}

