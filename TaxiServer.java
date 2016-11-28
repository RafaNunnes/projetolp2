/**************************************************************************************************************************
 * Servidor dos Taxis por Gabriel Fleig
 * 
 * O servidor deve ser aberto antes de qualquer coisa
 * O servidor inicia o socket para comunicacao com o servidor do bar
 * Inicia o buffer e o produtor, que gera um taxi a cada 5 segundos
 * se o servidor receber "request" via UTF, checa se ha taxi no buffer e "manda" o primeiro da fila (fetch no buffer)
 * O servidor roda pra sempre
 * 
 **************************************************************************************************************************/

import java.net.*;

import java.io.*;

public class TaxiServer {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {        
        
    	//variaveis do meme
        int portNumber = 4440;
        
        //variaveis dos taxis
        int numTaxis = 10;
        
        System.out.println("Servidor aberto. Esperando servidor do bar.");
        
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);        		
            Socket clientSocket = serverSocket.accept();          
        	DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        	DataInputStream in = new DataInputStream(clientSocket.getInputStream());            
        ) {
            
        	System.out.println("Servidor do bar chegou!\nSetando Taxis");
        	TaxiBuffer b = new TaxiBuffer(numTaxis);
        	TaxiGenerator p = new TaxiGenerator(b, "Taxi");		// a cada 5 segundos, um novo taxi tenta chegar no buffer circular   	
        	
    		p.start();													//inicia o produtor    		
    		
            while (true) {             	
            	String inputLine = in.readUTF();                               
                switch (inputLine)
                {
	                case "request":
	                	if(!b.vazia())									//checa se o buffer dos taxis esta vazio
	                	{
		                	b.fetch();									//se nao, tira um e avisa 
		                	out.writeUTF("taxi indo"); 
		                	break;
	                	}
	                	else											//se tiver sem taxi, avisa
	                	{
	                		out.writeUTF("nao temos taxis, tente novamente mais tarde"); 
	                		System.out.println("=>Tentativa falha de pedir taxi");
		                	break;
	                	}	  
	                default:
	                	System.out.println("Mensagem recebida invalida");
	                	out.writeUTF("Mensagem invalida"); 
	                	break;
                }                              
            }           
                       
        // se der merda no servidor    
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
