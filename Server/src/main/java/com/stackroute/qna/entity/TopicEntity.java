package com.stackroute.qna.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TOPICS", catalog="qnadb")
public class TopicEntity {
	
	@Id
	@GeneratedValue
	private int id;
	@Column(name="NAME")
	private String name;
	@Column(name="DESCRIPTION")
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	private Date createdOn;
	@OneToMany(mappedBy="topic")
	private Set<QuestionEntity> questions;
	@ManyToOne()
	@JoinColumn(name="user_id",referencedColumnName="user_id" ,nullable=false)
	private UserEntity createdBy;
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
	public Set<QuestionEntity> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<QuestionEntity> questions) {
		this.questions = questions;
	}
	public UserEntity getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "TopicEntity [id=" + id + ", name=" + name + ", description=" + description + ", createdOn=" + createdOn
				+ ", questions=" + questions + ", createdBy=" + createdBy + "]";
	}
	

}
