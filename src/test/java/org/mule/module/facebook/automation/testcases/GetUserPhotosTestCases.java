package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Photo;

public class GetUserPhotosTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserPhotosTestData");
			
			String profileId = getProfileId();
			testObjects.put("user", profileId);
			
			String caption = (String) testObjects.get("caption");
			File photoFile = new File(getClass().getClassLoader().getResource((String) testObjects.get("photoFilePath")).toURI());
			
			String photoId = publishPhoto(profileId, caption, photoFile);
			testObjects.put("objectId", photoId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserPhotos() {
		try {
			
			MessageProcessor flow = lookupFlowConstruct("get-user-photos");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Photo> result = (List<Photo>) response.getMessage().getPayload();
			
			assertTrue(result.size() > 0);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String photoId = (String) testObjects.get("objectId");
			deleteObject(photoId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}