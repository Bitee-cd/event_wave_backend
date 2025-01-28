package com.bitee.event;

import com.bitee.event.Event.EventType;
import com.bitee.event.Tag.Tag;
import com.bitee.event.Tag.TagCategory;

import java.util.Date;

public class TestUtils {
    private static final Integer FIFTEEN_MINUTES = 15*60*1000;
    public static final String TEST_FIRST_NAME = "Test_First_Name";
    public static final String TEST_LAST_NAME = "Test_Last_Name";
    public static final String TEST_EMAIL="test@example.com";
    public static final String TEST_PASSWORD="TestPassword?";
    public static final String TEST_PHONE_NUMBER = "07000000000";
    public static final String TEST_OTP="000000";
    public static final Tag TEST_TAG = new Tag();
    public static final TagCategory TEST_TAG_CATEGORY = TagCategory.FASHION;
    public static final String TEST_TAG_NAME = "Tag Test";
    public static final Long TEST_ID = 1L;
    public static final EventType TEST_EVENT_TYPE= EventType.VIRTUAL;
    public static final String TEST_EVENT_LINK="https://www.linkedin.com/feed/";
    public static final Date TEST_START_DATE= new Date();
    public static final Date TEST_END_DATE= new Date(System.currentTimeMillis()+FIFTEEN_MINUTES);
}
