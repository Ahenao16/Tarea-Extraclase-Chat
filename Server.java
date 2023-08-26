import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Objects;
/** 
 * Esta clase es la que se encarga de crear la interfaz gráfica y de iniciar el codigo del servidor.
 */
public class Server implements Runnable {
    private JTextField ip_box;
    private JTextArea chat_area;
    private JFrame window;
    private JScrollPane chat_scroller;
    private JLabel ip_label,name_label;



    /**
     * Este le da los parametros que queremos a la para la interfaz y le establecemos las cajas de mensajes del servidor.
     * @autor: Jorge gutierrez Y Alejandro Henao
     */
    Server() {
        window = new JFrame();
        window.setSize(800, 550);
        window.setTitle("Chat Box");
        window.setLayout(null);
        window.setResizable(false);



        chat_scroller = new JScrollPane(chat_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chat_scroller.setBounds(80, 20, 600, 400);

        ip_label= new JLabel("Coloque la dirección ip del destinatario:");
        ip_label.setBounds(20, 520, 300, 50);
        
        ip_box = new JTextField();
        ip_box.setBounds(260, 520, 200, 50);

        name_label= new JLabel("Nombre:");
        name_label.setBounds(475, 520, 300, 50);
       



        chat_area = new JTextArea("Bienvenido, introdusca ip y su nombre", 1, 1);
        chat_area.setLineWrap(true);
        chat_area.setEditable(false);

        chat_scroller = new JScrollPane(chat_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chat_scroller.setBounds(80, 20, 600, 400);
        
        window.add(chat_scroller);
        window.add(chat_scroller);


        window.setVisible(true);
        
        

        Thread hilo= new Thread(this);
        hilo.start();
    }   


    /**
     * Esta  es la parte que se encarga de crear la interfaz gráfica y de iniciar el codigo del servidor.
     * @autor: Jorge gutierrez Y Alejandro Henao
     * @category: Constructor'
     */
    public static void main(String[] args) {
    new Server();

    }
    @Override
    /**
     * Este metodo es el que se encarga de recibir los mensajes y de enviarlos a los demas usuarios.
     * @autor: Jorge gutierrez Y Alejandro Henao   
     * @category: Metodo
     */
    public void run() {
        LinkedList<Integer> List = new LinkedList<Integer>();
        try {
            

            ServerSocket server =new ServerSocket(1234);
            while(true){
                Socket Serversock =  server.accept();
                DataInputStream Data = new DataInputStream(Serversock.getInputStream());
                String mesj = Data.readUTF();
                if(Objects.equals(String.valueOf(mesj.charAt(0)), "0")){
                mesj = mesj.substring(1, mesj.length() );
                int PortF = Integer.parseInt(mesj);
                List.add(PortF);
                chat_area.append("\n"+"NEW USER");
                    
                }
                else{   
                Socket msport = null;
                for (int i = 0; i < List.size(); i++) {

                System.out.println(List.get(i));
                msport = new Socket("127.0.0.1",List.get(i));
                DataOutputStream afuera = new DataOutputStream(msport.getOutputStream());
                afuera.writeUTF(mesj);
                msport.close();
                             
                            }
                chat_area.append("\n"+mesj);
                    
                    
                }
                
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}





