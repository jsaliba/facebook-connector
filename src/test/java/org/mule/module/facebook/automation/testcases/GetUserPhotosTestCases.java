package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Photo;

public class GetUserPhotosTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserPhotosTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("user", profileId);
		
		String caption = (String) getTestRunMessageValue("caption");
		File photoFile = new File(getClass().getClassLoader().getResource((String) getTestRunMessageValue("photoFilePath")).toURI());
		
		String photoId = publishPhoto(profileId, caption, photoFile);
		upsertOnTestRunMessage("photoId", photoId);
		
		tagPhoto(photoId, profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserPhotos() {
		try {
			String photoId = (String) getTestRunMessageValue("photoId");
			
			List<Photo> result = runFlowAndGetPayload("get-user-photos");
			assertEquals(result.size(), 1);
			
			Photo photo = result.get(0);
			assertEquals(photo.getId(), photoId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String photoId = (String) getTestRunMessageValue("photoId");
		deleteObject(photoId);
	}
	
}
