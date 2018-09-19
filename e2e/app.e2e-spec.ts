import { AppPage } from "./app.po";
import { browser } from 'protractor';

describe("question-n-answers App", () => {
  let page: AppPage;
  let firstName = "EntToEnd"
  let lastName = 'Test'
  let password = 'Test@1234';
  let email = 'e2e@test.com';
  let testQuestion = 'This is a sample question';
  let testComment = 'This is a sample comment';

  let topicDetailsUrl:string;
  let questionCount:number;
  let commentCount:number;

  beforeEach(() => {
    page = new AppPage();
  });

  it("should display app heading", () => {
    page.navigateTo();
    expect(page.getHeadingText()).toEqual("QnA");
  });

  it("should navigate to the signup page", () => {
    page.clickGotoSignUpButton();
    expect(browser.getCurrentUrl()).toContain('/#/signup');
  });

  it("should be able to signup", () => {
    page.getFirstNameField().sendKeys(firstName);
    page.getLastNameField().sendKeys(lastName);
    page.getEmailField().sendKeys(email);
    page.getPasswordField().sendKeys(password);
    page.getRetypePasswordField().sendKeys(password);
    page.clickSignUpButton();
    expect(browser.getCurrentUrl()).toContain('/#/login');
  });

  it("should be able to login", () => {
    page.getEmailField().sendKeys(email);
    page.getPasswordField().sendKeys(password);
    page.clickLoginButton();
    expect(browser.getCurrentUrl()).toContain('/#/dashboard');
  });

  it("should be able to view at least 5 topics", () => {
    expect(page.getTopicCards().count()).toBeGreaterThanOrEqual(5);
  });

  it("should be able to navigate to topic details view", () => {
    page.clickViewTopicDetailsButton();
    browser.getCurrentUrl().then(val => topicDetailsUrl = val);
    expect(browser.getCurrentUrl()).toContain('/#/topic-details');
  });

  it("should be able to view topic name", () => {
   expect(page.getTopicNameText()).toBeDefined();
  });

  it("should be able to add a question", () => {
    page.getQuestionCards().count().then(count => questionCount = count);
    page.getQuestionField().sendKeys(testQuestion);
    page.clickQuestionButton();
    expect(page.getQuestionCards().count()).toBeGreaterThanOrEqual(1);
    expect(page.getQuestionDescriptions().first().getText()).toEqual(testQuestion);
    expect(page.getQuestionDates().first()).toBeDefined();
   });

   it("should be able to navigate to question details view", () => {
    page.clickViewCommentsButton();
    expect(browser.getCurrentUrl()).toContain('/#/question-details');
  });

  it("should be able to view question", () => {
   expect(page.getQuestionText()).toBeDefined();
  });

  it("should be able to add a comment", () => {
    page.getCommentCards().count().then(count => commentCount = count);
    page.getCommentField().sendKeys(testComment);
    page.clickCommentButton();
    expect(page.getCommentCards().count()).toBeGreaterThanOrEqual(1);
    expect(page.getCommentDescriptions().first().getText()).toEqual(testComment);
    expect(page.getCommentDates().first()).toBeDefined();
   });


 /* it("should be able to delete comment", () => {
    console.log("============ COUNT +++++++ "+commentCount)
    page.clickDeleteCommentButton();
     if(page.getConfirmDialogYesButton().isDisplayed()){
        page.clickConfirmDialogYesButton();
        console.log('BUTTON CLICKED !!');
        expect(page.getCommentCards().count()).toEqual(commentCount);
      }else{
        console.log('BUTTON INVISIBLE !!!!');
      }
   });*/

 it("should be able to navigate to previous topic-details page", () => {
   browser.get(topicDetailsUrl);
    expect(browser.getCurrentUrl()).toEqual(topicDetailsUrl);
   });
 

  /*it("should be able to delete question", () => {
    page.clickDeleteQuestionButton();
    browser.pause(1000);
    page.clickConfirmDialogYesButton();
    expect(page.getQuestionCards().count()).toEqual(questionCount);
   });*/

  it("should be able to logout", () => {
    page.clickLogoutButton();
     expect(browser.getCurrentUrl()).toContain('/login');
  }); 

  it("should NOT be able to navigate to dashboard", () => {
    browser.get('/#/dashboard');
     expect(browser.getCurrentUrl()).toContain('/login');
  });

 


});
