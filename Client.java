
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Client {
    private JTextField message_box;
    private JTextArea chat_area;
    private JFrame window;
    private JScrollPane chat_scroller;
    private JButton send_button;
    Client() {
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

        EnviaTexto mievento = new EnviaTexto();
        send_button = new JButton("Send");
        send_button.setBounds(580, 450, 100, 50);
        send_button.addActionListener(mievento);

        message_box = new JTextField();
        message_box.setBounds(80, 450, 480, 50);
        message_box.addActionListener(mievento);

        window.add(message_box);
        window.add(chat_scroller);
        window.add(send_button);

        window.setVisible(true);
    }

    private class EnviaTexto implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            Socket socket_cliente= new Socket("127.0.0.1",1234);
            DataOutputStream flujo_salida = new DataOutputStream(socket_cliente.getOutputStream()); //este flujo de datos circula por el socket
            flujo_salida.writeUTF(message_box.getText());// escribe en el flujo de datos lo que hay en message_box
            flujo_salida.close();
            chat_area.append("\n" + message_box.getText());
            message_box.setText("");
            chat_area.setCaretPosition(chat_area.getDocument().getLength());;

        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println(e1.getMessage());
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client());
    }

}


