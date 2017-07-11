package com.ncube.test.webtestsbase;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class for pages. When you create your page - you must extend your class from this
 */
public abstract class BasePage {
    protected Wait customWait;
    protected static int PAGE_LOAD_TIMEOUT = 10;

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
        this.customWait = new Wait();
        waitForOpen();
    }

    /**
     * Waiting for page opening
     */
    protected void waitForOpen(){
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isPageOpened();
        while (!isPageOpenedIndicator && secondsCount < PAGE_LOAD_TIMEOUT) {
            customWait.forXmilliseconds(1);
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

    /**
     * getting single element by xPath
     * @param by
     * @return WebElement
     */
    protected WebElement findElement(By by) {
        customWait.forElementVisible(by);
        return getDriver().findElement(by);
    }

    /**
     * getting a List of web elements by xPath
     * @param by
     * @return List of Web Elements
     */
    protected List<WebElement> findAllElements(By by) {
        customWait.forElementVisible(by);
        return getDriver().findElements(by);
    }

    /**
     * checking if WebElement is displayed on the page
     * @param by
     * @return
     */
    protected boolean isElementDisplayed(By by) {
        try {
            return getDriver().findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * checking if element is on page or not. Changing the implicitWait value allows
     * @param by
     * @return
     */
    protected boolean isElementOnPage(By by) {
        customWait.changeImplicitWait(500, TimeUnit.MILLISECONDS);
        try {
            return getDriver().findElements(by).size() > 0;
        } finally {
            customWait.restoreDefaultImplicitWait();
        }
    }

    /**
     * getting current page's URL
     * @return String with current page's url
     */
    public String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }
}
