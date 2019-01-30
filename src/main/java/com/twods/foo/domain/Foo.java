package com.twods.foo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FOO")
public class Foo {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "FOO")
	private String foo;

	public Foo(String foo) {
		this.foo = foo;
	}

	Foo() {
		// Default constructor needed by JPA
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public Long getId() {
		return id;
	}
	
	

}