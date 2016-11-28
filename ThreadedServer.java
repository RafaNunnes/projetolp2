/************************************************************************************************
 * Servidor do bar by Gabriel Fleig
 * 
 * O servidor deve ser iniciado depois do servidor de taxi
 * cria socket para se comunicar com o servidor do taxi e outro para comunicar com os clientes
 * Espera pelo menos um cliente entrar
 * Se Todos clientes sairem, ninguem mais entra <===========MERDA MERDA MERDA
 * 
 *************************************************************************************************/

import java.net.*;
import java.io.*;

public class ThreadedServer {

	public static void main(String[] args) {		
		System.out.println("Bem-vindo ao servidor do bar!!");
		
		try (
				ServerSocket s = new ServerSocket(4444);			//socket para receber mensagens do usuario
				Socket	 	ts = new Socket("localhost", 4440);		//socket para mandar mensagens para o servidor de taxi
				){
			
			System.out.println("Servidor aberto!! Esperando Clientes");			
			
			while(true){
				Socket ns = s.accept();
				new Thread(new EchoServerMultiThread(ns, ts)).start();
				System.out.println("Novo cliente chegou!");
			}
			//s.close();

		} catch (IOException e) { 
			System.out.println("Servidor do taxi nao encontrado... fechando.");
		}
	}
}
