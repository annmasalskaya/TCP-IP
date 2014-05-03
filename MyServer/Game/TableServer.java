package Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TableServer
{
    private int[][] table;
    private char[][] markedMoves;
    private int size;

    public TableServer()
    {
        size=10;
        table= new int[size][size];
        markedMoves=new char[size][size];
        try
        {
            File file = new File("field.txt");
            Scanner scanner = new Scanner(file);
            for(int i=0; i<size; i++)
                for(int j=0; j< size; j++)
                    table[i][j]=Integer.parseInt(scanner.next());

            while (scanner.hasNext())
            {
                scanner.next();
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File didn't open");
            System.exit(0);
        }
    }

    public String stepCheck(int i, int j)
    {
        if(table[i][j]==1)
        {
            table[i][j]=5;
            return "The ship is killed!";
        }
        else if( table[i][j]!=0 && table[i][j]!=5 )
        {
            int value=table[i][j],counter=0;
            table[i][j]=5;
            for(int k=0; k< 10 ; k++)
                for(int r=0; r<10 ; r++)
                    if(table[k][r]==value ) counter++;

            if( counter!=0) return "Hurted.";
            else if( counter==0)  return "The ship is killed!";
        }
        return "Move is missed.";
    }

    public void mark(int i, int j,boolean flag)
    {
        if(flag) markedMoves[i][j]='#';
        else  markedMoves[i][j]='-';
    }

    public boolean allKilled()
    {
        for(int i=0; i<size; i++)
        {
            for(int j=0; j< size; j++)
            {
                if(table[i][j]!=0 && table[i][j]!=5) return false;
                else continue;
            }
        }
        return true;
    }
    public void ouputField(int chooseOutputTable)
    {

        System.out.print("_");
        for (int i=0; i<size-1; ++i)
            System.out.print("____");

        System.out.println("____");
        for(int i=0; i<size; ++i)
        {
            for(int j=0; j<size; ++j)
            {
                if(chooseOutputTable==1)  System.out.print("|"+" "+ table[i][j]  +" ");
                else System.out.print("|"+" "+ markedMoves[i][j] +" ");
            }
            System.out.println("|");
            if(i==size-1)
            {
                System.out.print("____");
                for(int j=0; j<size-1; ++j)
                    System.out.print("____");

                System.out.println("_");
            }
            else
            {
                System.out.print("____");
                for(int j=0; j<size-1; ++j)
                    System.out.print("____");

                System.out.println("_");
            }
        }
    }

}