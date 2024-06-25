package br.ufba.dcc.wiser.m2client.services;

import br.ufba.dcc.wiser.m2client.simulation.GatewaySimulator;

public class GatewayChanges implements Runnable {

	private volatile boolean running = true;
	private GatewaySimulator gatewaySimulator;
	private Thread updaterThread;

	public GatewayChanges(GatewaySimulator gatewaySimulator) {
		this.gatewaySimulator = gatewaySimulator;
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
			
			gatewaySimulator.gatewayMonitor();
			
			// Wait 5 seconds between verifications
			try {
				Thread.sleep(5000); 
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

}
