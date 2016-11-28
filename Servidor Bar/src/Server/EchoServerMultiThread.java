package Server;

import java.net.*;
import java.io.*;

public class EchoServerMultiThread implements Runnable{

	Socket ss, ts;		//socket de receber mensagens e enviar mensagens para taxi server
	boolean sair;		//boolean para sair do loop principal
	boolean jaPediuTaxi;//boolean para nao pedir 300 taxis
	String resposta;	//resposta do servidor de taxi
	
	public EchoServerMultiThread(Socket ns, Socket ts){
		ss = ns;
		this.ts = ts;
		sair = false;
		jaPediuTaxi = false;
	}

	public void run() {
		//while(true){
		try (	
				BufferedReader 		stdIn 	= new BufferedReader(new InputStreamReader(System.in));		//para ler o que vem do servidor de taxi
				DataOutputStream 	outTaxi	= new DataOutputStream(ts.getOutputStream());
				DataInputStream 	inTaxi 	= new DataInputStream(ts.getInputStream());					
				DataOutputStream 	out 	= new DataOutputStream(ss.getOutputStream());				//para lero que vem do cliente
				DataInputStream 	in 		= new DataInputStream(ss.getInputStream());            
				) {
			//System.out.println("Foi!");

			while (true) {             	
				String inputLine = in.readUTF();  		//le o que vem do cliente
				switch (inputLine)						//trata o que vem do cliente
				{
				case "menu":
					out.writeUTF("Martini: R$10\nToba: R$15");   
					break;					
					
				case "taxi":							//envia "request" e retorna ao cliente					
					if(!jaPediuTaxi)
					{
						outTaxi.writeUTF("request");
						resposta = inTaxi.readUTF();
						out.writeUTF(resposta);
						if(resposta.equals("taxi indo"))
						{
							jaPediuTaxi = true; //se taxi disponivel, nao pode pedir outro
						}
					}
					else													//se ja pediu e conseguiu, espere, mzr
					{
						out.writeUTF("Você já pediu um taxi, você está bebado!");
					}
					break;				
				
				case "bye": 
					sair = true;
					break;
				default:
					out.writeUTF("Mensagem inválida"); 
					break;	
				} 
				
				if (sair)
				{
					break;
				}
			}
			
			System.out.println("cliente saiu!");
			
			inTaxi.close();
			outTaxi.close();
			in.close();
			out.close();
			ss.close();                     

		} catch (IOException e) {
			System.out.println("cliente cagou-se!");
		}
	}
	//}
}
