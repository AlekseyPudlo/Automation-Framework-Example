package com.ncube.test.pages.hotline_pages;

import com.ncube.test.webtestsbase.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppleIPhone7Page extends BasePage {

    private List<WebElement> allOffers;

    @FindBy(xpath = "//img[@src=\"/img/tx/107/1070042483.jpg\"]")
    private WebElement phoneImg;

    @FindBy(xpath = "//*[@data-statistic-key=\"stat158\"]")
    private WebElement allOffersTab;

    public AppleIPhone7Page() {
        super(false);
    }

    @Override
    protected void openPage() {

    }

    @Override
    public boolean isPageOpened() {
        return phoneImg.isDisplayed();
    }

    public AppleIPhone7Page goToAllOffersTab() {
        allOffersTab.click();
        return this;
    }

    public AppleIPhone7Page getAllOffers() {
        allOffers = findAllElementsByXpath("//*[@data-selector=\"price-line\"]");
        return this;
    }

    public AppleIPhone7Page printAllOffers() {
        int i = 0;
        for (WebElement element :
                allOffers) {
            System.out.println(++i + " - " + element.getAttribute("data-id"));
        }
        return this;
    }

    public AppleIPhone7Page filterOffersWithReviewsNotLessThen(int minReviews) {
        Iterator<WebElement> iterator = allOffers.listIterator();
        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPath = "//*[@data-id=\"" + elementDataId
                    + "\"]//a[@class=\"sum g_statistic\"]";
            String review;
            int reviewCount;

            if (findAllElementsByXpath(xPath).size() != 0) {
                review = findElementByXpath(xPath).getText().replaceAll("\\D+","");
                reviewCount = Integer.parseInt(review);

                if (reviewCount < minReviews) {
                    iterator.remove();
                }
            }
        }
        return this;
    }

    public AppleIPhone7Page filterOffersWithWarrantyMonthsNotLessThen(int minMonthsWarranty) {
        Iterator<WebElement> iterator = allOffers.listIterator();
        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPath = "//*[@data-id=\"" + elementDataId
                    + "\"]//*[@class=\"delivery-th cell4 cell-1024-b p-10-0-1024-b\"]";
            String warranty = findElementByXpath(xPath).getText().replaceAll("\\D+","");
            int warrantyMonthsCount;

            if (!warranty.isEmpty()) {
                warrantyMonthsCount = Integer.parseInt(warranty);

                if (warrantyMonthsCount < minMonthsWarranty) {
                    iterator.remove();
                }
            }
        }
        return this;
    }

    public void chooseTheCheapestElement() {
        Iterator<WebElement> iterator = allOffers.listIterator();
        String theCheapestElementDataId = "";
        double cheapestPrice = 999999999;

        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPath = "//*[@data-id=\"" + elementDataId
                    + "\"]//a[@id=\"gotoshop-price\"]";
            String price = findElementByXpath(xPath).getText().replaceAll(" ", "");
            price = price.replaceAll(",", ".");

            if (!price.isEmpty() && cheapestPrice > Double.parseDouble(price)) {
                cheapestPrice = Double.parseDouble(price);
                theCheapestElementDataId = elementDataId;
            }
        }

        findElementByXpath("//*[@data-id=\"" + theCheapestElementDataId
                + "\"]//*[@class=\"price txt-right cell5 cell-768 m_b-10-768\"]").click();
    }

    public void switchTab(int index) {
        List<String> browserTabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(browserTabs.get(index));
    }
}
