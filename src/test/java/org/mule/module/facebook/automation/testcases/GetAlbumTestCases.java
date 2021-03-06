/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Album;

public class GetAlbumTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getAlbumTestData");
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		String id = publishAlbum((String) getTestRunMessageValue("albumName"), (String) getTestRunMessageValue("msg"), (String) getTestRunMessageValue("profileId"));
		upsertOnTestRunMessage("album", id);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetAlbum() {
		try {
			String id = (String) getTestRunMessageValue("album");
			String albumName = (String) getTestRunMessageValue("albumName");
			
			Album album = runFlowAndGetPayload("get-album");

			assertEquals(album.getId(), id);
			assertEquals(album.getName(), albumName);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
     
	}
    
}