package com.ncube.test.reporter;

import com.ncube.test.configuration.TestsConfig;
import org.testng.ITestResult;
import org.uncommons.reportng.ReportNGUtils;

import java.util.List;

public class ReportUtils extends ReportNGUtils {

    public List<String> getTestOutput(final ITestResult result) {
        final List<String> output = super.getTestOutput(result);
        final Exception error = (Exception) result.getAttribute("reportGeneratingException");

        if (error != null) {
            output.add("Generating report error: " + error);
            return output;
        }

        final String screenshot = (String) result.getAttribute("screenshot");

        if (screenshot != null) {
            String url = (String) result.getAttribute("screenshotURL");
            String screenshotDir = TestsConfig.getConfig().getScreenshotsDir();
            output.add("<a href=\"" + screenshotDir + screenshot + "\" target=\"+blank\">Screenshot</a> for the page " + url);
        }
        return output;
    }
}
