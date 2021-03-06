package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class SetPagePictureFromSourceTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("setPagePictureFromSourceTestData");
		
		String photoFilePath = getTestRunMessageValue("photoFilePath");
		
		File photo = new File(getClass().getClassLoader().getResource(photoFilePath).toURI());
		upsertOnTestRunMessage("sourceRef", photo);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testSetPagePictureFromSource() {
		try {
			Boolean result = runFlowAndGetPayload("set-page-picture-from-source");
			assertTrue(result);
		}
		catch (Exception e) {
			fail("Test failed. Please make sure that you have less than 1000 photos in your page's Profile Picture album, " +
					"and that the picture you are trying to upload is of the right size (at least 180 pixels wide).\n" + 
					ConnectorTestUtils.getStackTrace(e));
		}
	}
}
