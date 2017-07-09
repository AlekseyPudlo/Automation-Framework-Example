package com.ncube.test.webtestsbase;

import com.ncube.test.utils.TimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * This is the main class for pages. When you create your page - you must extend your class from this
 */
public abstract class BasePage {
    protected static final int WAIT_FOR_PAGE_LOAD_IN_SECONDS = 10;
    /**
     * In subclasses should be used for page opening
     */
    protected abstract void openPage();

    /**
     * checks if page opened
     * @return true if opened
     */
    public abstract boolean isPageOpened();

    public BasePage(boolean openPageByUrl){
        if(openPageByUrl){
            openPage();
        }
        PageFactory.initElements(getDriver(), this);
        waitForOpen();
    }

    /**
     * Waiting for page opening
     */
    protected void waitForOpen(){
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isPageOpened();
        while (!isPageOpenedIndicator && secondsCount < WAIT_FOR_PAGE_LOAD_IN_SECONDS) {
            TimeUtils.waitForSeconds(1);
            secondsCount++;
            isPageOpenedIndicator = isPageOpened();
        }
        if(!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }

    /**
     * getting webdriver instance
     * @return initialized in tests webdriver instance
     */
    protected WebDriver getDriver(){
        return WebDriverFactory.getDriver();
    }

    protected WebElement findElementByXpath(String xPath) {
        return getDriver().findElement(By.xpath(xPath));
    }

    protected List<WebElement> findAllElementsByXpath(String xPath) {
        return getDriver().findElements(By.xpath(xPath));
    }

    public String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }

}
