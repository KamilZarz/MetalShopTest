import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MetalShopTests {


    static WebDriver driverChrome;

    @BeforeEach
    void cleanCookies() {
        driverChrome.manage().deleteAllCookies();
    }

    @BeforeAll
    static void prepareBrowser() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriverManager.chromedriver().setup();
        driverChrome = new ChromeDriver();
        driverChrome.manage().window().maximize();
        driverChrome.get(" http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
    }

    @AfterEach
    void cleanCookiesAfterTest() {
        driverChrome.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser(){
        driverChrome.quit();
    }

        @Test
        void shouldVerifyNegativeLoginWithoutUsername() {
            WebElement myAccountMenuItem = driverChrome.findElement(By.id("menu-item-125"));
            myAccountMenuItem.click();

            WebElement inputPassword = driverChrome.findElement(By.xpath("//*[@id='password']"));
            inputPassword.sendKeys("KXVMHcx");

            WebElement buttonLogin = driverChrome.findElement(By.cssSelector("button[name='login']"));
            buttonLogin.click();

            WebElement errorMessage = driverChrome.findElement(By.className(("woocommerce-error")));
            String expectedErrorMessage = "Błąd: Nazwa użytkownika jest wymagana.";
            Assertions.assertEquals(expectedErrorMessage, errorMessage.getText());
        }

    @Test
    void shouldVerifyNegativeLoginWithoutPassword() {
        WebElement myAccountMenuItem = driverChrome.findElement(By.id("menu-item-125"));
        myAccountMenuItem.click();

        WebElement inputAccount = driverChrome.findElement(By.xpath("//*[@id='username']"));
        inputAccount.sendKeys("tester");

        WebElement buttonLogin = driverChrome.findElement(By.cssSelector("button[name='login']"));
        buttonLogin.click();

        WebElement errorMessage = driverChrome.findElement(By.className(("woocommerce-error")));
        String expectedErrorMessage = "Błąd: pole hasła jest puste.";
        Assertions.assertEquals(expectedErrorMessage, errorMessage.getText());
    }


    @Test
    void shouldVerifyPositiveRegistrationNewAccount() {

        WebElement registrationMenuItem = driverChrome.findElement(By.id("menu-item-146"));
        registrationMenuItem.click();

        WebElement inputUsername = driverChrome.findElement(By.cssSelector("#user_login"));
        inputUsername.sendKeys("metalshoptest6");
        WebElement inputUserEmail = driverChrome.findElement(By.cssSelector("#user_email"));
        inputUserEmail.sendKeys("metalshoptest6@yopmail.com");
        WebElement inputUserPass = driverChrome.findElement(By.cssSelector("#user_pass"));
        inputUserPass.sendKeys("QWERTYqwerty123!");
        WebElement inputUserConfirmPass = driverChrome.findElement(By.cssSelector("#user_confirm_password"));
        inputUserConfirmPass.sendKeys("QWERTYqwerty123!");

        WebElement buttonSubmit = driverChrome.findElement(By.xpath("//button[contains(.,'Submit')]"));
        buttonSubmit.click();

        WebDriverWait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(5));
        WebElement registrationConfirm =
                wait.until(ExpectedConditions.
                        visibilityOfElementLocated(By.xpath("//*[@id='ur-submit-message-node']")));
        String registrationConfirmText = "User successfully registered.";
        Assertions.assertTrue(registrationConfirm.getText().contains(registrationConfirmText));
    }

    @Test
    void shouldVerifyContainLogoInHomeAndLoginPage()
    {
        List<WebElement> elements = driverChrome.findElements(By.id("woocommerce-product-search-field-0"));
        Assertions.assertEquals(1, elements.size());

        WebElement logo = driverChrome.findElement(By.xpath("//a[@rel='home']"));
        String expectedLogoText = "Softie Metal Shop";
        Assertions.assertEquals(expectedLogoText, logo.getText());

        WebElement myAccountMenuItem = driverChrome.findElement(By.id("menu-item-125"));
        myAccountMenuItem.click();

        Assertions.assertEquals(1, elements.size());

        WebElement logo2 = driverChrome.findElement(By.xpath("//a[@rel='home']"));
        String expectedLogoText2 = "Softie Metal Shop";
        Assertions.assertEquals(expectedLogoText2, logo2.getText());
    }

    @Test
    void verifyContactMenuItem() {
        WebElement myContactMenuItem = driverChrome.findElement(By.id("menu-item-132"));
        myContactMenuItem.click();

        WebElement ContactMenuItem = driverChrome.findElement(By.cssSelector(".entry-header .entry-title"));
        String expectedMenuItem = "Kontakt";
        Assertions.assertEquals(expectedMenuItem, ContactMenuItem.getText());
    }


    @Test
    void verifyMainPageItem() {

        driverChrome.get(" http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        WebElement mainPageItem = driverChrome.findElement(By.id("menu-item-124"));
        mainPageItem.click();

        WebElement mainPageVerify = driverChrome.findElement(By.className("woocommerce-products-header__title"));
        String expectedMainPage = "Sklep";
        Assertions.assertEquals(expectedMainPage, mainPageVerify.getText());
    }

    @Test
    void verifySendMsgInContact() {

        WebElement contactMenuItem = driverChrome.findElement(By.id("menu-item-132"));
        contactMenuItem.click();

        WebElement inputYourName = driverChrome.findElement(By.name("your-name"));
        inputYourName.sendKeys("Kamil Test");
        WebElement inputEmailAdress = driverChrome.findElement(By.name("your-email"));
        inputEmailAdress.sendKeys("metalshoptest@yopmail.com");
        WebElement inputSubject = driverChrome.findElement(By.name("your-subject"));
        inputSubject.sendKeys("Test");
        WebElement inputTextMsg = driverChrome.findElement(By.name("your-message"));
        inputTextMsg.sendKeys("Test strony");
        
        driverChrome.findElement(By.name("your-subject")).sendKeys(Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(5));
        WebElement errorMsg =
                wait.until(ExpectedConditions.
                        visibilityOfElementLocated(By.className("wpcf7-response-output")));
        String errorMsgText = "Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.";
        Assertions.assertTrue(errorMsg.getText().contains(errorMsgText));
    }

    @Test
    void verifyCartIsEmptyAndAfterAddItemsVerifyCartIsNotEmpty() {

        driverChrome.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/koszyk/");
        WebElement emptyCart = driverChrome.findElement(By.className("cart-empty"));
        String expectedEmptyCart = "Twój koszyk aktualnie jest pusty.";
        Assertions.assertEquals(expectedEmptyCart, emptyCart.getText());

        WebElement myContactMenuItem = driverChrome.findElement(By.id("menu-item-124"));
        myContactMenuItem.click();

        WebElement silverCoin = driverChrome.findElement(By.className("post-24"));
        silverCoin.click();
        WebElement silverCoinAddButton = driverChrome.findElement(By.className("single_add_to_cart_button"));
        silverCoinAddButton.click();

        WebDriverWait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(5));
        WebElement addCoinToCartMsg =
                wait.until(ExpectedConditions.
                        visibilityOfElementLocated(By.className("woocommerce-message")));
        String expectedAddCoinToCartMsg = "„Srebrna moneta 5g – UK 1980” został dodany do koszyka.";
        Assertions.assertTrue(addCoinToCartMsg.getText().contains(expectedAddCoinToCartMsg));

    }




    }


