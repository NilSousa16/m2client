package br.ufba.dcc.wiser.m2client.simulation;

import java.util.Calendar;
import java.util.Random;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.communication.server.ServerCommunication;
import br.ufba.dcc.wiser.m2model.model.GatewayStatus;

/**
 * The GatewayStatusSimulation class is responsible for simulating the status of
 * a list of gateways.
 * 
 * @author Nilson Rodrigues Sousa
 */
public class GatewayStatusSimulation {

	/**
	 * A random number generator for status simulation.
	 */
	private Random random;

	/**
	 * 
	 */
	private Gson gson;

	/**
	 * The current gateway status being simulated.
	 */
	private GatewayStatus gatewayStatus;

	/**
	 * 
	 */
	private ServerCommunication serverCommunication;
	
	/**
	 * Generates status data for the gateways in the list. For each gateway, it
	 * randomly decides whether to generate status data. If status data is
	 * generated, it includes the date, battery level, used memory, and used
	 * processor.
	 */
	public void statusDataGeneration() {
		random = new Random();

		GatewaysSimulation.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				if (gateway.isStatus()) {
					gatewayStatus = new GatewayStatus();

					gatewayStatus.setDate(Calendar.getInstance());
					gatewayStatus.setBaterryLevel(random.nextDouble());
					gatewayStatus.setUsedMemory(random.nextDouble());
					gatewayStatus.setUsedProcessor(random.nextDouble());
				}

				gson = new Gson();
				Object objectModifiedGateway = gatewayStatus;
				String jsonObject = gson.toJson(objectModifiedGateway);

				try {
					// Send data to the server every 10 seconds
					System.out.println("GatewayStatusSimulator - Enviar dados para o servidor a cada 10 segundos. ");
					serverCommunication.send(jsonObject);
				} catch (Exception e) {
					System.out.println(
							"GatewaySatusSimulator - Falha no envio das informações de status de um gateway para o servidor");
					// e.printStackTrace();
				}
			}
		});

	}

}
