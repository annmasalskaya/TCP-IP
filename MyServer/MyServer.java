import Game.TableServer;
import  java.io.*;
import java.net.*;
import java.util.Scanner;

 public class MyServer
    {
        public static void main(String[] args) throws IOException
        {
            Socket socket=null;
            Scanner scanner=new Scanner(System.in);
            TableServer table=new TableServer();
            try {
                ServerSocket server;
                InetAddress IP = InetAddress.getLocalHost();
                System.out.println(IP);
                server = new ServerSocket(8080);
                socket=server.accept();
                PrintStream ps=new PrintStream(socket.getOutputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String x, y,msg;
                System.out.println("Your field is: ");
                table.ouputField(1);

                while(true)
                {
                    System.out.println("Now is your move: ");
                    x=scanner.nextLine();
                    y=scanner.nextLine();
                    ps.println(x);
                    ps.println(y);
                    msg=br.readLine();
                    System.out.println(msg);
                    if(msg.equals("The ship is killed!") || msg.equals("Hurted."))  table.mark(Integer.parseInt(x), Integer.parseInt(y),true);
                    else table.mark(Integer.parseInt(x), Integer.parseInt(y),false);
                    msg=br.readLine();

                    if(msg.equals("Exit"))
                    {
                        ps.println("Exit");
                        System.out.println("Game Over. You are winner! ");
                        table.ouputField(2);
                        break;
                    }
                    table.ouputField(2);
                    System.out.println("Now is somebody's move. ");
                    System.out.println();
                    x=br.readLine();
                    y=br.readLine();
                    ps.println(table.stepCheck(Integer.parseInt(x), Integer.parseInt(y)));
                    if(table.allKilled())
                    {
                        ps.println("Exit");
                        ps.flush();
                        System.out.println("Game Over. You are loser! ");
                        table.ouputField(2);
                        break;
                    }
               }
                socket.close();
                ps.close();
            }
            catch (IOException e){
               System.out.println("error"+e);

            }
            finally {
                if(socket!=null)
                    socket.close();
            }
        }
    }
