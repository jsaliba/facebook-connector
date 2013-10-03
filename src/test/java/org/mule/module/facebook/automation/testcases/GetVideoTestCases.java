package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Video;

public class GetVideoTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getVideoTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetVideo() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-video");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Video video = (Video) response.getMessage().getPayload();
			
			assertNotNull(video);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}