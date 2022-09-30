package Com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DriverUtility {
    static WebDriver driver;
    private HashMap<String, String> urlKeys;

    private DriverUtility() throws Exception {
        System.setProperty("webdriver.chrome.driver", OpenBrowser.CHROME_DRIVER_PATH);
        Properties prop = new Properties();

        FileInputStream FileInputStream = new FileInputStream(
                new File("src/main/resources/object.properties"));
        prop.load(FileInputStream);
        urlKeys = new HashMap<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); //for headless mode
        options.addArguments("--window-size=800,600");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox");


        // urlKeys.put(OpenBrowser.CLICK_BUTTON_KEY, prop.getProperty(OpenBrowser.CLICK_BUTTON_KEY));
        urlKeys.put(OpenBrowser.FIRST_NAME_INPUT_KEY, prop.getProperty(OpenBrowser.FIRST_NAME_INPUT_KEY));
        urlKeys.put(OpenBrowser.LAST_NAME_INPUT_KEY, prop.getProperty(OpenBrowser.LAST_NAME_INPUT_KEY));
        urlKeys.put(OpenBrowser.ENTER_EMAIL_INPUT_KEY, prop.getProperty(OpenBrowser.ENTER_EMAIL_INPUT_KEY));
        urlKeys.put(OpenBrowser.RADIO_BUTTON_MALE_KEY, prop.getProperty(OpenBrowser.RADIO_BUTTON_MALE_KEY));
        urlKeys.put(OpenBrowser.RADIO_BUTTON_FEMALE_KEY, prop.getProperty(OpenBrowser.RADIO_BUTTON_FEMALE_KEY));
        urlKeys.put(OpenBrowser.ENTER_NUMBER_KEY, prop.getProperty(OpenBrowser.ENTER_NUMBER_KEY));
        urlKeys.put(OpenBrowser.OPEN_CALENDER_KEY, prop.getProperty(OpenBrowser.OPEN_CALENDER_KEY));
        urlKeys.put(OpenBrowser.MONTH_YEAR_BUTTON_KEY, prop.getProperty(OpenBrowser.MONTH_YEAR_BUTTON_KEY));
        urlKeys.put(OpenBrowser.SIDE_BUTTON_KEY, prop.getProperty(OpenBrowser.SIDE_BUTTON_KEY));
        urlKeys.put(OpenBrowser.DAY_BUTTON_KEY, prop.getProperty(OpenBrowser.DAY_BUTTON_KEY));
        urlKeys.put(OpenBrowser.SEND_INFO_BUTTON_KEY, prop.getProperty(OpenBrowser.SEND_INFO_BUTTON_KEY));
        urlKeys.put(OpenBrowser.DISPLAY_KEY, prop.getProperty(OpenBrowser.DISPLAY_KEY));

        driver = new ChromeDriver(options);

       // driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

    boolean performTest(SheetColumnHeader sheetColumnHeader) throws InterruptedException, IOException {
        try {


            driver.get(OpenBrowser.URL1);
            System.out.println("**Launching Chrome Browser**");

           //handle first name field
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.FIRST_NAME_INPUT_KEY))).sendKeys(sheetColumnHeader.getFirstname());
             //handle last name field
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.LAST_NAME_INPUT_KEY))).sendKeys(sheetColumnHeader.getLastname());
            //handle number field
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.ENTER_EMAIL_INPUT_KEY))).sendKeys(sheetColumnHeader.getEmailId());
            //handle radio button with male and female option
            if (sheetColumnHeader.getGender().equals("male")) {
                driver.findElement(By.xpath(urlKeys.get(OpenBrowser.RADIO_BUTTON_MALE_KEY))).click();
            } else {
                driver.findElement(By.xpath(urlKeys.get(OpenBrowser.RADIO_BUTTON_FEMALE_KEY))).click();
            }

           //handle number field
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.ENTER_NUMBER_KEY))).sendKeys(String.valueOf(sheetColumnHeader.getNumber()));

            //Now handle the calender dates with excel sheet
            WebElement table = driver.findElement(By
                    .cssSelector(urlKeys.get(OpenBrowser.OPEN_CALENDER_KEY)));

            table.click();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sheetColumnHeader.getDate());

            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();

            String month_year = months[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR);


            while(!driver.findElement(By.xpath(urlKeys.get(OpenBrowser.MONTH_YEAR_BUTTON_KEY))).getText().contains(month_year))
            {
                driver.findElement(By.xpath(urlKeys.get(OpenBrowser.SIDE_BUTTON_KEY))).click();
            }


            List<WebElement> dates= driver.findElements(By.cssSelector(urlKeys.get(OpenBrowser.DAY_BUTTON_KEY)));
            int count=driver.findElements(By.cssSelector(urlKeys.get(OpenBrowser.DAY_BUTTON_KEY))).size();


            for(int i=0;i<count;i++)
            {
                String text=driver.findElements(By.cssSelector(urlKeys.get(OpenBrowser.DAY_BUTTON_KEY))).get(i).getText();
                if(text.equalsIgnoreCase(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))
                {
                    driver.findElements(By.cssSelector(urlKeys.get(OpenBrowser.DAY_BUTTON_KEY))).get(i).click();
                    break;
                }

            }

           takeScreenshot("last1");

            //handle send button
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.SEND_INFO_BUTTON_KEY))).submit();
            //display all details form
            driver.findElement(By.cssSelector(urlKeys.get(OpenBrowser.DISPLAY_KEY))).isDisplayed();
            Thread.sleep(2000);
        } catch (Exception p) {
            return false;
        }
        return true;


    }


    void shutdownDriver() {

        driver.quit();

        //driver.close();
        System.out.println("I am closing Browser ");
    }


    public static void takeScreenshot( String filename) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(".//test-output//Screenshot//" + filename + ".png"));

    }

    public static DriverUtility getInstance() throws Exception {
        return new DriverUtility();
    }





}
