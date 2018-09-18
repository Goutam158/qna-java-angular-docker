package com.stackroute.qna.TO;

import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

import com.stackroute.qna.entity.TopicEntity;

public class QuestionTO {

	private int id;
	@NotBlank
	private String description;
	private Date createdOn;
	private Set<CommentTO> comments;
	private TopicTO topic;
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
	public Set<CommentTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentTO> comments) {
		this.comments = comments;
	}
	public TopicTO getTopic() {
		return topic;
	}
	public void setTopic(TopicTO topic) {
		this.topic = topic;
	}
	public UserTO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserTO createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "QuestionTO [id=" + id + ", description=" + description + ", createdOn=" + createdOn + ", comments="
				+ comments + ", topic=" + topic + ", createdBy=" + createdBy + "]";
	}
	
}
