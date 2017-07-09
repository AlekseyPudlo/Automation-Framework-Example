package com.ncube.test.webtestsbase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class BaseTest {

//    @BeforeClass(alwaysRun = true)
//    public void beforeClass() { WebDriverFactory.startBrowser(true); }

//    @AfterClass(alwaysRun = true)
//    public void afterClass() { WebDriverFactory.finishBrowser(); }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() { WebDriverFactory.startBrowser(true);}

    @AfterTest(alwaysRun = true)
    public void afterTest() { WebDriverFactory.finishBrowser();}

//    @BeforeSuite(alwaysRun = true)
//    public void beforeSuite() { WebDriverFactory.startBrowser(true);}

    @AfterSuite(alwaysRun = true)
    public void afterSuite() { WebDriverFactory.finishBrowser();}


}
