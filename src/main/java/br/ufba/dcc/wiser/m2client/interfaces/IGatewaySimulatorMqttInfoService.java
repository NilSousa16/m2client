package br.ufba.dcc.wiser.m2client.interfaces;

import java.util.Calendar;
import java.util.List;

import br.ufba.dcc.wiser.m2model.model.Gateway;

public interface IGatewaySimulatorMqttInfoService {

	public void createGateway(String mac, String ip, String manufacturer, String hostName, boolean status, Calendar date,
			String solution, String coordinates);

	public List<Gateway> getListGateway();

	public Gateway getGatewayById(String id);

	public void updateGatewayStatus(String id, Boolean status);

}
