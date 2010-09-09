/**
 * TCPComunication.java
 * cadi
 * SDAR_GERENCIADOR
 * sdar.gerenciador.server
 */
package sdar.comunication.tcp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author cadi
 *
 */
public class TCPComunication {

	private Socket socket;
	private OutputStream out;
	private InputStream in;
	
	public TCPComunication(Socket sock) throws IOException {
		this.socket = sock;
		this.in = this.socket.getInputStream();
		this.out = this.socket.getOutputStream();
	}
	
	
	public String readStringToSocket() {

		StringBuffer buf = new StringBuffer();
		String line = null;
		boolean fim = false;
		
		try {
			
			BufferedReader bufIn = new BufferedReader(new InputStreamReader(this.in));
			
			while (!fim) {
				Thread.sleep(20);
				
				if (!fim) {
					line = bufIn.readLine();
				}
				if (line != null) {
					buf.append(line);
				} else {
					fim = true;
				}
				if (line.equals("</locadora>")) {
					fim = true;
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String ret = buf.toString();
		
		System.out.println("RECEBIDO:\n" + ret);
		
		return ret;
		//return buf.toString();
	}
	
	
	/**
	 * @param str
	 * @throws IOException 
	 */
	public void sendString(String str) throws IOException {
        this.out.write( str.getBytes() );
        this.out.flush();
        System.out.println("ENVIADO: \n" +  str);
	}
	
	
	public void close() throws IOException {
		this.socket.close();
	}
	
	
}