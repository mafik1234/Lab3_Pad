package md.utm.fi131;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Mediator {
	static ArrayList<Nodes> listNodes = new ArrayList<Nodes>();


	static int port = 5551;
	static Tcp tcp = new Tcp();
	static Gson gson = new Gson();

	public static void main(String[] args)
			throws UnknownHostException, IOException, InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, InvocationTargetException, JSONException {
		 listNodes = XmlTool.getNodeList("mediator.xml");
		Gson gson = new Gson();
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		while(true) {
		//ServerSocket serverSocket = new ServerSocket(port);
		Socket server = serverSocket.accept();
		
		//try {
		//	ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client on port " + port + "...");
			
		//	Socket server = serverSocket.accept();
				String clientMessage = tcp.tcpReceive(server);
				System.out.println("client message " + clientMessage);
			
				String jmes = gson.toJson(getAll());
				tcp.tcpSend(jmes, server);
				System.out.println(jmes);
		}
		
		} 
		//catch (IOException e) 
		{

		//	e.printStackTrace();
		
	}
//	}

	

	
	public static ArrayList<Worker> getAll() {
		ArrayList<Worker> listResp = new ArrayList<Worker>();

		for (Nodes node : listNodes) {
			Socket soket;
			try {
				soket = new Socket(node.getNodeIp(), node.getTcpPort());
				Message mes = new Message("command", "get", "all");
				String jmes = gson.toJson(mes);
				tcp.tcpSend(jmes, soket);
				String colection = tcp.tcpReceive(soket);
				listResp.addAll(jsonToList(colection));
				soket.close();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

		

		return listResp;
	}
	public static ArrayList<Worker> jsonToList(String json) {
		java.lang.reflect.Type type = new TypeToken<List<Worker>>() {
		}.getType();

		ArrayList<Worker> workers = gson.fromJson(json, (java.lang.reflect.Type) type);

		return workers;

	}
	

	

	

	
}