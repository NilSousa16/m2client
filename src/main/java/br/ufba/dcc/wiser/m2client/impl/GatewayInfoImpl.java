package br.ufba.dcc.wiser.m2client.impl;

import java.util.Calendar;
import java.util.Random;

import br.ufba.dcc.wiser.m2client.communication.ServerCommunication;
import br.ufba.dcc.wiser.m2client.service.GatewayInfo;
import br.ufba.dcc.wiser.m2model.model.Gateway;
import br.ufba.dcc.wiser.m2model.model.GatewayStatus;

public class GatewayInfoImpl implements GatewayInfo {

	/* instance responsible for the latest update information */
	private static Gateway gateway;

	/* instance responsible for sending the information to the server */
	private ServerCommunication serverCommunication;

	/* instance responsible for capturing gateway status information */
//	private GatewayStatus gatewayStatus;
	
	/* responsible for indicating the registration of the gateway on the server */
	public boolean storaged = true; 
	
	/* simulation of the capture of the gateway's mac address */
	String macAddress = Integer.toString(new Random().nextInt());

	public ServerCommunication getServerCommunication() {
		return serverCommunication;
	}

	public void setServerCommunication(ServerCommunication serverCommunication) {
		this.serverCommunication = serverCommunication;
	}	
	
	public void informationMonitor() {
		new Thread() {
			public void run() {
				if (storaged) {
					gateway = new Gateway();

					// generating information
					gateway.setMac(macAddress);
					gateway.setIp(Integer.toString(new Random().nextInt()));
					gateway.setHostName(Integer.toString(new Random().nextInt()));					
					gateway.setManufacturer(Integer.toString(new Random().nextInt()));
					gateway.setStatus(new Random().nextBoolean());
					
					try {
						if(serverCommunication.send(gateway)) {
							System.out.println("Submission completed successfully");
							storaged = false;
						} else {
							System.out.println("Submission was not completed successfully");
						}
					} catch (Exception e) {
						System.out.println("The data could not be sent");
						e.printStackTrace();
					}
				} else {					
					//informações enviadas são referentes a dados de status dos gateways
					GatewayStatus gatewayStatus = new GatewayStatus();
					
					// generating information
					gatewayStatus.setGateway(new Gateway());
					gatewayStatus.setDate(Calendar.getInstance());
					gatewayStatus.getGateway().setMac(macAddress);
					gatewayStatus.setBaterryLevel(new Random().nextDouble());
					gatewayStatus.setUsedMemory(new Random().nextLong());
					gatewayStatus.setUsedProcessor(new Random().nextDouble());
					
					try {
						if(serverCommunication.send(gatewayStatus)) {
							System.out.println("Status information has been sent");
						} else {
							System.out.println("Status information was not sent");
						}
					} catch (Exception e) {
						System.out.println("Error sending status information from gateway");
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
}
