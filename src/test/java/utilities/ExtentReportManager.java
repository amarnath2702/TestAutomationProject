package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener
{
    public ExtentSparkReporter sparkReporter;
    public ExtentReports reports;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext)
    {
        String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName="Test-Report-"+timeStamp+".html";
        sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
        sparkReporter.config().setDocumentTitle("openCart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        reports=new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Application","opencart");
        reports.setSystemInfo("Module","Admin");
        reports.setSystemInfo("Sub Module","Admin");
        reports.setSystemInfo("User Name",System.getProperty("user.name"));
        reports.setSystemInfo("Environment","QA");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        reports.setSystemInfo("Operating System",os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        reports.setSystemInfo("Browser",browser);

        List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty())
        {
            reports.setSystemInfo("Groups",includedGroups.toString());
        }

    }

    public void onTestSuccess(ITestResult result)
    {
       test=reports.createTest(result.getTestClass().getName());
       test.assignCategory(result.getMethod().getGroups());
       test.log(Status.PASS,result.getName()+" got successful Executed");

    }
    public void onTestFailure(ITestResult result)
    {
        test=reports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL,result.getName()+" got failed");
        test.log(Status.INFO,result.getThrowable().getMessage());

        try{
            String imgPath=new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onTestSkipped(ITestResult result)
    {
        test=reports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP,result.getName()+" got Skipped");
        test.log(Status.INFO,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext)
    {
        reports.flush();

        String pathofExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport=new File(pathofExtentReport);
        try
        {
            Desktop.getDesktop().browse(extentReport.toURI());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}