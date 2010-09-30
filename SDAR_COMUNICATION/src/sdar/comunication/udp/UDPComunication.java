package sdar.comunication.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import sdar.comunication.def.CommunicationEspecification;

public class UDPComunication {

	
	/**
	 * 
	 * @param address
	 * @param port
	 * @param msg
	 * @throws IOException
	 */
	public void send(String address, int port, byte[] msg) throws IOException {
		//TODO msg pode mudar de tipo desde que implemente serializable
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(address);
		
		System.out.println(msg.length);
		
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, port);
		clientSocket.send(sendPacket);

		clientSocket.close();
	}
	
	
	
//	/**
//	 * 
//	 * @param group
//	 * @param port
//	 * @param msg
//	 * @throws IOException
//	 */
//	public void sendGroup(String group, int port, byte[] msg) throws IOException {
//		//TODO msg pode mudar de tipo desde que implemente serializable
//
//		InetAddress groupAddress = InetAddress.getByName(group);
//	
//		MulticastSocket multicastSocket = new MulticastSocket(port);
//		multicastSocket.joinGroup(groupAddress);
//		
//		System.out.println(msg.length);
//		
//		DatagramPacket message = new DatagramPacket(msg, msg.length, groupAddress, port);
//
//		multicastSocket.send(message);
//
//		multicastSocket.leaveGroup(groupAddress);
//	}
	
	
	
	public byte[] readGroup(String group, int port) throws IOException {
		
		InetAddress groupAddress = InetAddress.getByName(group);
		
		MulticastSocket multicastSocket = new MulticastSocket(port);
		multicastSocket.joinGroup(groupAddress);
		
		byte[] receiveData = new byte[CommunicationEspecification.BUFFER_SIZE];
		
        DatagramPacket receivedMessage= new DatagramPacket(receiveData, receiveData.length);

        multicastSocket.receive(receivedMessage);

        receiveData = receivedMessage.getData();
        
		System.out.println(receiveData.length);
        
        multicastSocket.leaveGroup(groupAddress);
        
        return receiveData;
	
	}
	

	
	/**
	 * 
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public byte[] read(int port) throws IOException {
		
		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[CommunicationEspecification.BUFFER_SIZE];
		
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        
		System.out.println(receiveData.length);
        
        return receiveData;
	
	}
	
	
	

	public static void main(String args[]) {
		UDPComunication udpComunication = new UDPComunication();
		
		try {

			
//			//server

//			System.out.println("aguardando");
//			byte[] data = udpComunication.readGroup("234.234.234.234", 4000);
//			String str = new String(data);
//			System.out.println("recebido " + str);
			
			
			//cliente
			String str = new String("louis gay");
			byte[] data = str.getBytes();
//			udpComunication.sendGroup("228.5.6.7", 4000, data);
			udpComunication.send("228.5.6.7", 4000, data);
			System.out.println("enviado " + str);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
