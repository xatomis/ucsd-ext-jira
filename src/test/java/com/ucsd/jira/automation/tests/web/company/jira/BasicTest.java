package com.ucsd.jira.automation.tests.web.company.jira;

import com.pwc.core.framework.annotations.Issue;
import com.pwc.core.framework.listeners.Retry;
import com.ucsd.jira.automation.data.Constants;
import com.ucsd.jira.automation.data.enums.LeftMenu;
import com.ucsd.jira.automation.frameworksupport.Groups;
import com.ucsd.jira.automation.frameworksupport.JiraTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.pwc.logging.service.LoggerService.FEATURE;
import static com.pwc.logging.service.LoggerService.GIVEN;
import static com.pwc.logging.service.LoggerService.SCENARIO;
import static com.pwc.logging.service.LoggerService.THEN;
import static com.pwc.logging.service.LoggerService.WHEN;


public class BasicTest extends JiraTestCase {

    @Override
    public void beforeMethod() {
    }

    @Override
    public void afterMethod() {
    }

    @Issue("STORY-1234")
    @Test(retryAnalyzer = Retry.class, groups = {Groups.ACCEPTANCE_TEST})
    public void testBasic() {
        FEATURE("Modified Jira Test");
        SCENARIO("User logs in and submits a new User Story after populating the required fields");

        GIVEN("I am a valid user who wants to create a User Story");
        // Verify that the default "test" dashboard heading is visible
        webElementVisible(Constants.NEW_TEST_HEADING);

        WHEN("I navigate with the left menu");
        // Click 'Create' menu item
        webAction(Constants.CREATE_DIV);
        // Check visibility of 'create' modal window
        webElementVisible(Constants.CREATE_HEADING);
        // Populate Summary field ~ //*[@id="summary"]
        WebElement summaryElement = webEventController.getWebEventService().getMicroserviceWebDriver().findElement(By.id("summary"));
        summaryElement.sendKeys(Constants.SUMMARY_TEXT);
        // Populate Description field ~ //*[@id="description"]
        WebElement bodyElement = webEventController.getWebEventService().getMicroserviceWebDriver().findElement(By.id("description"));
        bodyElement.sendKeys(Constants.BODY_TEXT);
        // Click "send" button
        webEventController.getWebEventService().getMicroserviceWebDriver().findElement(By.id("create-issue-submit")).click();

        THEN("The expected action is performed");
        // Return to dashboard view
        redirect(Constants.HOME_URL);

        /*
         * There's an intermittent problem here where sometimes the left menu isn't
         * expanded when returning to the home dashboard view. This causes subsequent
         * test actions to fail.
         */
        /*
        WebElement toggleButton = webEventController.getWebEventService().getMicroserviceWebDriver().findElement(By.id(Constants.TOGGLE_BUTTON));
        // Check to see if side menu is expanded; it needs to be if it isn't already
        if (toggleButton.getAttribute("aria-expanded").contains("false")) {
            WebElement webElement = webEventController.getWebEventService().getMicroserviceWebDriver().findElementByXPath("");
            webElement.sendKeys(String.valueOf('\u005b'));
        }
        */


        // Click 'Issues and Filters' menu item
        webAction(Constants.ISSUES_AND_FILTERS_DIV);
        // Click 'Created recently' menu item
        webAction(Constants.CREATED_RECENTLY_DIV);
        // Check visibility of 'Created recently' landing page heading
        webElementVisible(Constants.CREATED_RECENTLY_HEADING);
        // Now look for the Constants.SUMMARY_TEXT value on this page. It
        // should be visible here since you just created the User Story.
        Assert.assertTrue(webEventController.getWebEventService().getMicroserviceWebDriver().getPageSource().contains(Constants.SUMMARY_TEXT));

        /*
        FEATURE("Basic Jira Test sneak developer");
        SCENARIO("User logs in and validates basic navigation functionality");

        GIVEN("I am a valid user");
        webElementVisible(Constants.NEW_TEST_HEADING);

        WHEN("I navigate with the left menu");
        webAction(Constants.ISSUES_AND_FILTERS_DIV);
        redirect(Constants.HOME_URL);

        THEN("The expected pages are displayed");
        webAction(Constants.DASHBOARD_DIV);

        redirect(Constants.HOME_URL);
        */
    }

}
