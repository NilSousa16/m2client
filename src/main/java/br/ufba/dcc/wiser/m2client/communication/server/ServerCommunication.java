package br.ufba.dcc.wiser.m2client.communication.server;

/**
 * 
 * Responsible for communicating with the server
 * 
 * @author Nilson Rodrigues Sousa	
 */
public class ServerCommunication {

//	public boolean send(Object object) throws Exception{
//		try {
//			Gson gson = new Gson();
//			
//			String uri = "";
//
//			String jsonObject = gson.toJson(object);
//			
//			if(object instanceof Gateway) {
//				uri = "m2fot/fot-gateway/";
//			} else if(object instanceof GatewayStatus) {
//				uri = "m2fot-status/fot-gateway-status/";
//			}
//			
//			if (this.register("http://" + Consts.SERVER_IP + ":8181/cxf/" + uri, jsonObject)) {
//				return true;
//			}
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
//		return false;
//	}
//
//	private boolean register(String url, String jsonObject) throws Exception {
//		try {
//			HttpClient client = new HttpClient();
//			PostMethod mPost = new PostMethod(url);
//			mPost.setRequestEntity(new StringRequestEntity(jsonObject, null, null));
//
//			Header mtHeader = new Header();
//			mtHeader.setName("content-type");
//			mtHeader.setValue("application/x-www-form-urlencoded");
//			mtHeader.setName("accept");
//			mtHeader.setValue("application/json");
//			
//			mPost.addRequestHeader(mtHeader);
//			client.executeMethod(mPost);
//			
//			mPost.releaseConnection();
//			
//			if (mPost.getStatusCode() == 200) {
//				return true;
//			}
//		} catch (Exception e) {
//			System.out.println("M2Client - Exception in adding bucket...");
//			throw new Exception(e);
//		}
//		return false;
//	}

}
