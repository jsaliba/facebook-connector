package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Link;

public class GetLinkTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getLinkTestData");
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);

		String msg = getTestRunMessageValue("msg").toString();
		String link = getTestRunMessageValue("link").toString();
		
		String messageId = publishLink(profileId, msg, link);
		upsertOnTestRunMessage("messageId", messageId);
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetLink(){
		try {
			String link = (String) getTestRunMessageValue("link");
			
			Link result = runFlowAndGetPayload("get-link");

			assertEquals(link, result.getId());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String profileId = (String) getTestRunMessageValue("profileId");
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(profileId + "_" + messageId);
	}

}
