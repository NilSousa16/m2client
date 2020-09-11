package br.ufba.dcc.wiser.m2client.communication;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.google.gson.Gson;

import br.ufba.dcc.wiser.m2client.utils.Consts;

public class ServerCommunication {

	public boolean send(Object object) throws Exception{
		try {
			Gson gson = new Gson();

			String jsonObject = gson.toJson(object);
			
			if (this.register("http://" + Consts.IP + "/cxf/informationgateway/addgateway", jsonObject))
				return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return false;
	}

	private boolean register(String url, String jsonObject) throws Exception {
		String output = null;
		
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
			output = mPost.getResponseBodyAsString();
			mPost.releaseConnection();
			if (output.equals("true")) {
				System.out.println("Response: " + output);
				return true;
			}
		} catch (Exception e) {
			// throw new Exception("Exception in adding bucket : " + e);
			System.out.println("Exception in adding bucket...");
			throw new Exception(e);
		}

		return false;
	}

}