package com.ncube.test.webtestsbase;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Wait {
    private static final int DEFAULT_TIMEOUT = 15;
    private WebDriverWait wait;

    public Wait() {
        this.wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
    }

    /**
     * getting webdriver instance
     * @return initialized in tests webdriver instance
     */
    protected WebDriver getDriver(){
        return WebDriverFactory.getDriver();
    }

    /**
     * checking if the element is present in DOM
     * @param by
     * @return
     */
    public WebElement forElementPresentInDOM(By by) {
        return forElementPresentInDOM(by, true);
    }

    /**
     * checking if the element is present in DOM
     * @param by
     * @param failOnTimeOut
     * @return
     */
    public WebElement forElementPresentInDOM(By by, boolean failOnTimeOut) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            throw e;
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * checking if the element is present in DOM
     * @param by
     * @param timeout
     * @return
     */
    public WebElement forElementPresentInDOM(By by, int timeout) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            throw e;
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is clickable in browser tab
     * @param element element that is checked to be clickable
     * @return
     */
    public WebElement forElementClickable(WebElement element) {
        changeImplicitWait(0, TimeUnit.MILLISECONDS);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverException e) {
            throw e;
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is clickable in browser tab
     * @param element
     * @param timeout
     * @return
     */
    public WebElement forElementClickable(WebElement element, int timeout) {
        changeImplicitWait(0, TimeUnit.MILLISECONDS);
        try{
            return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverException e) {
            throw e;
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is clickable in browser tab
     * @param by
     * @return
     */
    public WebElement forElementClickable(By by) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is clickable in browser tab
     * @param by
     * @param timeout
     * @return
     */
    public WebElement forElementClickable(By by, int timeout) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(by));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is visible in browser
     * @param element element that should be visible
     * @return
     */
    public WebElement forElementVisible(WebElement element) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is visible in browser
     * @param element
     * @param timeout
     * @param polling
     * @return
     */
    public  WebElement forElementVisible(WebElement element, int timeout, int polling) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return new WebDriverWait(getDriver(), timeout, polling).until(ExpectedConditions.visibilityOf(element));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is visible in browser
     * @param
     * @return
     */
    public WebElement forElementVisible(By by) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * Checking if the element is visible in browser
     * @param by
     * @param timeout
     * @param polling
     * @return
     */
    public WebElement forElementVisible(By by, int timeout, int polling) {
        changeImplicitWait(250, TimeUnit.MILLISECONDS);
        try {
            return new WebDriverWait(getDriver(), timeout, polling).until(ExpectedConditions.visibilityOfElementLocated(by));
        } finally {
            restoreDefaultImplicitWait();
        }
    }

    /**
     * wrapper for Thread.sleep method
     * @param time time value
     */
    public void forXmilliseconds(int time) {
        System.out.println("Wait for " + time + " ms.");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Wait.forXmilliseconds method throws the exception\n" +  e);
        }
    }

    /**
     * customising Drivers implicit wait
     * @param value
     * @param timeUnit
     */
    protected void changeImplicitWait(int value, TimeUnit timeUnit){
        getDriver().manage().timeouts().implicitlyWait(value, timeUnit);
    }

    /**
     * returning implicit wait to his default state
     */
    protected void restoreDefaultImplicitWait() {
        changeImplicitWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }
}
