package com.ncube.test.webtestsbase;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverProvider {

    //  Returns a new instance of FireFox Driver
    public WebDriver initFireFoxBrowser() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("gecko", true);
        FirefoxDriverManager.getInstance().setup();
        return new FirefoxDriver(capabilities);
    }

    //  Returns a new instance of chrome Driver
    public WebDriver initChromeBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-proxy-server");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver initOperaBrowser() {
        OperaDriverManager.getInstance().setup();
        return new OperaDriver();
    }

    public WebDriver initPhantomJsBrowser() {
        PhantomJsDriverManager.getInstance().setup();
        return new PhantomJSDriver();
    }

    public WebDriver initEdgeBrowser() {
        EdgeDriverManager.getInstance().setup();
        return new EdgeDriver();
    }

    public WebDriver initInternExplorerBrowser() {
        InternetExplorerDriverManager.getInstance().setup();
        return new InternetExplorerDriver();
    }
}
