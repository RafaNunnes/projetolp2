import java.io.*;
import java.net.*;

public class EchoClient {
	public static void main(String[] args) throws IOException {    	

		//variaveis do meme
		String hostName = "localhost"; //= args[0];
		int portNumber = 4444; //Integer.parseInt(args[1]);

		try (
				Socket echoSocket = new Socket(hostName, portNumber);            
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				DataInputStream in = new DataInputStream(echoSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(echoSocket.getOutputStream());
				) {        	         

			while (true) {
				String userInput = stdIn.readLine();
				out.writeUTF(userInput);                   
				System.out.println(in.readUTF());                
				if(userInput.equals("bye") || userInput.equals("kys")) break;
			}

			in.close();
			out.close();
			echoSocket.close();            

			//se der merda    
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					hostName);
			System.exit(1);
		} 
	}
}
