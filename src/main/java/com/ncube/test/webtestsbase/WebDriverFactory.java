package com.ncube.test.webtestsbase;

import com.ncube.test.configuration.TestsConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Base class for web tests. It contains web driver {@link WebDriver} instance, used in all tests.
 * All communications with driver should be done through this class
 */
public class WebDriverFactory {
    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    private static WebDriver driver;

    /**
     * Getting of pre-configured {@link WebDriver} instance.
     * Please use this method only after call {@link #startBrowser(boolean) startBrowser} method
     *
     * @return webdriver object, or throw IllegalStateException, if driver has not been initialized
     */
    protected static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver has not been initialized. " +
                    "Please call WebDriverFactory.startBrowser() before use this method");
        }
    }

    /**
     * Main method of class - it initialize driver and starts browser.
     *
     * @param isLocal - are tests will be started local or not
     */
    public static void startBrowser(boolean isLocal) {
        if (driver == null) {
            Browser browser = TestsConfig.getConfig().getBrowser();
            if (!isLocal) {
                driver = new RemoteWebDriver(CapabilitiesGenerator.getDefaultCapabilities(browser));
            } else {
                switch (browser) {
                    case FIREFOX:
                        driver = new DriverProvider().initFireFoxBrowser();
                        break;
                    case CHROME:
                        driver = new DriverProvider().initChromeBrowser();
                        break;
                    case OPERA:
                        driver = new DriverProvider().initOperaBrowser();
                        break;
                    case PHANTOMJS:
                        driver = new DriverProvider().initPhantomJsBrowser();
                        break;
                    case EDGE:
                        driver = new DriverProvider().initEdgeBrowser();
                        break;
                    case IE:
                        driver = new DriverProvider().initInternExplorerBrowser();
                        break;
                    default:
                        throw new IllegalStateException("Unsupported browser type");
                }
            }
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().window().setSize(TestsConfig.getConfig().getScreenSize());
            driver.manage().window().maximize();
        } else {
            throw new IllegalStateException("Driver has already been initialized. Quit it before using this method");
        }
    }

    /**
     * Finishes browser
     */
    public static void finishBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Load a new web page in the current browser window. This is done using an HTTP GET operation,
     * and the method will block until the load is complete. This will follow redirects issued either
     * by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect
     * "rest" for any duration of time, it is best to wait until this timeout is over, since should
     * the underlying page change whilst your test is executing the results of future calls against
     * this interface will be against the freshly loaded page. Synonym for
     * {@link WebDriver.Navigation#to(String)}.
     *
     * @param url The URL to load. It is best to use a fully qualified URL
     */
    public static void get(String url) {
        driver.get(url);
    }

    /**
     * Method for screenshot taking. It is empty now, because you could save your screenshot as you want.
     * This method calls in tests listeners on test fail
     */
    public static void takeScreenShot(final ITestResult result) {
        if (driver != null) {
            final DateFormat timeFormat = new SimpleDateFormat("MM.dd.yyyy_HH-mm-ss");
            final String fileName = result.getMethod().getMethodName() + "_" + timeFormat.format(new Date());
            try {
                File srcFile;
                if (driver.getClass().equals(RemoteWebDriver.class)) {
                    srcFile = ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(OutputType.FILE);
                } else {
                    srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                }
                String outputDir = result.getTestContext().getOutputDirectory();
                outputDir = outputDir.substring(0,outputDir.lastIndexOf(File.separator) + 1) + "screenshots";
                final File saved = new File(outputDir, fileName + ".png");
                FileUtils.copyFile(srcFile, saved);
                result.setAttribute("screenshot", saved.getName());
            } catch (IOException e) {
                result.setAttribute("reportGeneratingException", e);
            }
            result.setAttribute("screenshotURL", driver.getCurrentUrl());
        } else {
            throw new NoSuchSessionException("It's impossible to make a screenshot. WebDriver may not have been initialized...");
        }
    }

    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
