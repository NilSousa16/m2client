/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufba.dcc.wiser.m2client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import br.ufba.dcc.wiser.m2client.communication.mqtt.ListenMQTTMessage;
import br.ufba.dcc.wiser.m2client.communication.mqtt.MQTTClientGateway;
import br.ufba.dcc.wiser.m2client.services.GatewayStatusChanges;
import br.ufba.dcc.wiser.m2client.simulation.GatewaySimulator;
import br.ufba.dcc.wiser.m2client.simulation.GatewayStatusSimulation;
import br.ufba.dcc.wiser.m2client.utils.Consts;

/**
 * Responsible for starting the bundle in karaf
 * 
 * @author Nilson Rodrigues Sousa
 */
public class Activator implements BundleActivator {
	
	MQTTClientGateway clientMQTTCommunication = new MQTTClientGateway(Consts.BROKER_IP, null, null);
	String[] topics = { Consts.SEND_DEVICE_REGISTER, Consts.SEND_DEVICE_INFO, Consts.SEND_DEVICE_SETTINGS };

	GatewayStatusChanges gatewayStatusChanges;
	
	GatewayStatusSimulation gatewayStatusSimulation;
	
	GatewaySimulator gatewaySimulator;
	
    public void start(BundleContext context) {
    	clientMQTTCommunication.start();

		gatewaySimulator = new GatewaySimulator(10);
		
		new ListenMQTTMessage(clientMQTTCommunication, 0, gatewaySimulator, topics);

		gatewayStatusChanges = new GatewayStatusChanges(gatewayStatusSimulation);
		gatewayStatusChanges.start();
		
    	
        System.out.println("Starting the bundle - m2client");
    }

    public void stop(BundleContext context) {
        System.out.println("Stopping the bundle - m2client");
    }

}