package br.ufba.dcc.wiser.m2client.services;

/**
 * Responsible for collecting information from a gateway and sending it to the server
 * 
 * It also assesses the need to register the gateway on the server
 * 
 * @author Nilson Rodrigues Sousa
 */
public class GatewayInfoImpl { // implements GatewayInfo {

//	/* instance responsible for the latest update information */
//	private static Gateway gateway;
//
//	/* instance responsible for sending the information to the server */
//	private ServerCommunication serverCommunication;
//
//	/* responsible for indicating the registration of the gateway on the server */
//	public boolean storaged = true; 
//	
//	/* simulation of the capture of the gateway's mac address */
//	String macAddress = Integer.toString(new Random().nextInt());
//	
//	/* information hardware */
//	SystemInfo si = new SystemInfo();
//	HardwareAbstractionLayer hal = si.getHardware();
//
//	public ServerCommunication getServerCommunication() {
//		return serverCommunication;
//	}
//
//	public void setServerCommunication(ServerCommunication serverCommunication) {
//		this.serverCommunication = serverCommunication;
//	}
//
//	@Override
//	public void informationMonitor() {
//		// TODO Auto-generated method stub
//		
//	}	
//	
	/**
	 * Method composes information for registering the 
	 * gateway and sending status information
	 * 
	 * @author Nilson Rodrigues Sousa
	 */
//	public void informationMonitor() {
//		new Thread() {
//			public void run() {
//				if (storaged) {
//					gateway = new Gateway();
//
////					gateway.setMac(gatewayHardwareInfo.getMacAddress());
////					gateway.setIp(gatewayHardwareInfo.getIpAddress());
////					gateway.setHostName(gatewayHardwareInfo.getHostName());					
////					gateway.setManufacturer(gatewayHardwareInfo.getManufacturer());
//					gateway.setStatus(true);
//					
//					try {
//						if(serverCommunication.send(gateway)) {
//							System.out.println("M2Client - Gateway registration was successful");
//							storaged = false;
//						} else {
//							System.out.println("M2Client - Gateway registration was not successful");
//						}
//					} catch (Exception e) {
//						System.out.println("M2Client - Gateway registration data could not be sent");
//						//e.printStackTrace();
//					}
//				} else {					
//					//information sent refers to the status data of the gateways
//					GatewayStatus gatewayStatus = new GatewayStatus();
//					
//					// generating information
////					gatewayStatus.setGateway(new Gateway());
////					gatewayStatus.setDate(Calendar.getInstance());
////					gatewayStatus.getGateway().setMac(gatewayHardwareInfo.getMacAddress());
////					gatewayStatus.getGateway().setStatus(true);
////					gatewayStatus.setBaterryLevel(gatewayHardwareInfo.getLevelBattery());
////					gatewayStatus.setUsedMemory(gatewayHardwareInfo.getMemoryUsed());
////					gatewayStatus.setUsedProcessor(gatewayHardwareInfo.getCPULoad());
//
////					try {
////						if(serverCommunication.send(gatewayStatus)) {
////							//System.out.println("M2Client - Status information has been sent");
////						} else {
////							System.out.println("M2Client - Status information was not sent");
////						}
////					} catch (Exception e) {
////						System.out.println("M2Client - Error sending status information from gateway");
////						e.printStackTrace();
////					}
////				}
////			}
////		}.start();
////	}
	
}
