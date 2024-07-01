package br.ufba.dcc.wiser.m2client.services;

import br.ufba.dcc.wiser.m2client.simulation.GatewayStatusSimulation;

public class GatewayStatusChanges implements Runnable {

	private volatile boolean running = true;
	private GatewayStatusSimulation gatewayStatusSimulation;
	private Thread updaterThread;

	public GatewayStatusChanges(GatewayStatusSimulation gatewayStatusSimulation) {
		this.gatewayStatusSimulation = gatewayStatusSimulation;
	}

	public void start() {
		updaterThread = new Thread(this);
		updaterThread.start();
	}

	public void stop() {
		running = false;
		if (updaterThread != null) {
			try {
				updaterThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (running) {
			
			gatewayStatusSimulation.statusDataGeneration();
			
			// Wait 5 seconds between verifications
			try {
				Thread.sleep(20000); 
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

}
