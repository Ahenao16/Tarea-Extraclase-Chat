import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Random;

/** 
 * Esta clase es la que se encarga de crear la interfaz gráfica y de iniciar el codigo del cliente.
 */


public class Client implements Runnable {
    private JTextField message_box, ip_box,name_box;
    private JTextArea chat_area;
    private JFrame window;
    private JScrollPane chat_scroller;
    private JButton send_button;
    private JLabel ip_label,name_label;
    private String ip;
    /**
     * Este le da los parametros que queremos a la para la interfaz y le establecemos las cajas de mensaje, nombre, envio y la caja donde va aparecer el texto del cliente.
     */
    Client() {
        window = new JFrame();
        window.setSize(800, 650);
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
       

        EnviaTexto mievento = new EnviaTexto();
        send_button = new JButton("Send");
        send_button.setBounds(580, 450, 100, 50);
        send_button.addActionListener(mievento);

        message_box = new JTextField();
        message_box.setBounds(80, 450, 480, 50);
        message_box.addActionListener(mievento);

        name_box = new JTextField();
        name_box.setBounds(530, 520, 200, 50);
        name_box.addActionListener(mievento);

        chat_area = new JTextArea("Bienvenido, introdusca ip y su nombre", 1, 1);
        chat_area.setLineWrap(true);
        chat_area.setEditable(false);

        chat_scroller = new JScrollPane(chat_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chat_scroller.setBounds(80, 20, 600, 400);
        
        window.add(message_box);
        window.add(chat_scroller);
        window.add(send_button);
        window.add(chat_scroller);
        window.add(name_label);
        window.add(name_box);


        window.setVisible(true);
        
        

        Thread hilo= new Thread(this);
        hilo.start();
    }
    /**
     * Esta clase es la que se encarga de enviar el texto que se encuentra en la caja de texto del cliente asi el servidor se lo envia alos demas clietnes.
     * @author Alejandro Henao
     * @version 1.0
     */

    private class EnviaTexto implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        
        try {
            ip=ip_box.getText();
            Socket socket_cliente= new Socket(ip,1234);
            DataOutputStream flujo_salida = new DataOutputStream(socket_cliente.getOutputStream()); //este flujo de datos circula por el socket
            flujo_salida.writeUTF(name_box.getText()+":"+ message_box.getText());// escribe en el flujo de datos lo que hay en message_box
            flujo_salida.close();

            socket_cliente.close();

        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            chat_area.append("\n Introduzca una dirección ip válida");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            chat_area.append("\n Verifique que la dirección ip sea la correcta");
        }
    }
}
    /**
     * Esta  es la parte que se encarga de crear la interfaz gráfica y de iniciar el codigo del servidor.
     * @autor: Jorge gutierrez Y Alejandro Henao
     * @category: Constructor'
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client());
    }

    /**
     * Este es el metodo que utlilizamos para recibir los mensajes de los demas usuarios y mostralo en la caja de texto del cliente, todo mediante un port aleatorio.
     * @autor: Jorge gutierrez 
     * @category: Metodo
     */
    public void run() {
        // TODO Auto-generated method stub
        //System.out.println("El hilo funciona");

        try {

            Random random = new Random();
            int nump =  random.nextInt(5000) + 5000;
            ServerSocket server =new ServerSocket(nump);
            String numM = String.valueOf("0"+nump);
            Socket Socketcli = new Socket("127.0.0.1",1234);
            DataOutputStream out = new DataOutputStream(Socketcli.getOutputStream());
            out.writeUTF( numM);
            Socketcli.close();
            while(true){
            Socket Serbersock =  server.accept();
            DataInputStream info = new DataInputStream(Serbersock.getInputStream());
            String mesj = info.readUTF();
            chat_area.append("\n"+mesj);
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


