package com.test.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "library")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Library implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "group_name", nullable = false)
	private String groupName;
	
	@NotNull
	@Column(name = "ranking", nullable = false)
	private Long ranking;
	
	@NotNull
	@Column(name = "address", nullable = false)
	private String address;
	
	@NotNull
	@Column(name = "description", nullable = false)
	private String desc;
	
	@NotNull
	@Column(name = "status", columnDefinition = "boolean default true", nullable = false)
	private Boolean status;

	@JsonIgnore
	@OneToMany(mappedBy = "library")
	private Set<Book> books = new HashSet<>();

}
