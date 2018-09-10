package com.stackroute.qna.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="COMMENTS", catalog="QNADB")
public class CommentEntity {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(name="DESCRIPTION")
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	@JoinColumn(name="id", nullable=false)
	private QuestionEntity question;
	
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
	public QuestionEntity getQuestion() {
		return question;
	}
	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}
	@Override
	public String toString() {
		return "CommentEntity [id=" + id + ", description=" + description + ", createdOn=" + createdOn + ", question="
				+ question + "]";
	}

}
