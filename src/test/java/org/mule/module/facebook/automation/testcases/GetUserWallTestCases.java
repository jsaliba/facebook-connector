package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Post;

public class GetUserWallTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserWallTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		List<String> messages = (List<String>) getTestRunMessageValue("messages");
		List<String> messageIds = new ArrayList<String>();
		
		for (String message : messages) {
			String messageId = publishMessage(profileId, message);
			messageIds.add(messageId);
			upsertOnTestRunMessage("messageIds", messageIds);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserWall() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			upsertOnTestRunMessage("user", profileId);
			
			final List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
			
			Collection<Post> posts = runFlowAndGetPayload("get-user-wall");
			
			Collection<Post> matching = CollectionUtils.select(posts, new Predicate() {
				
				@Override
				public boolean evaluate(Object object) {
					Post post = (Post) object;
					return messageIds.contains(post.getId());
				}
			});
			
			assertEquals(matching.size(), messageIds.size());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() throws Exception {
		List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
		for (String messageId : messageIds) {
			deleteObject(messageId);
		}
	}
}
