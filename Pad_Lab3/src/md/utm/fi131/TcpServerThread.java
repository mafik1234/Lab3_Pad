package md.utm.fi131;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

;

public class TcpServerThread extends Thread {

	protected Socket server;
	protected String topName;

	Tcp tcp = new Tcp();
	public String workers;
	private Nodes thisNode;

	public TcpServerThread(Socket clientSocket, Nodes thisNode) {
		this.server = clientSocket;

		this.thisNode = thisNode;

	}

	public void run() {

		try {

			workers = new String(Files.readAllBytes(Paths.get("col" + thisNode.getId() + ".json")));
			System.out.println(workers);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		try {

			DataInputStream in = new DataInputStream(server.getInputStream());
			String input = in.readUTF();

			String message = getAttribute("message", input);

			if (message.equals("all")) {

				tcp.tcpSend(workers, server);
			}

		} catch (IOException e) {
			System.out.println("Client deconectat With Ctrl+C");

			return;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getAttribute(String key, String input) throws JSONException {
		JSONObject jObject = new JSONObject(input);
		String command = jObject.getString(key);
		return command;
	}

}
