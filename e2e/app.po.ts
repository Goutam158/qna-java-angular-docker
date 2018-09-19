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

  getQuestionField(){
    return element(by.id('#question-field'));
  }

  getCommentField(){
    return element(by.id('#comment-field'));
  }

  clickLoginButton(){
    return element(by.id('#btn-login')).click();
  }

  clickLogoutButton(){
    return element(by.id('#btn-logout')).click();
  }

  clickSignUpButton(){
    return element(by.id('#btn-signup')).click();
  }

  clickGotoSignUpButton(){
    return element(by.id('#btn-goto-signup')).click();
  }

  clickDeleteCommentButton(){
    return element(by.id('.delete-comment')).click();
  }

  getConfirmDialogYesButton(){
    return element(by.id('.confirm-dialog-yes'));
  }

  clickConfirmDialogYesButton(){
    return element(by.id('.confirm-dialog-yes')).click();
  }
  clickConfirmDialogNoButton(){
    return element(by.id('.confirm-dialog-no')).click();
  }


  clickDeleteQuestionButton(){
    return element(by.id('.delete-question')).click();
  }

  getTopicCards(){
    return element.all(by.id('.topic-card'));
  }

  getQuestionCards(){
    return element.all(by.id('.question-card'));
  }

  getQuestionDescriptions(){
    return element.all(by.id('.question-description'));
  }

  getQuestionDates(){
    return element.all(by.id('.question-date'));
  }

  getCommentDescriptions(){
    return element.all(by.id('.comment-description'));
  }

  getCommentDates(){
    return element.all(by.id('.comment-date'));
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

  clickQuestionButton(){
    return element(by.id('#question-button')).click();
  }

  clickCommentButton(){
    return element(by.id('#comment-button')).click();
  }

  getTopicNameText() {
    return element(by.id("text-h1-topic-details")).getText();
  }

  getQuestionText() {
    return element(by.id("text-h1-question")).getText();
  }

}
