import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChatServer {
	
	public ChatServer() throws RemoteException{
		try {
			String url ="jdbc:sqlserver://DESKTOP-BK1E1MM\\MSSQLServer;databaseName=chat";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url, "sa", "_43690");
			
			LocateRegistry.createRegistry(8282);
			Naming.rebind("rmi://localhost:8282/chat", new ChatAula(conn));
			System.out.println("Rodando");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException {
		new ChatServer();
	}

}
