package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002LoginTest extends BaseClass
{


    @Test(groups={"Sanity","Master"})
    public void verify_Login()
    {
        logger.info("Login Test case started....");
     try
       {
         HomePage hp = new HomePage(driver);
         hp.clickMyAccount();
         hp.clickLogin();

         LoginPage lp = new LoginPage(driver);
         lp.setEmail(p.getProperty("email"));
         lp.setPassword(p.getProperty("password"));
         lp.clickLoginBtn();

         MyAccountPage accPage = new MyAccountPage(driver);

         boolean targetPage = accPage.isMyAccountPageExists();
         Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage,true,"Login Failed");

           accPage.clickLogout();

     }
     catch(Exception e)
     {
         Assert.fail();
     }
        logger.info("**** Login Test Finished ****");

    }

}
