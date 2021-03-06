package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Link;

public class GetUserLinksTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserLinksTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
			
		String msg = (String) getTestRunMessageValue("msg");
		String link = (String) getTestRunMessageValue("link");
			
		String linkId = publishLink(profileId, msg, link);
		upsertOnTestRunMessage("linkId", linkId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserLinks() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			upsertOnTestRunMessage("user", profileId);
			
			String linkId = (String) getTestRunMessageValue("linkId");
			
			List<Link> links = runFlowAndGetPayload("get-user-links");
			// We only insert 1 link
			assertTrue(links.size() == 1);
			
			Link link = links.get(0);
			assertEquals(link.getId(), linkId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String profileId = (String) getTestRunMessageValue("profileId");
		String linkId = (String) getTestRunMessageValue("linkId");
		deleteObject(profileId + "_" + linkId);
	}
	
}
