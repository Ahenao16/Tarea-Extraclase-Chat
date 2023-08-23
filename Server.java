import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Server implements Runnable {
    private JTextField message_box, ip_box,name_box;
    private JTextArea chat_area;
    private JFrame window;
    private JScrollPane chat_scroller;
    private JButton send_button;
    private JLabel ip_label,name_label;
    private String ip;
    Server() {
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

        window.add(ip_label);
        window.add(ip_box);
        window.setVisible(true);
        
        

        Thread hilo= new Thread(this);
        hilo.start();
    }

        private class EnviaTexto implements ActionListener {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            
        try {
            ip=ip_box.getText();
            Socket socket_cliente= new Socket(ip,12345);
            DataOutputStream flujo_salida = new DataOutputStream(socket_cliente.getOutputStream()); //este flujo de datos circula por el socket
            flujo_salida.writeUTF(name_box.getText()+":"+ message_box.getText());// escribe en el flujo de datos lo que hay en message_box
            flujo_salida.close();
            chat_area.append("\n" + name_box.getText()+":"+ message_box.getText());
            message_box.setText("");
            chat_area.setCaretPosition(chat_area.getDocument().getLength());;
            socket_cliente.close();

        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            chat_area.append("\n Introduzca una dirección ip válida");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            chat_area.append("\n Verifique que la dirección ip sea la correcta");
        }
    }

}
    public static void main(String[] args) {
    new Server();

    }
    @Override
    public void run() {

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





