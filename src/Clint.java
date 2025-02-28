import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Clint  extends JFrame implements Runnable, ActionListener {
    TextField textField ;
    TextArea textArea ;
    Button button ;
    Socket socket ;
    DataOutputStream dout ;
    DataInputStream din ;
    Thread chat ;

    Clint() throws IOException {
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

        socket = new Socket("localhost",999);

        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());

        add(textField);
        add(textArea);
        add(button);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();





    }

    public static void main(String[] args) throws IOException {
       new Clint();



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = textField.getText();
        textArea.append( "Clint : "+ s + "\n");
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
                textArea.append("Irfan : " + text+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }
}