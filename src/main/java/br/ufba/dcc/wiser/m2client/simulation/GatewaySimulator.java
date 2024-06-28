package br.ufba.dcc.wiser.m2client.simulation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.communication.mqtt.MQTTClientGateway;
import br.ufba.dcc.wiser.m2client.communication.server.ServerCommunication;
import br.ufba.dcc.wiser.m2client.interfaces.IGatewaySimulatorMqttInfoService;
import br.ufba.dcc.wiser.m2client.utils.GatewayDataGenerator;
import br.ufba.dcc.wiser.m2model.model.Gateway;
import br.ufba.dcc.wiser.m2model.model.GatewayStatus;

/**
 * 
 * Responsible for creating and managing simulated gateways
 * 
 * @author Nilson Rodrigues Sousa	
 */
public class GatewaySimulator implements IGatewaySimulatorMqttInfoService {

	private List<Gateway> listGateways;
	private GatewayDataGenerator gatewayDataGenerator;
	private GatewayStatus gatewayStatus;
	private ServerCommunication serverCommunication;

	private Gson gson;
	private Random random;

	private String mac;
	private String ip;
	private String manufacture;
	private String hostName;
	private boolean status;
	private Calendar date;
	private String solution;
	private String coordinates;

	public GatewaySimulator(int quantityDevices) {
		listGateways = new ArrayList<>();
		gatewayDataGenerator = new GatewayDataGenerator();
		serverCommunication = new ServerCommunication();

		random = new Random();

		for (int interator = 0; interator < quantityDevices; interator++) {
			mac = gatewayDataGenerator.generateRandomMacAddress();
			manufacture = gatewayDataGenerator.getRandomManufacturer();
			hostName = gatewayDataGenerator.generateGatewayHostname();
			status = random.nextBoolean();
			date = Calendar.getInstance();
			solution = gatewayDataGenerator.getRandomSolution();
			coordinates = gatewayDataGenerator.generateRandomCoordinates();

			this.createGateway(mac, manufacture, hostName, status, date, solution, coordinates);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createGateway(String mac, String manufacturer, String hostName, boolean status, Calendar date,
			String solution, String coordinates) {
		Gateway gateway = new Gateway(mac, ip, manufacturer, hostName, status, date, solution, coordinates);

		gson = new Gson();
		Object objectGateway = gateway;
		String jsonObject = gson.toJson(objectGateway);

		// enviar para o server armazenar no banco de dados
		// com a resposta adicionar no listGateways		
		System.out.println("GatewaySimulator - Enviando os dados do gateway " + gateway.getMac() + " para armazenamento no servidor.");

		try {
			serverCommunication.send(jsonObject);
			listGateways.add(gateway);
		} catch (Exception e) {
			System.out.println("GatewaySimulator - Falha no envio das informações cadastrais para o servidor");
//			e.printStackTrace();
		}
		// RETIRAR APÓS ADICIONAR COMUNICAÇÃO COM O SERVIDOR
		listGateways.add(gateway);
	}

	@Override
	public List<Gateway> getListGateway() {
		// solicitar do server
//		System.out.println("GatewaySimulator - Solicita lista de gateways do servidor.");

		return listGateways;
	}

	@Override
	public Gateway getGatewayById(String mac) {
		// solicitar do server ou não caso a ideia seja que exista um cache
		System.out.println("GatewaySimulator - Solicita dados do gateway " + mac + "ao servidor.");

		for (Gateway gateway : listGateways) {
			if (gateway.getMac() == mac) {
				return gateway;
			}
		}
		return null;
	}

	@Override
	public void updateGatewayStatus(String mac, Boolean status) {
		Gateway gatewayFound = this.getGatewayById(mac);
		if (gatewayFound != null) {
			gatewayFound.setStatus(status);;

			gson = new Gson();
			Object objectModifiedGateway = gatewayFound;
			String jsonObject = gson.toJson(objectModifiedGateway);
			
			// Enviar para o server realizar a modificação no banco
			System.out.println("GatewaySimulator - Enviando os dados do gateway " + gatewayFound.getMac() + "para atualização no servidor.");

		} else {
			System.out.println("GatewaySimulator - Gateway not found.");
		}
	}
	
	public void statusDataGeneration() {
		// enviar dados para o servidor a cada 10 segundos
		System.out.println("GatewaySimulator - Enviar dados para o servidor a cada 10 segundos. ");
		
		this.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				if(gateway.isStatus()) {
					gatewayStatus = new GatewayStatus();

					gatewayStatus.setDate(Calendar.getInstance());
					gatewayStatus.setBaterryLevel(random.nextDouble());
					gatewayStatus.setUsedMemory(random.nextDouble());
					gatewayStatus.setUsedProcessor(random.nextDouble());
					
					gson = new Gson();
					Object objectModifiedGateway = gatewayStatus;
					String jsonObject = gson.toJson(objectModifiedGateway);
					
					try {
						serverCommunication.send(jsonObject);
						listGateways.add(gateway);
					} catch (Exception e) {
						System.out.println("GatewaySimulator - Falha no envio das informações de status de um gateway para o servidor");
//						e.printStackTrace();
					}
					
					listGateways.add(gateway);					
				}
			}
		});
		
		
	}
	
	public void gatewayMonitor() {
		this.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				this.updateGatewayStatus(gateway.getMac(), !gateway.isStatus());
			}
		});
	}

}
