import Game.TableServer;

import  java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args)
    {
        Socket socket=null;
        TableServer table=new TableServer();
        Scanner scanner=new Scanner(System.in);
        byte input[] = {(byte)10, (byte)175, (byte)6, (byte)98};

        try {
            socket=new Socket( InetAddress.getByAddress(input),  8080 );
            PrintStream ps=new PrintStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String x, y,msg;
            System.out.println("Your field is: ");
            table.ouputField(1);

            while(true)
            {
                System.out.println("Now is somebody's move.");
                x=br.readLine();
                y=br.readLine();
                ps.println(table.stepCheck(Integer.parseInt(x), Integer.parseInt(y)));
                System.out.flush();
                if(table.allKilled())
                {
                    ps.println("Exit");
                    ps.flush();
                    System.out.println("Game Over. You are loser! ");
                    table.ouputField(2);
                    break;
                }
                System.out.println("Now is your move: ");
                x=scanner.nextLine();
                y=scanner.nextLine();
                ps.println(x);
                ps.println(y);
                ps.flush();
                msg=br.readLine();
                System.out.println(msg);
                if(msg.equals("The ship is killed!") || msg.equals("Hurted."))  table.mark(Integer.parseInt(x), Integer.parseInt(y),true);
                else table.mark(Integer.parseInt(x), Integer.parseInt(y),false);
                msg=br.readLine();
                if(msg.equals("Exit"))
                {
                    System.out.println("Game Over. You are winner! ");
                    table.ouputField(2);
                    break;
                }
                table.ouputField(2);
            }

            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}