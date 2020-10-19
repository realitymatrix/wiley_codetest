import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Test {

    public static List<WebElement> getChildElements(WebElement element, By method){

        try
        {
            List<WebElement> children = element.findElements(method);
            /*
            for (WebElement e : children) {
                System.out.println(e.getText());  //Uncomment for debugging purposes
            }
            */
            return children;

        } finally{
            //System.out.print("\n child elements from " + element.getAttribute("class").toString() + " retrieved.");   //Uncomment for debugging purposes
        }
    }

    public static void part1case1(WebDriver driver){
        //PART 1.1
        //Opens Wiley's main site
        String HOME_URL = "https://www.wiley.com/en-us"; 
        driver.get(HOME_URL);
        //Confirms test has begun
        Assert.assertEquals(HOME_URL, driver.getCurrentUrl());
        //Confirms region to proceed testing
        driver.findElement(By.cssSelector("#country-location-form > div.modal-footer > button.changeLocationConfirmBtn.button.large.button-teal")).click();
        //Saves expected resources to string list data type
        List<String> resources = Arrays.asList("Students","Textbook Rental", "Instructors", "Book Authors", "Professionals", "Researchers", "Institutions", "Librarians", "Corporations", "Societies", "Journal Editors","Bookstores", "Government" );
        //Confirms expected result list size
        Assert.assertEquals(13, resources.size());
        //Finds parent resource element
        WebElement whoweserve = driver.findElement(By.cssSelector("#Level1NavNode1 > ul"));

        List<WebElement> resourceElements = getChildElements(whoweserve, By.className("dropdown-item"));
        //Verifies correct number of resource elements
        Assert.assertEquals(13, resourceElements.size());

        //Initialization of data type to store detected resources
        List<String> resourceContents = new ArrayList<String>();

        for (WebElement webElement : resourceElements) {
            resourceContents.add(webElement.findElement(By.tagName("a")).getAttribute("text").toString().trim());   //extra spaces trimmed
        }
        //Prints resource contents and list size (for debugging purposes)
        //System.out.print(resourceContents);
        //System.out.println("\n" + resourceContents.size() + " resources found.");

        //Verifies correct resouce contents
        Assert.assertEquals(resources,resourceContents);
        //Prints test validation
        System.out.print("\n 1.1 passed.\n");
    }

    public static void part1case2(WebDriver driver){
        //PART 1.2
        //Finds search box and types "Java"
        driver.findElement(By.cssSelector("#js-site-search-input")).sendKeys("Java");
        //Finds the autocomplete box and confirms that it's visible
        //Creates an explicit wait for the autocomplete box to dynamically load
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement autocomplete = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ui-id-2")));
        //Verifies that the element has dynamically loaded
        Assert.assertTrue(autocomplete.isDisplayed());
        //Prints test validation
        System.out.print("\n 1.2 passed.\n");
    }

    public static void part1case3(WebDriver driver){
        //PART 1.3
        //Starts search for "Java"
        driver.findElement(By.cssSelector("#main-header-container > div > div.main-navigation-search > div > form > div > span > button")).click();

        //Waits until the search results page has fully loaded
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#search-result-page-row > div.search-result-content > div > div.search-result-tabs-wrapper > div > div > div > div.products-list")));

        //Finds the product list element and reads child element titles
        WebElement queryResults = driver.findElement(By.className("products-list"));
        //Retries all child elements that are of the "product-title" class
        List<WebElement> searchTitles = getChildElements(queryResults, By.className("product-title"));

        //Initialization of data type to store detected resources
        List<String> queryContents = new ArrayList<String>();

        //Retrieves all strings from each "product-title" element's contents
        for (WebElement webElement : searchTitles) {
            queryContents.add(webElement.findElement(By.tagName("a")).getAttribute("text").toString().trim());   //extra spaces trimmed
        }

        //Prints the number of titles that were found
        //System.out.print("\n" + queryContents.size() + " query results found.\n");    //Uncomment for debugging purposes
        //Verifies correct number of search results found
        Assert.assertEquals(10, queryContents.size());

        //Verifies that each title string contains the character array "Java"
        for(String str : queryContents) {
            Assert.assertTrue(str.contains("Java"));
        }

        List<WebElement> products = getChildElements(queryResults, By.className("product-item"));

        //Each search result undergoes a check for presence of "Add to Card" functionality
        //This test case DOES NOT check whether the product is sold-out or not!!
        for (WebElement product : products) {
            Assert.assertTrue(product.findElement(By.className("product-button")).isDisplayed());
        }
        //Prints test validation
        System.out.print("\n 1.3 passed.\n");
    }
    public static void part1case4(WebDriver driver){
        //PART 1.4
        //Expected results for the "Education" subheader
        List<String> subjectTitles = Arrays.asList("Assessment, Evaluation Methods", "Classroom Management", "Conflict Resolution & Mediation", "Curriculum Tools", "Education & Public Policy", "Educational Research", "General Education", "Higher Education", "Information & Library Science", "Special Education", "Special Topics", "Vocational Technology");
        //Finds the "Subjects" dropdown menu and hovers over it
        new Actions(driver).moveToElement(driver.findElement(By.cssSelector("#main-header-navbar > ul.navigation-menu-items.initialized > li:nth-child(2)"))).perform();
        //Waits until the "Subjects" dopdown menu dynamically loaded
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Level1NavNode2 > ul > li:nth-child(9) > a")));
        //Verifies that "Education" subheader present
        Assert.assertTrue(driver.findElement(By.cssSelector("#Level1NavNode2 > ul > li:nth-child(9)")).isDisplayed());
        //Save "Education" subheader WebElement as a local variable
        WebElement education = driver.findElement(By.cssSelector("#Level1NavNode2 > ul > li:nth-child(9)"));
        //Hovers over the "Education" subheader
        new Actions(driver).moveToElement(education).perform();

        //Retieves list of items under "Education" subheader
        List<WebElement> educationSubheaders = getChildElements(education, By.className("dropdown-item"));

        //Initialization of list to store text from each dropdown-item
        List<String> educationContents = new ArrayList<String>();
        //Populate list with dropdown-item text
        for(WebElement webElement : educationSubheaders) {
            WebElement a = webElement.findElement(By.tagName("a"));
            educationContents.add(a.getAttribute("text"));
        }
        //Print the contents of the list (for debugging)
        //System.out.print(educationContents);

        //Validate there are 13 education subheaders
        Assert.assertEquals(12, educationSubheaders.size());
        //Validate all education subheaders are displayed
        for(WebElement webElement : educationSubheaders) {
            Assert.assertTrue(webElement.isDisplayed());
        }

        int i = 0;
        for(String s : educationContents) {
            Assert.assertTrue(s.contains(subjectTitles.get(i)));
            i++;
        }

        //Prints test validation
        System.out.print("\n 1.4 passed.\n");
    }
    //Execute PART 1 test cases
    public static void part1(WebDriver driver){

        try{
            part1case1(driver);   //Part 1.1
            part1case2(driver);   //Part 1.2
            part1case3(driver);   //Part 1.3
            part1case4(driver);   //Part 1.4
        }
        finally{
            System.out.print("PART 1 VALIDATED");
            driver.close();
        }
    }

    public static void main(String[] args) throws Exception {
        
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //Execute tests in PART 1
        part1(driver);
        
    }
}
