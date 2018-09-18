import { AppPage } from "./app.po";
import { browser } from 'protractor';

describe("question-n-answers App", () => {
  let page: AppPage;
  let password = 'Test@1234';
  let email = 'dhiman@t.com';

  beforeEach(() => {
    page = new AppPage();
  });

  it("should display app heading", () => {
    page.navigateTo();
    expect(page.getHeadingText()).toEqual("QnA");
  });


  it("should navigate to the login page", () => {
    expect(browser.getCurrentUrl()).toContain('/#/login');
  });

  it("should be able to login", () => {
    page.getEmailField().sendKeys(email);
    page.getPasswordField().sendKeys(password);
    page.clickLoginButton();
    expect(browser.getCurrentUrl()).toContain('/#/dashboard');
  });

  it("should be able to view 5 topics", () => {
    expect(page.getTopicCards().count()).toEqual(5);
  });

  it("should be able to view details", () => {
    page.clickViewTopicDetailsButton();
    expect(browser.getCurrentUrl()).toContain('/#/topic-details');
  });


});
