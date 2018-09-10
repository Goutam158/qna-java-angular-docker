package com.stackroute.qna.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="QUESTIONS", catalog="QNADB")
public class QuestionEntity {

	@Id
	@GeneratedValue
	private int id;
	@Column(name="DESCRIPTION")
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	private Date createdOn;
	@OneToMany(mappedBy="question")
	private Set<CommentEntity> comments;
	@JoinColumn(name="id", nullable=false)
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
	public Set<CommentEntity> getComments() {
		return comments;
	}
	public void setComments(Set<CommentEntity> comments) {
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
		return "QuestionEntity [id=" + id + ", description=" + description + ", createdOn=" + createdOn + ", comments="
				+ comments + ", topic=" + topic + "]";
	}
	
}
