package com.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Size(max = 100)
	@Column(name = "author_name", nullable = false)
	private String authorName;

	@Column(name = "status", columnDefinition = "boolean default true", nullable = false)
	private Boolean status;
	
	@Column(name = "price", nullable = false)
	private BigDecimal price;
	
	@NotNull
	@Column(name = "description", nullable = false)
	private String desc;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "library_id", nullable = false)
	private Library library;

}
