package br.ufba.dcc.wiser.m2client.communication.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import br.ufba.dcc.wiser.m2client.simulation.GatewaySimulator;
import br.ufba.dcc.wiser.m2client.utils.Consts;

/**
 * Responsible for receiving and processing mqtt messages
 * 
 * @author Nilson Rodrigues Sousa
 */
public class ListenMQTTMessage implements IMqttMessageListener {

	private MQTTClientGateway clientMQTTCommunication;
	private GatewaySimulator gatewaySimulator;

	/*	 */
	public ListenMQTTMessage(MQTTClientGateway clientMQTTCommunication, int qos, GatewaySimulator gatewaySimulator,
			String... topics) {

		this.clientMQTTCommunication = clientMQTTCommunication;
		this.gatewaySimulator = gatewaySimulator;

		clientMQTTCommunication.subscribe(qos, this, topics);
	}

	/*	 */
	@Override
	public void messageArrived(String topic, MqttMessage mm) throws Exception {
		System.out.println("Mensagem recebida:");
		System.out.println("\tTópico: " + topic);
		System.out.println("\tMensagem: " + new String(mm.getPayload()));
		System.out.println("");

		if (topic.equals(Consts.SEND_DEVICE_REGISTER)) {
			System.out.println("ESCUTANDO BROKER DOS DISPOSITIVOS");
			System.out.println("Função: Envio dos dados cadastrais de um dispositivo para o SERVIDOR");
			System.out.println("Origem: Dispositivos - m2mqtt");
			System.out.println("Destino: Servidor - m2server");
			
			// chama função do gatewaysimulator
		} else if (topic.equals(Consts.SEND_DEVICE_INFO)) {
			System.out.println("ESCUTANDO BROKER DOS DISPOSITIVOS");
			System.out.println("Função: Envio dos dados sobre o status de um dispositivo para o SERVIDOR "
					+ " - realizado de forma automática pelo m2mqtt");
			System.out.println("Origem: Dispositivos - m2mqtt");
			System.out.println("Destino: Servidor - m2server");
			
			// chama função do gatewaysimulator
		} else if (topic.equals(Consts.SEND_DEVICE_SETTINGS)) {
			System.out.println("ESCUTANDO BROKER DOS DISPOSITIVOS");
			System.out.println("Executando o envio dos dados sobre o status de um dispositivo para o SERVIDOR "
					+ " - solicitaÇÃO realizada pelo servidor ou pelo gateway ");
			System.out.println("Origem: Dispositivos - m2mqtt");
			System.out.println("Destino: Servidor - m2server");
			
			// chama função do gatewaysimulator
		}
		// NÃO É NECESSÁRIO POIS O SERVIDOR ENVIA AS SOLICITAÇÕES VIA REST
//		} else if (topic.equals(Consts.RECEIVE_DEVICE_MODIFY_SETTINGS)) {
//			System.out.println("ESCUTANDO BROKER DO SERVER");
//			System.out.println("Executando o envio dos dados com as configurações a serem implantadas "
//					+ "em um determinado dispositivo ");
//			System.out.println("Origem: Servidor - m2server");
//			System.out.println("Destino: Dispositivos - m2mqtt");
//		} else if (topic.equals(Consts.RECEIVE_DEVICE_REQUEST)) {
//			System.out.println("ESCUTANDO BROKER DO SERVER");
//			System.out.println("Função: Envio de solicitação da configuração de um dispositivo");
//			System.out.println("Origem: Servidor - m2server");
//			System.out.println("Destino: Dispositivos - m2mqtt");
//		}
	}

}
