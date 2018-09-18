import { browser, by, element } from "protractor";

export class AppPage {
  navigateTo() {
    return browser.get("/");
  }

  getHeadingText() {
    return element(by.id(".app-heading")).getText();
  }

  getFirstNameField(){
    return element(by.id('#first-name-field'));
  }

  getLastNameField(){
    return element(by.id('#last-name-field'));
  }

  getEmailField(){
    return element(by.id('#email-field'));
  }

  getPasswordField(){
    return element(by.id('#password-field'));
  }

  getRetypePasswordField(){
    return element(by.id('#retype-password-field'));
  }

  clickLoginButton(){
    return element(by.id('#btn-login')).click();
  }

  clickSignUpButton(){
    return element(by.id('#btn-signup')).click();
  }

  getTopicCards(){
    return element.all(by.id('.topic-card'));
  }

  getQuestionCards(){
    return element.all(by.id('.question-card'));
  }

  getCommentCards(){
    return element.all(by.id('.comment-card'));
  }

  clickViewTopicDetailsButton(){
    element.all(by.id('.view-topic-details')).first().click();
  }

  clickViewCommentsButton(){
    element.all(by.id('.view-comments')).first().click();
  }

}
