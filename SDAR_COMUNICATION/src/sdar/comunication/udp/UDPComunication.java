package sdar.comunication.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPComunication {

	private int BUFFER_SIZE;
	
	
	
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
		
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, port);
		clientSocket.send(sendPacket);

		clientSocket.close();
	}

	
	/**
	 * 
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public byte[] read(int port) throws IOException {
		
		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[this.BUFFER_SIZE];
		
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        System.out.println("aguardando conexao");
        serverSocket.receive(receivePacket);
        System.out.println("conexao estabelecida");
        receiveData = receivePacket.getData();
        
        return receiveData;
	
	}
	
	
	
	/**
	 * @return the bUFFER_SIZE
	 */
	public int getBufferSize() {
		return BUFFER_SIZE;
	}


	/**
	 * @param bUFFER_SIZE the bUFFER_SIZE to set
	 */
	public void setBufferSize(int bUFFER_SIZE) {
		BUFFER_SIZE = bUFFER_SIZE;
	}


	public static void main(String args[]) {
		UDPComunication udpComunication = new UDPComunication();
		
		try {
			
			byte[] data = udpComunication.read(4000);
			String str = new String(data);
			System.out.println(str);
//			
//			String str = new String("louis gay");
//			byte[] data = str.getBytes();
//			udpComunication.send("localhost", 4000, data);
//			System.out.println(str);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
