package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.restfb.types.User;

public class UninviteUserTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("uninviteUserTestData");
		
		String profileId = getProfileId();
		String auxProfileId = getProfileIdAux();
		upsertOnTestRunMessage("profileId", profileId);
		upsertOnTestRunMessage("auxProfileId", auxProfileId);
			
		String eventName = getTestRunMessageValue("eventName");
		String startTime = getTestRunMessageValue("startTime");
		
		String eventId = publishEvent(profileId, eventName, startTime);
		upsertOnTestRunMessage("eventId", eventId);
		
		inviteUser(eventId, auxProfileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testUninviteUser() {
		try {
			String auxProfileId = getTestRunMessageValue("auxProfileId");
			String eventId = getTestRunMessageValue("eventId");
			String until = getTestRunMessageValue("until");
			String since = getTestRunMessageValue("since");
			String offset = getTestRunMessageValue("offset");
			String limit = getTestRunMessageValue("limit");

			Boolean result = runFlowAndGetPayload("uninvite-user");
			assertTrue(result);
			
			List<User> invited = getEventInvited(eventId, until, since, limit, offset);
			for (User user : invited) {
				assertFalse(user.getId().equals(auxProfileId));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String eventId = getTestRunMessageValue("eventId");
		deleteObject(eventId);
	}
	
}
