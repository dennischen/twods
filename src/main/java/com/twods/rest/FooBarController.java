package com.twods.rest;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twods.bar.domain.Bar;
import com.twods.bar.repo.BarRepository;
import com.twods.foo.domain.Foo;
import com.twods.foo.repo.FooRepository;

@RestController
public class FooBarController {

	@Autowired
	private FooRepository fooRepo;
	@Autowired
	private BarRepository barRepo;

	Random r = new Random(System.currentTimeMillis());

	@RequestMapping("/foobar/{id}")
	public String get(@PathVariable("id") Long id) {
		Foo foo = fooRepo.findById(id).get();
		Bar bar = barRepo.findById(id).get();

		return foo.getFoo() + " " + bar.getBar();
	}
	
	@RequestMapping("/foobar")
	public String add() {
		Foo foo = new Foo("Foo-"+r.nextInt());
		Bar bar = new Bar("Bar-"+r.nextInt());
		foo = fooRepo.saveAndFlush(foo);
		bar = barRepo.saveAndFlush(bar);

		String msg = String.format("%s(%s) - %s(%s)", foo.getFoo(), foo.getId(), bar.getBar(), bar.getId());

		System.out.println("1>>>>>"+msg);
		
		return msg;
	}
	
	@Transactional
	@RequestMapping("/foobartx")
	public String addTx() {
		Foo foo = new Foo("FooTx-"+r.nextInt());
		Bar bar = new Bar("BarTx-"+r.nextInt());
		foo = fooRepo.saveAndFlush(foo);
		bar = barRepo.saveAndFlush(bar);

		String msg = String.format("%s(%s) - %s(%s)", foo.getFoo(), foo.getId(), bar.getBar(), bar.getId());

		System.out.println("2>>>>>"+msg);
		
		if (true) {
			throw new RuntimeException("doens't roll back");
		}
		

		return msg;
	}
	
}