/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Event;

public class GetPageEventsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getPageEventsTestData");
		
		String pageId = (String) getTestRunMessageValue("page");
		String eventName = (String) getTestRunMessageValue("eventName");
		String startTime = (String) getTestRunMessageValue("startTime");
		
		String eventId = publishEventPage(pageId, eventName, startTime);
		upsertOnTestRunMessage("eventId", eventId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPageEvents() {
		try {
			String eventId = (String) getTestRunMessageValue("eventId");
			
			List<Event> result = runFlowAndGetPayload("get-page-events");
			assertTrue(result.size() == 1);
			
			Event event = result.get(0);
			assertTrue(event.getId().equals(eventId));
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
    @After
    public void tearDown() throws Exception {
    	String eventId = (String) getTestRunMessageValue("eventId");
    	deleteObject(eventId);
    }
    
}