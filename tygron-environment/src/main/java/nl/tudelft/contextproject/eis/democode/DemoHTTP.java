package nl.tudelft.contextproject.eis.democode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DemoHTTP {

	private final static String apiUrl = "http://validate.jsontest.com/";
	
	public static void main(String[] args) {
		getTime();
	}
	
	public static void getTime() {

		Map<String, String> kvPairs = new HashMap<String, String>();
		kvPairs.put("json", "{\"key\":\"value\"}");
		HttpResponse re = null;
		try {
			re = doPost(apiUrl, kvPairs);
			String temp = EntityUtils.toString(re.getEntity());
			if (temp.length() != 0) {
				System.out.println(temp);
			}
		} catch (Exception e) {

		}
	}

	public static HttpResponse doPost(String url, Map<String, String> kvPairs)
			throws ClientProtocolException, IOException {
		@SuppressWarnings("resource")
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		if (kvPairs != null && kvPairs.isEmpty() == false) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					kvPairs.size());
			String k, v;
			Iterator<String> itKeys = kvPairs.keySet().iterator();

			while (itKeys.hasNext()) {
				k = itKeys.next();
				v = kvPairs.get(k);
				nameValuePairs.add(new BasicNameValuePair(k, v));
			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}
		HttpResponse response;
		response = httpclient.execute(httppost);
		return response;
	}
    
    public void doNothing() {
        
    }
}
