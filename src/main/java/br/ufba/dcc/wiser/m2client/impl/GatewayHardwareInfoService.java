package br.ufba.dcc.wiser.m2client.impl;

import java.util.List;

import br.ufba.dcc.wiser.m2client.service.IGatewayHardwareInfo;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.OperatingSystem;

/**
 * Responsible for capturing information about the gateway's memory
 * 
 * @author Nilson Rodrigues Sousa
 */
public class GatewayHardwareInfoService implements IGatewayHardwareInfo {

	/* Instance for capturing system information */
	SystemInfo si = new SystemInfo();

	/* Instance to capture information about the gateway hardware */
	HardwareAbstractionLayer hal = si.getHardware();

	/* Instance to capture information about operational system */
	OperatingSystem os = si.getOperatingSystem();

	public GatewayHardwareInfoService() {
		this.si = new SystemInfo();
		this.hal = si.getHardware();
	}

	@Override
	public long getMemoryAvailable() {
		return hal.getMemory().getAvailable();
	}

	@Override
	public long getMemoryUsed() {
		return hal.getMemory().getTotal() - hal.getMemory().getAvailable();
	}

	@Override
	public long getMemoryTotal() {
		return hal.getMemory().getTotal();
	}

	@Override
	public double getCPULoad() {
		CentralProcessor cpu = hal.getProcessor();
		long[] prevTicks = new long[TickType.values().length];

		double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

		return cpuLoad;
	}

	@Override
	public double getLevelBattery() {
		List<PowerSource> list = hal.getPowerSources();

		for (PowerSource powerSource : list) {
			return powerSource.getRemainingCapacityPercent();
		}
		// Failure returns -1
		return -1;
	}

	@Override
	public String getMacAddress() {
		String mac = "";
		
		for (NetworkIF net : hal.getNetworkIFs()) {
			mac = net.getMacaddr();
		}

		return mac;
	}

	@Override
	public String getIpAddress() {
		String ip = "";
		
		for (NetworkIF net : hal.getNetworkIFs()) {
			String[] ips = net.getIPv4addr();

			for (int i = 0; i < ips.length; i++) {
				ip = ips[i];
			}
		}

		return ip;
	}

	@Override
	public String getHostName() {
		return os.getNetworkParams().getHostName();
	}

	@Override
	public String getManufacturer() {
		return hal.getComputerSystem().getManufacturer();
	}

}
