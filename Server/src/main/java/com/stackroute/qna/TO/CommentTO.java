package com.stackroute.qna.TO;

import java.util.Date;

public class CommentTO {
	
	private int id;
	private String description;
	private Date createdOn;
	private QuestionTO question;
	private UserTO createdBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public QuestionTO getQuestion() {
		return question;
	}
	public void setQuestion(QuestionTO question) {
		this.question = question;
	}
	public UserTO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserTO createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "CommentTO [id=" + id + ", description=" + description + ", createdOn=" + createdOn + ", question="
				+ question + ", createdBy=" + createdBy + "]";
	}

}
