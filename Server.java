import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Server implements Runnable {
    private JTextArea chat_area;
    private JFrame window;
    private JScrollPane chat_scroller;
   
    Server() {
        window = new JFrame();
        window.setSize(800, 600);
        window.setTitle("Chat Box");
        window.setLayout(null);
        window.setResizable(false);

        chat_area = new JTextArea("This is the chat box", 1, 1);
        chat_area.setLineWrap(true);
        chat_area.setEditable(false);

        chat_scroller = new JScrollPane(chat_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chat_scroller.setBounds(80, 20, 600, 400);
        
        window.add(chat_scroller);
        window.setVisible(true);

        Thread hilo= new Thread(this);
        hilo.start();
    }
    public static void main(String[] args) {
    new Server();

    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        //System.out.println("El hilo funciona");

        try {
            ServerSocket socket_servidor= new ServerSocket(1234);

            while(true){
            Socket misocket= socket_servidor.accept();
            DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
            String mensaje_texto= flujo_entrada.readUTF();
            chat_area.append("\n"+mensaje_texto);
            misocket.close();  //se debe crear un loop para volver a abrir el socket
            chat_area.setCaretPosition(chat_area.getDocument().getLength());;
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}





