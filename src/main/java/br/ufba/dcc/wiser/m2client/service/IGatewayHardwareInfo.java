package br.ufba.dcc.wiser.m2client.service;

public interface IGatewayHardwareInfo {

	public long getMemoryAvailable();
	public long getMemoryUsed();
	public long getMemoryTotal();
	
	public double getCPULoad();
	
	public double getLevelBattery();
	
	public String getMacAddress();
	
	public String getIpAddress();
	
	public String getHostName();
	
	public String getManufacturer();
	
	
	// process information
	
	// network information

}
