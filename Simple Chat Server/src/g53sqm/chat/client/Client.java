package g53sqm.chat.client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple Swing-based client for the chat server. It also use JFC.  In fact,
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole conversation.
 *
 */
public class Client implements Runnable{

    BufferedReader in;
    PrintWriter out;
    public JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);

    private String serverAddress;
    private int serverPort;
    private String[] commands;
    static String message = null;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Textfield
     * edit when client get information with "NAMEACCEPT" from server.
     */
    public Client(int serverPort, String serverAddress, String[] commands, String clientName) {

    	this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.commands = commands;
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

     // Add Listeners
        textField.addActionListener(new ActionListener() {

			/**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server. Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
            	String text = textField.getText();
                out.println("HAIL " + text);
                textField.setText("");
            }
        });
    }

    /**
     * Connects to the server then enters the processing loop.
     */
	public void run(){

        Socket clientSocket = null;
        Random rand = new Random();
        String line = null;

        //connect and initialize in & out
    	try {
    	      clientSocket = new Socket(this.serverAddress, this.serverPort);
    	      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	      out = new PrintWriter(clientSocket.getOutputStream(), true);
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    }
    	for (String command: commands) {
    	      out.println(command);

    	      try {
    	        Thread.sleep(rand.nextInt(1000));
    	      } catch (InterruptedException e) {
    	        e.printStackTrace();
    	      }
    	    }

        // Process all messages from server, according to the protocol.
        while (true) {

        	try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(line == null) {
				return;
			}

            if (line.startsWith("NAMEACCEPT")) {
                textField.setEditable(true);

            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            } else if (line.startsWith("PASSED")) { //Test for passed situation for server
            	setMessage(line.substring(7));
            } else if (line.startsWith("FAILED")) {
            	setMessage(line.substring(7));
            }
        }
    }

	private void setMessage(String line){
		message = line;
	}
	public String getMessage(){
		return message;
	}

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {

    	ExecutorService executor = Executors.newCachedThreadPool();
    	String[] client2Commands = {"IDEN tom", "LIST", "HAIL Hello from client 1"}; // add more commands in here
	    String[] testMesg = {"IDEN porden", "HAIL Hello from client 2", "MESG SQM is good"}; //test MESG


    	Client client = new Client(9000, "localhost", client2Commands, "client1");

        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        executor.execute(client);


        Client client2 = new Client(9000, "localhost", testMesg, "client2");

        client2.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client2.frame.setVisible(true);
        executor.execute(client2);

        try {
  	      executor.awaitTermination(1, TimeUnit.MINUTES);
  	    } catch(InterruptedException e){
  	      e.printStackTrace();
  	    }
    }
}


