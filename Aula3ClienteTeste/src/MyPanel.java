import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;
import javax.swing.event.*;

public class MyPanel extends JPanel  implements ActionListener, KeyListener {
	private JButton bt;
    private JTextField msg;
    private JTextArea tela;
    private IChatAula chat;

    public MyPanel(String nome) throws Exception {
        //construct components
        bt = new JButton ("Enviar");
        msg = new JTextField (5);
        tela = new JTextArea (5, 5);

        //set components properties
        tela.setEnabled (false);

        //adjust size and set layout
        setPreferredSize (new Dimension (416, 288));
        setLayout (null);

        //add components
        add (bt);
        add (msg);
        add (tela);

        //set component bounds (only needed by Absolute Positioning)
        bt.setBounds (315, 250, 85, 25);
        msg.setBounds (15, 250, 290, 25);
        tela.setBounds (15, 15, 385, 225);
        
        chat = (IChatAula) Naming.lookup("rmi://localhost:8282/chat");
		        
        bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = msg.getText();
				String mensagens = "";
				msg.setText("");
				try {
					chat.sendMessage(new Message(nome, texto));
					for(Message m : chat.retriveMessage()) {
						mensagens += "\n"+m.getUsuario()+": "+m.getMessage();
					}
					tela.setText(mensagens);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			
			
			
		});
    }
    

    public static void main (String[] args) throws Exception {
    	String nome = JOptionPane.showInputDialog("Digite seu Nome:");
		if(nome == null || nome.equals("")){
			JOptionPane.showMessageDialog(null, "Cancelado", "Chat Info", 0);	
		}else {
			JFrame frame = new JFrame ("Chat" + nome);
	        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().add (new MyPanel(nome));
	        frame.pack();
	        frame.setVisible (true);
		}
    }

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
	        System.out.println("Hello");
	    }
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}