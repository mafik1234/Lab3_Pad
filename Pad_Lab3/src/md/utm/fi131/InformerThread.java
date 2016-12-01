package md.utm.fi131;

import sun.net.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class InformerThread extends Thread {
	int thread;
	static int port = 5551;
	static Tcp tcp = new Tcp();

	String configFile;
	Nodes thisNode;
	public ArrayList<Nodes> listNodes;
	

	public InformerThread(String configFile) {

		this.configFile = configFile;
		
	}

	public InformerThread(int i) {
		this.thread = i;
	}

	public void run() {
		
		new TcpServer(thisNode).start();
		

	}



}
