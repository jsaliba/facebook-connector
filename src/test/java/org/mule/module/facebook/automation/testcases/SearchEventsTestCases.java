package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Event;

public class SearchEventsTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("searchEventsTestData");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testSearchEvents() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-events");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Event> events = (List<Event>) response.getMessage().getPayload();
			assertTrue(events.size() > 0);
			
			for (Event event : events) {
				assertNotNull(event.getId());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}