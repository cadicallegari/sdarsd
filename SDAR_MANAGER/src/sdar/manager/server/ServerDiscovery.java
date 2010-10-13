/**
 * ServerDiscovery.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.server
 */
package sdar.manager.server;

import java.io.IOException;

import sdar.comunication.especification.Especification;
import sdar.comunication.udp.UDPComunication;
import sdar.manager.handler.DiscoveryHandler;

/**
 * @author cadi
 *
 */
public class ServerDiscovery extends Thread {
		
		private boolean finish = false;
		
		
		/**
		 * Construtor da classe
		 * @param threadName
		 */
		public ServerDiscovery(String threadName) {
			super(threadName);
		}


		/**
		 * Metodo que fica ouvindo a porta do servidor a espera de novas conexões
		 */
		@Override
		public void run() {

			UDPComunication com = new UDPComunication();
			Object object;
			
			while (!this.finish) {

				try {
					//Fica ouvindo o grupo
					object = com.readGroupObject(Especification.GROUP, Especification.DISCOVERY_PORT);
					
					//Cria uma nova thread assim que chega um objeto no grupo
					new Thread(new DiscoveryHandler(object), "DISCOVERY").start();
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		
		/**
		 * Método que finaliza o processo servidor
		 */
		public void finish() {
			this.finish = true;
		}
}
