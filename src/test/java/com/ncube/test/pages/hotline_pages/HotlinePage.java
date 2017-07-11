package com.ncube.test.pages.hotline_pages;

import com.ncube.test.webtestsbase.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotlinePage extends BasePage {

    @FindBy(className = "header")
    private WebElement header;

    @FindBy(xpath = "//input[@id=\"searchbox\"]")
    private WebElement searchbox;

    private static String PAGE_URL = "http://hotline.ua";

    public HotlinePage() {
        super(true);
    }

    @Override
    protected void openPage() {
        getDriver().get(PAGE_URL);
    }

    @Override
    public boolean isPageOpened() {
        return header.isDisplayed();
    }

    public HotlinePage fillInSearchBox(String s) {
        searchbox.sendKeys(s);
        return this;
    }

    public AppleIPhone7Page chooseAppleIPhone7FromList(String s) {
        WebElement element = findElement(By.xpath("//a[contains(.,'" + s + "')]"));
        element.click();
        return new AppleIPhone7Page();
    }


}
