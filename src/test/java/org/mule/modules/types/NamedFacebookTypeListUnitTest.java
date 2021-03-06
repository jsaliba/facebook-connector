/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.NamedFacebookTypeList;

import com.restfb.types.NamedFacebookType;
 
public class NamedFacebookTypeListUnitTest
{
    private NamedFacebookTypeList list;
    
    @Before
    public void setup()
    {
        list = new NamedFacebookTypeList();
    }
    
    @Test
    public void testGettersAndSetters()
    {
        final List<NamedFacebookType> mockList = new ArrayList<NamedFacebookType>();
        mockList.add(mock(NamedFacebookType.class));
        mockList.add(mock(NamedFacebookType.class));
        list.setData(mockList);
        assertEquals(list.getData().size(), 2);
    }
}
