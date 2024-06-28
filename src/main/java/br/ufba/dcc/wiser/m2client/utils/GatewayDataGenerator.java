package br.ufba.dcc.wiser.m2client.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for generating various types of data related to IoT gateways
 * and smart city solutions.
 * 
 * @author Nilson Rodrigues Sousa
 */
public class GatewayDataGenerator {
	
	/**
	 * List of simulated manufacturers for IoT gateways.
	 */
	private List<String> listSimulationManufacturers;
	
	/**
	 * List of simulated smart city solutions.
	 */
	private List<String> listSimulationSolutions;

	/**
	 * Constructs a GatewayDataGenerator object initializing lists of manufacturers
	 * and solutions.
	 */
	public GatewayDataGenerator() {
		this.listSimulationManufacturers = new ArrayList<>();

		listSimulationManufacturers.add("Cisco");
		listSimulationManufacturers.add("Huawei");
		listSimulationManufacturers.add("Siemens");
		listSimulationManufacturers.add("Dell");
		listSimulationManufacturers.add("Advantech");
		listSimulationManufacturers.add("Schneider Electric");
		listSimulationManufacturers.add("IBM");
		listSimulationManufacturers.add("Intel");
		listSimulationManufacturers.add("HPE");
		listSimulationManufacturers.add("Samsung");
		listSimulationManufacturers.add("LG");
		listSimulationManufacturers.add("Aruba Networks");
		listSimulationManufacturers.add("Juniper Networks");
		listSimulationManufacturers.add("Bosch");
		listSimulationManufacturers.add("Honeywell");
		listSimulationManufacturers.add("Texas Instruments");
		listSimulationManufacturers.add("Panasonic");
		listSimulationManufacturers.add("Ericsson");
		listSimulationManufacturers.add("Nokia");
		listSimulationManufacturers.add("GE Digital");
		listSimulationManufacturers.add("Rockwell Automation");
		listSimulationManufacturers.add("Moxa");
		listSimulationManufacturers.add("ADLINK");
		listSimulationManufacturers.add("Lantronix");
		listSimulationManufacturers.add("Telit");
		listSimulationManufacturers.add("Multitech");
		listSimulationManufacturers.add("Eurotech");
		listSimulationManufacturers.add("NXP Semiconductors");
		listSimulationManufacturers.add("Dell EMC");
		listSimulationManufacturers.add("Qualcomm");
		listSimulationManufacturers.add("Broadcom");
		listSimulationManufacturers.add("Belden");
		listSimulationManufacturers.add("Gemalto");
		listSimulationManufacturers.add("InHand Networks");
		listSimulationManufacturers.add("Netgear");
		listSimulationManufacturers.add("Ubiquiti Networks");
		listSimulationManufacturers.add("Cradlepoint");
		listSimulationManufacturers.add("CalAmp");
		listSimulationManufacturers.add("Sierra Wireless");
		listSimulationManufacturers.add("Zebra Technologies");
		listSimulationManufacturers.add("Kontron");
		listSimulationManufacturers.add("Aaeon");
		listSimulationManufacturers.add("ICP DAS");
		listSimulationManufacturers.add("Pepperl+Fuchs");
		listSimulationManufacturers.add("Sena Technologies");
		listSimulationManufacturers.add("Belkin");
		listSimulationManufacturers.add("Linksys");
		listSimulationManufacturers.add("Ruckus Wireless");

		this.listSimulationSolutions = new ArrayList<>();

		listSimulationSolutions.add("Intelligent Traffic Management");
		listSimulationSolutions.add("Smart Public Lighting");
		listSimulationSolutions.add("Waste Management");
		listSimulationSolutions.add("Environmental Monitoring");
		listSimulationSolutions.add("Smart Energy");
	}

	/**
	 * Generates a random MAC address.
	 *
	 * @return A random MAC address in the format "XX:XX:XX:XX:XX:XX".
	 */
	public String generateRandomMacAddress() {
		Random random = new Random();
		byte[] macAddr = new byte[6];
		random.nextBytes(macAddr);

		// Convert to MAC address format
		StringBuilder macAddress = new StringBuilder(18);
		for (byte b : macAddr) {
			if (macAddress.length() > 0) {
				macAddress.append(":");
			}
			macAddress.append(String.format("%02x", b));
		}

		return macAddress.toString().toUpperCase();
	}

	/**
	 * Generates a random IPv4 address.
	 *
	 * @return A randomly generated IPv4 address as a string in the format
	 *         "XXX.XXX.XXX.XXX".
	 */
	public String generateRandomIPAddress() {
		Random random = new Random();
		StringBuilder ipAddress = new StringBuilder();

		for (int i = 0; i < 4; i++) {
			int octet = random.nextInt(256); // Generates a value between 0 and 255
			ipAddress.append(octet);
			if (i < 3) {
				ipAddress.append(".");
			}
		}

		return ipAddress.toString();
	}

	/**
	 * Returns a random manufacturer from the internal list of manufacturers.
	 *
	 * @return A randomly selected manufacturer.
	 */
	public String getRandomManufacturer() {
		Random random = new Random();
		int index = random.nextInt(this.listSimulationManufacturers.size());
		return this.listSimulationManufacturers.get(index);
	}

	/**
	 * Generates a random gateway hostname.
	 * 
	 * @return a string representing the hostname
	 */
	public String generateGatewayHostname() {
		Random random = new Random();
		int randomNumber = 100 + random.nextInt(900); // Generates a number between 100 and 999
		return String.format("gateway-%03d", randomNumber);
	}

	/**
	 * Returns a randomly selected smart city solution from the list of predefined
	 * solutions.
	 *
	 * @return A randomly selected smart city solution as a String.
	 */
	public String getRandomSolution() {
		Random random = new Random();

		int randomIndex = random.nextInt(listSimulationSolutions.size());

		return listSimulationSolutions.get(randomIndex);
	}

	/**
	 * Generates a random geographic coordinate.
	 * 
	 * @return a string representing the coordinate
	 */
	public String generateRandomCoordinates() {
		Random random = new Random();

		double latitude = -90 + (90 - (-90)) * random.nextDouble();
		double longitude = -180 + (180 - (-180)) * random.nextDouble();
		String coordinate = String.format("%.4f, %.4f", latitude, longitude);

		return coordinate;
	}

	/**
	 * Generates a random text string of the specified length.
	 *
	 * @param length The length of the random text to generate.
	 * @return A randomly generated text string consisting of alphanumeric
	 *         characters.
	 */
	public String generateRandomText(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder text = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			text.append(characters.charAt(index));
		}

		return text.toString();
	}
}
