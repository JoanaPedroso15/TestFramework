package pageObjects;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestInputs {
	
private static final Logger LOG = LoggerFactory.getLogger(TestInputs.class);
	
	private static Map <String, String> purchaseData = new HashMap <> ();

	
	public static Map<String, String> getPurchaseData() {
		return purchaseData;
	}


	public static void setPurchaseData(String key, String value) {
		TestInputs.purchaseData.put(key, value);
	}


	public static void clearPurchaseData() {
		TestInputs.purchaseData.clear(); 
	}
	

}
