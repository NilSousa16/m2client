package br.ufba.dcc.wiser.m2client.impl;

import java.util.Random;

import br.ufba.dcc.wiser.m2client.communication.ServerCommunication;
import br.ufba.dcc.wiser.m2client.service.GatewayInfo;
import br.ufba.dcc.wiser.m2model.model.Gateway;

public class GatewayInfoImpl implements GatewayInfo {

	/* instance responsible for the latest update information */
	private static Gateway gateway;

	/* instance responsible for sending the information to the server */
	private ServerCommunication serverCommunication;

	/* instance responsible for capturing gateway information */
	
	/* responsible for indicating the registration of the gateway on the server */
	public boolean storaged = true; 

	public void informationMonitor() {
		new Thread() {
			public void run() {
				if (storaged) {
					gateway = new Gateway();

					// gerando informações
					gateway.setMac(Integer.toString(new Random().nextInt()));
					gateway.setIp(Integer.toString(new Random().nextInt()));
					gateway.setHostName(Integer.toString(new Random().nextInt()));					
					gateway.setManufacturer(Integer.toString(new Random().nextInt()));
					gateway.setStatus(new Random().nextBoolean());
					
					try {
						if(serverCommunication.send(gateway)) {
							System.out.println("Envio concluído com sucesso!!!!");
						} else {
							System.out.println("Envio não foi concluído com sucesso!!!!");
						}
					} catch (Exception e) {
						System.out.println("The data could not be sent");
						System.out.println("Error: " + e.getMessage());
					}
					
					storaged = false;

					System.out.println("Envio para cadastro concluído com sucesso.");
				} else {
					System.out.println("Envio para atualização do status concluído com sucesso.");
					// condição para envio caso as informações cadastrais já estejam ok! 
					
					//informações enviadas são referentes a dados de status dos gateways
				}
			}
		}.start();
	}
	
}
