package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetVideoCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getVideoCommentsTestData");
			
		String commentId = publishComment((String) getTestRunMessageValue("postId"), (String) getTestRunMessageValue("msg"));
		upsertOnTestRunMessage("objectId", commentId);
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testGetVideoComments() {
		try {
			List<Comment> result = runFlowAndGetPayload("get-video-comments");

			Comment comment = result.get(0);
			assertEquals((String) getTestRunMessageValue("msg"), comment.getMessage());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String objectId = (String) getTestRunMessageValue("objectId");
		deleteObject(objectId);
	}

}
