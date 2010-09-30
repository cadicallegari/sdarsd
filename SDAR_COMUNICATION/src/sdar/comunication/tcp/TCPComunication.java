/**
 * TCPComunication.java
 * cadi
 * SDAR_GERENCIADOR
 * sdar.gerenciador.server
 */
package sdar.comunication.tcp;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import sdar.comunication.common.Packt;
import sdar.comunication.common.Solicitation;
import sdar.comunication.def.ComEspecification;

/**
 * @author cadi
 *
 */
public class TCPComunication {

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	
	public TCPComunication(Socket socket) throws IOException {
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.out.flush(); // Descarrega o buffer
		this.in = new ObjectInputStream(socket.getInputStream());	
	}


	
	/**
	 * 
	 * @param obj
	 * @throws IOException
	 */
	public void sendObject(Object obj) throws IOException {
        this.out.writeObject(obj);
        this.out.flush();
	}
	

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object readObject() throws IOException, ClassNotFoundException {
		Object obj = this.in.readObject();
		return obj;
	}
	

	
	public static void main(String args[]) {
		
		try {

			//envia obj
			Socket sock = new Socket("localhost", ComEspecification.TCP_PORT);
			System.out.println(sock.getLocalAddress());
			TCPComunication c = new TCPComunication(sock);
			Packt p = new Packt();
			Solicitation s = new Solicitation();
			p.setFileName("muitoloconeh");
			s.setMethodName("metodo muito loco");
			System.out.println("enviando");
//			c.sendObject(p);
			c.sendObject(s);
			System.out.println("enviado");
			sock.close();
			System.out.println("fechado");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
//	public String readStringToSocket() {
//
//		StringBuffer buf = new StringBuffer();
//		String line = null;
//		boolean fim = false;
//		
//		try {
//			
//			BufferedReader bufIn = new BufferedReader(new InputStreamReader(this.in));
//			
//			while (!fim) {
//				Thread.sleep(20);
//				
//				if (!fim) {
//					line = bufIn.readLine();
//				}
//				if (line != null) {
//					buf.append(line);
//				} else {
//					fim = true;
//				}
//				if (line.equals("</locadora>")) {
//					fim = true;
//				}
//			}			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		String ret = buf.toString();
//		
//		System.out.println("RECEBIDO:\n" + ret);
//		
//		return ret;
//		//return buf.toString();
//	}
	
}