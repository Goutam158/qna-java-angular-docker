package com.stackroute.qna.TO;

import java.util.Date;
import java.util.Set;

public class TopicTO {
	
	private int id;
	private String name;
	private String description;
	private Date createdOn;
	private Set<QuestionTO> questions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Set<QuestionTO> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<QuestionTO> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "TopicTO [id=" + id + ", name=" + name + ", description=" + description + ", createdOn=" + createdOn
				+ ", questions=" + questions + "]";
	}
	
	

}
