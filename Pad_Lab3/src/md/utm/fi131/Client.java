package md.utm.fi131;
import sun.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
	
		String serverName = "localhost";
		Tcp tcp=new Tcp();
		Socket soket = new Socket(serverName, 5551);
		Message mes = new Message("command", "get", "all");
		Gson gson = new Gson();
		String jmes = gson.toJson(mes);
		tcp.tcpSend(jmes, soket);
		String res=tcp.tcpReceive(soket);
		System.out.println("tcp resp "+res+"\n");
			
	}
	
	
}
