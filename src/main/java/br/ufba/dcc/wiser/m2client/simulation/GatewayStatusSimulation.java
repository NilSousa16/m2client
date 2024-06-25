package br.ufba.dcc.wiser.m2client.simulation;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import br.ufba.dcc.wiser.m2model.model.Gateway;
import br.ufba.dcc.wiser.m2model.model.GatewayStatus;

/**
 * 
 * Responsible for creating and managing gateway status information
 * 
 * @author Nilson Rodrigues Sousa	
 */
public class GatewayStatusSimulation {

	private List<Gateway> listGateways;
	
	private Random random;
	
	private GatewayStatus gatewayStatus;
	
	public GatewayStatusSimulation(List<Gateway> listGateways) {
		this.listGateways = listGateways;
	}
	
	public void statusDataGeneration() {
		this.listGateways.forEach(gateway -> {
			if (random.nextBoolean()) {
				if(gateway.isStatus()) {
					gatewayStatus = new GatewayStatus();

					gatewayStatus.setDate(Calendar.getInstance());
					gatewayStatus.setBaterryLevel(random.nextDouble());
					gatewayStatus.setUsedMemory(random.nextDouble());
					gatewayStatus.setUsedProcessor(random.nextDouble());
				}
				
				// enviar dados para o servidor a cada 10 segundos
				System.out.println("Enviar dados para o servidor a cada 10 segundos. ");
			}
		});
		
		
	}
	
	
	
}
