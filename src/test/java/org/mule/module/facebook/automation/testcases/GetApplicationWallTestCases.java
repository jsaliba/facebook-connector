package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Post;

public class GetApplicationWallTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getApplicationWallTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationWall() {
		try {
			List<Post> result = runFlowAndGetPayload("get-application-wall");
			assertNotNull(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
