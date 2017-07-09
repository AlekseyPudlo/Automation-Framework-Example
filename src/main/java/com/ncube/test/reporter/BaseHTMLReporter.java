package com.ncube.test.reporter;

import com.ncube.test.webtestsbase.WebDriverFactory;
import org.apache.velocity.VelocityContext;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.uncommons.reportng.HTMLReporter;

public class BaseHTMLReporter extends HTMLReporter implements ITestListener {
    private static final String UTILS_KEY = "utils";
    private static final ReportUtils REPORT_UTILS = new ReportUtils();

    protected VelocityContext createContext() {
        final VelocityContext context = super.createContext();
        context.put(UTILS_KEY, REPORT_UTILS);
        return context;
    }

    public void onTestSuccess(ITestResult result) {
        WebDriverFactory.takeScreenShot(result);
    }

    public void onTestFailure(final ITestResult result) {
        WebDriverFactory.takeScreenShot(result);
    }

    public void onTestStart(ITestResult iTestResult) {}

    public void onTestSkipped(ITestResult iTestResult) {}

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}

    public void onStart(ITestContext iTestContext) {}

    public void onFinish(ITestContext iTestContext) {}
}
