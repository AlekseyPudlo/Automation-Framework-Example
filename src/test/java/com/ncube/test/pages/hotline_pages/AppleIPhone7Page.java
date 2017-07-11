package com.ncube.test.pages.hotline_pages;

import com.ncube.test.webtestsbase.BasePage;
import org.openqa.selenium.By;
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

    /**
     * going to All offers tab on the goods's pages
     * @return this
     */
    public AppleIPhone7Page goToAllOffersTab() {
        allOffersTab.click();
        return this;
    }

    /**
     * collecting all offers to the List of WebElements
     * @return this
     */
    public AppleIPhone7Page getAllOffers() {
        allOffers = findAllElements(By.xpath("//*[@data-selector=\"price-line\"]"));
        return this;
    }

    /**
     * printing all offers collected in WebElemets List
     * @return this
     */
    public AppleIPhone7Page printAllOffers() {
        int i = 0;
        for (WebElement element :
                allOffers) {
            System.out.println(++i + " - " + element.getAttribute("data-id"));
        }
        return this;
    }

    /**
     * filtering all shops represented on a page by quantity of reviews
     * @param minReviews minimum reviews that will be taken in account during filtering
     * @return
     */
    public AppleIPhone7Page filterOffersWithReviewsNotLessThen(int minReviews) {
        Iterator<WebElement> iterator = allOffers.listIterator();
        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPathToLineShop = "//*[@data-id=\"" + elementDataId + "\"]";
            String xPathToReview = xPathToLineShop + "//a[@class=\"sum g_statistic\"]";
            String review;
            int reviewCount;

            if (isElementOnPage(By.xpath(xPathToReview))) {
                review = findElement(By.xpath(xPathToReview)).getText().replaceAll("\\D+","");
                reviewCount = Integer.parseInt(review);

                if (reviewCount < minReviews) {
                    iterator.remove();
                }
            }
        }
        return this;
    }

    /**
     * filtering all shops represented on a page by quantity of warranty months given by seller
     * @param minMonthsWarranty minimum months that will be taken in account during filtering
     * @return
     */
    public AppleIPhone7Page filterOffersWithWarrantyMonthsNotLessThen(int minMonthsWarranty) {
        Iterator<WebElement> iterator = allOffers.listIterator();
        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPathToLineShop = "//*[@data-id=\"" + elementDataId + "\"]";
            String xPathToWarranty = xPathToLineShop + "//*[@class=\"delivery-th cell4 cell-1024-b p-10-0-1024-b\"]";

            String warranty = findElement(By.xpath(xPathToWarranty)).getText().replaceAll("\\D+","");
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

    /**
     * choosing the lowest price shop for current goods
     */
    public void chooseTheCheapestElement() {
        Iterator<WebElement> iterator = allOffers.listIterator();
        String theCheapestElementDataId = "";
        double cheapestPrice = 999999999;

        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPathToLineShop = "//*[@data-id=\"" + elementDataId + "\"]";
            String xPathToGoToShopBtn = xPathToLineShop + "//a[@id=\"gotoshop-price\"]";
            String price = findElement(By.xpath(xPathToGoToShopBtn)).getText().replaceAll(" ", "");
            price = price.replaceAll(",", ".");

            if (!price.isEmpty() && cheapestPrice > Double.parseDouble(price)) {
                cheapestPrice = Double.parseDouble(price);
                theCheapestElementDataId = elementDataId;
            }
        }

        findElement(By.xpath("//*[@data-id=\"" + theCheapestElementDataId
                + "\"]//*[@class=\"price txt-right cell5 cell-768 m_b-10-768\"]")).click();
    }

    /**
     * switching browsers tabs
     * @param index index in array list of browser's tabs that will be chosen
     */
    public void switchTab(int index) {
        List<String> browserTabs = new ArrayList<>(getDriver().getWindowHandles());

        try {
            getDriver().switchTo().window(browserTabs.get(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Warning: There is only one Browser's tab opened.\n" +
                    "It is not possible to switch to not existed tab.");
        }
    }

    /**
     * filtering all shop elements on a page which are represented in the DOM but not visible on a page
     * @return this
     */
    public AppleIPhone7Page filterInvisibleElements() {
        Iterator<WebElement> iterator = allOffers.listIterator();

        while (iterator.hasNext()) {
            WebElement element = iterator.next();

            String elementDataId = element.getAttribute("data-id");
            String xPathToLineShop = "//*[@data-id=\"" + elementDataId + "\"]";

            if (!isElementDisplayed(By.xpath(xPathToLineShop))) {
                iterator.remove();
            }
        }
        return this;
    }
}
