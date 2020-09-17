package br.ufba.dcc.wiser.m2client.communication;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.utils.Consts;
import br.ufba.dcc.wiser.m2model.model.Gateway;
import br.ufba.dcc.wiser.m2model.model.GatewayStatus;

public class ServerCommunication {

	public boolean send(Object object) throws Exception{
		try {
			Gson gson = new Gson();
			
			String uri = "";

			String jsonObject = gson.toJson(object);
			
			if(object instanceof Gateway) {
				uri = "m2fot/fot-gateway/";
			} else if(object instanceof GatewayStatus) {
				uri = "m2fot/fot-gatewaystatus/";
			}
			
			if (this.register("http://" + Consts.IP + ":8181/cxf/" + uri, jsonObject)) {
				return true;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return false;
	}

	private boolean register(String url, String jsonObject) throws Exception {
		try {
			HttpClient client = new HttpClient();
			PostMethod mPost = new PostMethod(url);
			mPost.setRequestEntity(new StringRequestEntity(jsonObject, null, null));

			Header mtHeader = new Header();
			mtHeader.setName("content-type");
			mtHeader.setValue("application/x-www-form-urlencoded");
			mtHeader.setName("accept");
			mtHeader.setValue("application/json");
			
			mPost.addRequestHeader(mtHeader);
			client.executeMethod(mPost);
			
			mPost.releaseConnection();
			
			if (mPost.getStatusCode() == 200) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception in adding bucket...");
			throw new Exception(e);
		}
		return false;
	}

}
