package br.ufba.dcc.wiser.m2client.communication.mqtt;

import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * 
 * Responsible for providing resources to communicate via mqtt with devices
 * 
 * @author Nilson Rodrigues Sousa	
 */
public class MQTTClientGateway implements MqttCallbackExtended {

	private final String serverURI;
	private MqttClient client;
	private final MqttConnectOptions mqttOptions;

	public MQTTClientGateway(String serverURI, String usuario, String senha) {
		this.serverURI = serverURI;

		mqttOptions = new MqttConnectOptions();
		mqttOptions.setMaxInflight(200);
		mqttOptions.setConnectionTimeout(3);
		mqttOptions.setKeepAliveInterval(10);
		mqttOptions.setAutomaticReconnect(true);
		mqttOptions.setCleanSession(false);

		if (usuario != null && senha != null) {
			mqttOptions.setUserName(usuario);
			mqttOptions.setPassword(senha.toCharArray());
		}
	}

	//
	public IMqttToken subscribe(int qos, IMqttMessageListener gestorMensagemMQTT, String... topicos) {
		if (client == null || topicos.length == 0) {
			return null;
		}
		int tamanho = topicos.length;
		int[] qoss = new int[tamanho];
		IMqttMessageListener[] listners = new IMqttMessageListener[tamanho];

		for (int i = 0; i < tamanho; i++) {
			qoss[i] = qos;
			listners[i] = gestorMensagemMQTT;
		}
		try {
			return client.subscribeWithResponse(topicos, qoss, listners);
		} catch (MqttException ex) {
			System.out.println(String.format("MQTTClientGateway - Error subscribing to topics %s - %s", Arrays.asList(topicos), ex));
			return null;
		}
	}

	public void unsubscribe(String... topicos) {
		if (client == null || !client.isConnected() || topicos.length == 0) {
			return;
		}
		try {
			client.unsubscribe(topicos);
		} catch (MqttException ex) {
			System.out.println(String.format("MQTTClientGateway - Error when unsubscribing to topic %s - %s", Arrays.asList(topicos), ex));
		}
	}

	public void start() {
		try {
			System.out.println("MQTTClientGateway - Connecting to the MQTT broker in " + serverURI);
			client = new MqttClient(serverURI, String.format("cliente_java_%d", System.currentTimeMillis()),
					new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
			client.setCallback(this);
			client.connect(mqttOptions);
		} catch (MqttException ex) {
			System.out.println("MQTTClientGateway - Error connecting to mqtt broker " + serverURI + " - " + ex);
		}
	}

	public void finish() {
		if (client == null || !client.isConnected()) {
			return;
		}
		try {
			client.disconnect();
			client.close();
		} catch (MqttException ex) {
			System.out.println("MQTTClientGateway - Error disconnecting from mqtt broker - " + ex);
		}
	}

	public void publish(String topic, String content, int qos) {
		try {
			if (client.isConnected()) {
				MqttMessage message = new MqttMessage(content.getBytes());
				message.setQos(qos);
				client.publish(topic, message);
				System.out.println("MQTTClientGateway - Message published in " + topic);
			} else {
				System.out.println("MQTTClientGateway - Client disconnected, topic could not be published " + topic);
			}
		} catch (MqttException ex) {
			System.out.println("MQTTClientGateway - Error publishing " + topic + " - " + ex);
		}

	}

	@Override
	public void connectionLost(Throwable thrwbl) {
		System.out.println("MQTTClientGateway - Broker connection lost -" + thrwbl);
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		System.out.println("MQTTClientGateway - MQTT Client " + (reconnect ? "reconnected" : "connected") + " with broker " + serverURI);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken imdt) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage mm) throws Exception {
	}
}
