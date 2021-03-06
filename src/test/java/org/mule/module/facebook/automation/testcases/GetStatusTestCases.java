package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.StatusMessage;

public class GetStatusTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getStatusTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		String msg = (String) getTestRunMessageValue("msg");
		
		String messageId = publishMessage(profileId, msg);
		upsertOnTestRunMessage("messageId", messageId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetStatus() {
		try {
			String msg = (String) getTestRunMessageValue("msg");
			String messageId = (String) getTestRunMessageValue("messageId");
			upsertOnTestRunMessage("status", messageId);
			
			StatusMessage status = runFlowAndGetPayload("get-status");

			assertTrue(status.getId().equals(messageId));
			assertTrue(status.getMessage().equals(msg));
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(messageId);
	}
	
}
