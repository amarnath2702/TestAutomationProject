package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.BasePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003LogindDDT extends BaseClass {

    @Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class,groups="DataDriven")
    public void verify_Login(String email,String pwd,String exp)
    {
        logger.info("Login Test case started....");
        try
        {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);

            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLoginBtn();

            MyAccountPage accPage = new MyAccountPage(driver);

            boolean targetPage = accPage.isMyAccountPageExists();


           if(exp.equalsIgnoreCase("Valid"))
           {
               if (targetPage)
               {
                   Assert.assertTrue(true);
                   accPage.clickLogout();
               }
               else
               {
                   Assert.assertTrue(false);
               }
           }


           if(exp.equalsIgnoreCase("Invalid"))
           {
               if(targetPage)
               {
                   Assert.assertTrue(false);
                   accPage.clickLogout();
               }
               else
               {
                   Assert.assertTrue(true);
               }
           }



        }
        catch(Exception e)
        {
            Assert.fail();
        }
        logger.info("**** Login Test Finished ****");

    }

}
