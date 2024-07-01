package br.ufba.dcc.wiser.m2client.simulation;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

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
public class GatewaySimulationEnvironment implements IGatewaySimulatorMqttInfoService {

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

	public GatewaySimulationEnvironment(int quantityDevices) {
		gatewayDataGenerator = new GatewayDataGenerator();
		serverCommunication = new ServerCommunication();

		random = new Random();

		for (int interator = 0; interator < quantityDevices; interator++) {
			mac = gatewayDataGenerator.generateRandomMacAddress();
			ip = gatewayDataGenerator.generateRandomIPAddress();
			manufacture = gatewayDataGenerator.getRandomManufacturer();
			hostName = gatewayDataGenerator.generateGatewayHostname();
			status = random.nextBoolean();
			date = Calendar.getInstance();
			solution = gatewayDataGenerator.getRandomSolution();
			coordinates = gatewayDataGenerator.generateRandomCoordinates();

			this.createGateway(mac, ip, manufacture, hostName, status, date, solution, coordinates);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void createGateway(String mac, String ip, String manufacturer, String hostName, boolean status,
			Calendar date, String solution, String coordinates) {
		Gateway gateway = new Gateway(mac, ip, manufacturer, hostName, status, date, solution, coordinates);

		Gson gson = new Gson();
		Object objectGateway = gateway;
		String jsonObject = gson.toJson(objectGateway);

		try {
			serverCommunication.send(jsonObject);
			// GatewaysSimulation.listGateways.add(gateway);
		} catch (Exception e) {
			System.out
					.println("GatewaySimulatorEnvironment - Falha no envio das informações cadastrais para o servidor");
			// e.printStackTrace();
		}
		
		GatewaysSimulation.listGateways.add(gateway); // RETIRAR APÓS ADICIONAR COMUNICAÇÃO COM O SERVIDOR
	}

	@Override
	public List<Gateway> getListGateway() {
		// solicitar do server
		return GatewaysSimulation.listGateways.subList(0, GatewaysSimulation.listGateways.size());
	}

	@Override
	public Gateway getGatewayById(String mac) {
		// solicitar do server 

		for (Gateway gateway : GatewaysSimulation.listGateways) {
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
			gatewayFound.setStatus(status);

			gson = new Gson();
			Object objectModifiedGateway = gatewayFound;
			String jsonObject = gson.toJson(objectModifiedGateway);

			try {
				serverCommunication.send(jsonObject);
				// GatewaysSimulation.listGateways.add(gateway);
			} catch (Exception e) {
				System.out.println(
						"GatewaySimulatorEnvironment - Falha no envio das informações de atualização de um gateway para o servidor...");
				// e.printStackTrace();
			}

			// verificar se a atualização ocorre na na instância da lista

		} else {
			System.out.println("GatewaySimulatorEnvironment - Gateway not found.");
		}
	}

	public void statusDataGeneration() {		
		GatewaysSimulation.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				if (gateway.isStatus()) {
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
						GatewaysSimulation.listGateways.add(gateway);
					} catch (Exception e) {
						System.out.println(
								"GatewaySimulatorEnvironment - Falha no envio das informações de status de um gateway para o servidor");
//						e.printStackTrace();
					}

					GatewaysSimulation.listGateways.add(gateway);
				}
			}
		});

	}

	public void gatewayMonitor() {
		GatewaysSimulation.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				this.updateGatewayStatus(gateway.getMac(), !gateway.isStatus());
			}
		});
	}

}
