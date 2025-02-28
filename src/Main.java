import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main  extends JFrame implements Runnable, ActionListener {
    TextField textField ;
    TextArea textArea ;
    Button button ;
    ServerSocket serverSocket ;
    Socket socket ;
    DataOutputStream dout ;
    DataInputStream din ;
    Thread chat ;

    Main() throws IOException {
        textField = new TextField() ;
        textArea = new TextArea() ;
        button = new Button("Send") ;
        button.addActionListener(this);

        textField.setBounds(10,10,460,20);

        textArea.setBounds(10,50,470,300);

        button.setBounds(200,350,100,30);

        setLayout(null) ;
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(textField);
        add(textArea);
        add(button);

        serverSocket = new ServerSocket(999);
        socket = serverSocket.accept();

        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();








    }

    public static void main(String[] args) throws IOException {
        new Main();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = textField.getText();
        textArea.append("Irfan :"+ s +  "\n");
        textField.setText("");
        try {
            dout.writeUTF(s);
            dout.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    @Override
    public void run() {
        while(true){
            try {
                String text = din.readUTF();
                textArea.append("Clint : " + text+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }
}