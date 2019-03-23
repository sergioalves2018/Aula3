import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChatAula extends UnicastRemoteObject implements IChatAula{
	
	private static final long serialVersionUID = 412321221L;
	
	private Connection conn;

	public ChatAula(Connection conn) throws RemoteException{
		super();
		this.conn = conn;
	}

	@Override
	public void sendMessage(Message msg) throws RemoteException {
		Message.addLstMessage(msg);
		insereBanco(msg);
		insereArquivo(msg);
	}

	@Override
	public List<Message> retriveMessage() throws RemoteException {
		return Message.getLstMessage();
	}
	
	private void insereBanco(Message msg) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("insert into mensagens values (?, ?);");
			ps.setString(1, msg.getUsuario());
			ps.setString(2, msg.getMessage());
			ps.execute();
		} catch (Exception e) {
			System.err.println("Erro ao inserir no banco");
			e.printStackTrace();
		}
	}
	
	private void insereArquivo(Message msg) {
		FileWriter fileWriter;
		PrintWriter writer;
		try {
			fileWriter = new FileWriter("mensagens.txt", true);
			writer = new PrintWriter(fileWriter);
			writer.println(msg.getUsuario() + ": \"" + msg.getMessage()+"\"");
			writer.close();
		} catch (Exception e) {
			System.err.println("Erro ao inserir no arquivo");
		}
	}

}
