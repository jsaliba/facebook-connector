package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class SearchPostsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("searchPostsTestData");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchPosts() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-posts");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Post> result = (List<Post>) response.getMessage().getPayload();
			
			assertNotNull(result);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}