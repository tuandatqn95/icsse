package com.example.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pages")
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "slug")
	private String slug;

	@Column(name = "userid")
	private int userID;

	@ManyToOne
	@JoinColumn(name = "userid", nullable = false, updatable = false, insertable = false)
	private User user;

	@OneToMany(mappedBy = "page")
	private Set<DriveFile> driveFiles;

	public Set<DriveFile> getDriveFiles() {
		return driveFiles;
	}

	public void setDriveFiles(Set<DriveFile> driveFiles) {
		this.driveFiles = driveFiles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Page() {
		super();
	}

	public Page(int id, String title, String content, String slug, int userID) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.slug = slug;
		this.userID = userID;
	}

}
