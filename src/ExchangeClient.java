/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author atamarkin2
 */
public class ExchangeClient {

	private Socket socket;
	private PrintWriter pout;
	private BufferedReader bin;
	
	public ExchangeClient(){}
			
    public ExchangeClient(String[] args) throws IOException {
    	socket = new Socket(args[0], Integer.parseInt(args[1]));
    	pout = new PrintWriter(socket.getOutputStream());
        bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pout.println(args[2] + " " + args[3]);
        pout.flush();
    }
    
    public void close() throws IOException {
    	pout.println("CLOSE_CONNECTION");
    	pout.close();
        bin.close();
        socket.close();
    }
	
	/**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public String call(String[] args) throws IOException {
    	
        for (int i = 4; i < args.length; i++) {
            pout.println(args[i]);
        }
        
        pout.flush();
        String line;
        line = bin.readLine();   
                
        return line;
    }

}
