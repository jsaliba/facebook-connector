package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class PublishEventTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("publishEventTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishEvent() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			String eventName = (String) getTestRunMessageValue("eventName");
			String startTime = (String) getTestRunMessageValue("startTime");
			
			String eventId = publishEvent(profileId, eventName, startTime);
			upsertOnTestRunMessage("objectId", eventId);
			
			assertTrue(StringUtils.isNotEmpty(eventId));
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject((String) getTestRunMessageValue("objectId"));
	}
}
