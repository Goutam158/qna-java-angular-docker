package com.stackroute.qna.TO;

import java.util.Date;
import java.util.Set;

import com.stackroute.qna.entity.TopicEntity;

public class QuestionTO {

	private int id;
	private String description;
	private Date createdOn;
	private Set<CommentTO> comments;
	private TopicEntity topic;
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
	public TopicEntity getTopic() {
		return topic;
	}
	public void setTopic(TopicEntity topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "QuestionTO [id=" + id + ", description=" + description + ", createdOn=" + createdOn + ", comments="
				+ comments + ", topic=" + topic + "]";
	}
	
}
