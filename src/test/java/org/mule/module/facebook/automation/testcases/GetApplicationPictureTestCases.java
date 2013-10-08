package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class GetApplicationPictureTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getApplicationPictureTestData");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationPicture() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-picture");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			byte[] result = (byte[]) response.getMessage().getPayload();
			
			assertTrue(result.length > 0);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}