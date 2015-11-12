package Data;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketConnection  {

    // creo mi socket llamado clientsocket
    private Socket clientSocket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    //metodo que me abre el socket y abre el input y el output
    public boolean openSocket()
    {
        boolean flag = false;
        try {
            clientSocket = new Socket("linuxbox",88);
            dataOutput = new DataOutputStream(clientSocket.getOutputStream());
            dataInput = new DataInputStream(clientSocket.getInputStream());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    // metodo que me manda el request al servidor y al recibir datos cierra el socket y el I/O stream
    public void sendrequest(){

        if(openSocket()) {
            try{
                dataOutput.writeBytes("nombre de la cancion");
                String responseLine;
                while ((responseLine = dataInput.readLine()) != null){
                    System.out.println("server" + responseLine);
                    if(responseLine.indexOf("OK")!= -1)
                        break;
                }
                dataInput.close();
                dataOutput.close();
                clientSocket.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

}
