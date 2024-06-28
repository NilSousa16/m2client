package br.ufba.dcc.wiser.m2client.communication.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.communication.server.ServerCommunication;
import br.ufba.dcc.wiser.m2client.interfaces.IServerCommunication;
import br.ufba.dcc.wiser.m2client.simulation.GatewaySimulator;
import br.ufba.dcc.wiser.m2client.utils.Consts;
import br.ufba.dcc.wiser.m2model.model.Device;

/**
 * Responsible for receiving and processing mqtt messages
 * 
 * @author Nilson Rodrigues Sousa
 */
public class ListenMQTTMessage implements IMqttMessageListener {

	private MQTTClientGateway clientMQTTCommunication;
	private GatewaySimulator gatewaySimulator;

	private ServerCommunication serverCommunication;

	/*	 */
	public ListenMQTTMessage(MQTTClientGateway clientMQTTCommunication, int qos, GatewaySimulator gatewaySimulator,
			String... topics) {

		this.clientMQTTCommunication = clientMQTTCommunication;
		this.gatewaySimulator = gatewaySimulator;
		
		serverCommunication = new ServerCommunication();

		clientMQTTCommunication.subscribe(qos, this, topics);
	}

	/*	 */
	@Override
	public void messageArrived(String topic, MqttMessage mm) throws Exception {
//		System.out.println("Mensagem recebida:");
//		System.out.println("\tTópico: " + topic);
//		System.out.println("\tMensagem: " + new String(mm.getPayload()));
//		System.out.println("");

		if (topic.equals(Consts.SEND_DEVICE_REGISTER)) {
			System.out.println("ListenMQTTMessage - Enviando dados cadastrais de um dispositivo para o SERVIDOR");

			String jsonMessage = new String(mm.getPayload());
			Gson gson = new Gson();
			Device device = gson.fromJson(jsonMessage, Device.class);

			try {
				if (serverCommunication.send(device)) {
					System.out.println("ListenMQTTMessage - Informação enviada com sucesso...");
				} else {
					System.out.println("ListenMQTTMessage - Falha no envio das informações de cadastro do dispositivo " + device.getId());
				}
			} catch (Exception e) {
				System.out.println("ListenMQTTMessage - Erro no envio de dados para o servidor...");
//				e.printStackTrace();
			}
		} else if (topic.equals(Consts.SEND_DEVICE_INFO)) {
			System.out.println("ListenMQTTMessage - Envio de dados sobre o cadastro de um dispositivo para o SERVIDOR ");

			String jsonMessage = new String(mm.getPayload());
			Gson gson = new Gson();
			Device device = gson.fromJson(jsonMessage, Device.class);

			try {
				if (serverCommunication.send(device)) {
					System.out.println("ListenMQTTMessage - Informações sobre o status do dispositivo " + device.getId()
							+ " foi realizado com sucesso...");
				} else {
					System.out.println("ListenMQTTMessage - Falha no envio das informações de status do dispositivo " + device.getId());
				}
			} catch (Exception e) {
				System.out.println("ListenMQTTMessage - M2Client - Error sending status information from gateway");
//				e.printStackTrace();
			}
			
			// chama função do gatewaysimulator
		} else if (topic.equals(Consts.SEND_DEVICE_SETTINGS)) {
			System.out.println("ListenMQTTMessage - Executando o envio dos dados sobre o status de um dispositivo para o SERVIDOR "
					+ " - solicitação realizada pelo servidor ou pelo gateway ");

			// chama função do gatewaysimulator
		}
	}

}
