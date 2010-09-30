package sdar.comunication.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import sdar.comunication.def.CommunicationEspecification;

public class UDPComunication {

	
	
	public void sendObject(String address, int port, Object obj) throws IOException {

		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(address);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        ObjectOutputStream out = new ObjectOutputStream(bos);
	    out.writeObject(obj);
        out.close();
    
        // Obtém os bytes do objeto serializado
        byte[] msg = bos.toByteArray();
		
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, port);
		clientSocket.send(sendPacket);

		clientSocket.close();
	}
	
	
	
	
	
	
	public Object readGroupObject(String group, int port) throws IOException, ClassNotFoundException {
		
		InetAddress groupAddress = InetAddress.getByName(group);
		
		MulticastSocket multicastSocket = new MulticastSocket(port);
		multicastSocket.joinGroup(groupAddress);
		
		byte[] receiveData = new byte[CommunicationEspecification.BUFFER_SIZE];
		
        DatagramPacket receivedMessage= new DatagramPacket(receiveData, receiveData.length);

        multicastSocket.receive(receivedMessage);

        receiveData = receivedMessage.getData();
        
        //converte byte[] para Object
        ByteArrayInputStream bas = new ByteArrayInputStream(receiveData);
        ObjectInputStream in = new ObjectInputStream(bas);
        
        multicastSocket.leaveGroup(groupAddress);
        
        return in.readObject();
	
	}	
	
	
	/**
	 * 
	 * @param address
	 * @param port
	 * @param msg
	 * @throws IOException
	 */
	public void send(String address, int port, byte[] msg) throws IOException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(address);
		
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, port);
		clientSocket.send(sendPacket);

		clientSocket.close();
	}
	
	
	
	
	public byte[] readGroup(String group, int port) throws IOException {
		
		InetAddress groupAddress = InetAddress.getByName(group);
		
		MulticastSocket multicastSocket = new MulticastSocket(port);
		multicastSocket.joinGroup(groupAddress);
		
		byte[] receiveData = new byte[CommunicationEspecification.BUFFER_SIZE];
		
        DatagramPacket receivedMessage= new DatagramPacket(receiveData, receiveData.length);

        multicastSocket.receive(receivedMessage);

        receiveData = receivedMessage.getData();
        
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
        
        return receiveData;
	
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
