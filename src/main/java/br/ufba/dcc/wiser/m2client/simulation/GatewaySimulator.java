package br.ufba.dcc.wiser.m2client.simulation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.communication.mqtt.MQTTClientGateway;
import br.ufba.dcc.wiser.m2client.interfaces.IGatewaySimulatorMqttInfoService;
import br.ufba.dcc.wiser.m2client.utils.GatewayDataGenerator;
import br.ufba.dcc.wiser.m2model.model.Gateway;

/**
 * 
 * Responsible for creating and managing simulated gateways
 * 
 * @author Nilson Rodrigues Sousa	
 */
public class GatewaySimulator implements IGatewaySimulatorMqttInfoService {

	private List<Gateway> listGateways;
	private GatewayDataGenerator gatewayDataGenerator;

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

		listGateways.add(gateway);
	}

	@Override
	public List<Gateway> getListGateway() {
		// solicitar do server

		return listGateways;
	}

	@Override
	public Gateway getGatewayById(String mac) {
		// solicitar do server ou não caso a ideia seja que exista um cache

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

		} else {
			System.out.println("Gateway not found.");
		}
	}
	
	public void gatewayMonitor() {
		this.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				this.updateGatewayStatus(gateway.getMac(), !gateway.isStatus());
			}
		});
	}

}
