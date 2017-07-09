package com.ncube.test.configuration;

import com.ncube.test.configuration.properties.PropertiesLoader;
import com.ncube.test.configuration.properties.Property;
import com.ncube.test.configuration.properties.PropertyFile;
import com.ncube.test.webtestsbase.Browser;
import org.openqa.selenium.Dimension;

import java.awt.*;

/**
 * Class for base tests properties - urls for test, browser name and version
 */
@PropertyFile("config.properties")
public class TestsConfig {

    private static TestsConfig config;

    public static TestsConfig getConfig() {
        if (config == null) {
            config = new TestsConfig();
        }
        return config;
    }

    public TestsConfig() {
        PropertiesLoader.populate(this);
    }

    @Property("browser.name")
    private String browser = "";

    @Property("browser.version")
    private String version = "";

    @Property("screenshot.path")
    private String screenshotDir ="";

    /**
     * getting browser object
     * @return browser object
     */
    public Browser getBrowser() {
        Browser browserForTests = Browser.getByName(browser);
        if (browserForTests != null) {
            return browserForTests;
        } else {
            throw new IllegalStateException("Browser name '" + browser + "' is not valid");
        }
    }

    /**
     * getting browser version
     * @return browser version
     */
    public String getBrowserVersion() {
        return version;
    }

    public String getScreenshotsDir() {
        return screenshotDir;
    }

    public Dimension getScreenSize() {
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }
}
