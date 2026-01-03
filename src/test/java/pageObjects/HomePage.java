package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath="//span[normalize-space()='My Account']")
    WebElement lnkMyaccount;

    @FindBy(xpath="//a[normalize-space()='Register']")
    WebElement lnkRegiser;

    @FindBy(xpath="//a[@class='dropdown-item'][normalize-space()='Login']")
    WebElement linkLogin;


public void clickMyAccount()
{
    lnkMyaccount.click();
}

public void clickRegister()
{
    lnkRegiser.click();
}

public void clickLogin()
{
    linkLogin.click();
}
}
