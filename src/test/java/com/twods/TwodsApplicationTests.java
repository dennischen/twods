package com.twods;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.twods.bar.domain.Bar;
import com.twods.bar.repo.BarRepository;
import com.twods.foo.domain.Foo;
import com.twods.foo.repo.FooRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwodsApplicationTests {

	@Autowired
	FooRepository fooRepos;
	@Autowired
	BarRepository barRepos;
	
	@Test
	@Transactional("fooTransactionManager")
	//org.hibernate.LazyInitializationException: could not initialize proxy [com.twods.bar.domain.Bar#1] - no Session
	public void testFoo() {
		Assert.assertEquals(0,fooRepos.count());
		
		Foo foo1 = new Foo("foo");
		foo1 = fooRepos.save(foo1);
		
		Assert.assertEquals(1,fooRepos.count());
		
		Foo foo = fooRepos.getOne(foo1.getId());
		
		Assert.assertEquals(foo1.getId(), foo.getId());
		Assert.assertEquals(foo1.getFoo(), foo.getFoo());
		
		List<Foo> fooList = fooRepos.findAll();
		
		Assert.assertEquals(1, fooList.size());
		
		foo = fooList.get(0);
		
		Assert.assertEquals(foo1.getId(), foo.getId());
		Assert.assertEquals(foo1.getFoo(), foo.getFoo());
		
		fooRepos.deleteById(foo1.getId());
		
		Assert.assertEquals(0,fooRepos.count());
	}
	
	@Test
	@Transactional(transactionManager="barTransactionManager")
	public void testBar() {
		Assert.assertEquals(0,barRepos.count());
		
		Bar bar1 = new Bar("bar");
		bar1 = barRepos.save(bar1);
		
		Assert.assertEquals(1,barRepos.count());
		
		Bar bar = barRepos.getOne(bar1.getId());
		
		Assert.assertEquals(bar1.getId(), bar.getId());
		Assert.assertEquals(bar1.getBar(), bar.getBar());
		
		List<Bar> barList = barRepos.findAll();
		
		Assert.assertEquals(1, barList.size());
		
		bar = barList.get(0);
		
		Assert.assertEquals(bar1.getId(), bar.getId());
		Assert.assertEquals(bar1.getBar(), bar.getBar());
		
		barRepos.deleteById(bar1.getId());
		
		Assert.assertEquals(0,barRepos.count());
	}
	
	@Test
	public void testFooBar() {
		Assert.assertEquals(0,fooRepos.count());
		Assert.assertEquals(0,barRepos.count());
		
		Foo foo1 = new Foo("foo");
		foo1 = fooRepos.saveAndFlush(foo1);
		
		Assert.assertEquals(1,fooRepos.count());
		
		Bar bar1 = new Bar("bar");
		bar1 = barRepos.saveAndFlush(bar1);
		
		Assert.assertEquals(1,barRepos.count());
		
		//org.hibernate.LazyInitializationException: could not initialize proxy [com.twods.foo.domain.Foo#2] - no Session
//		Foo foo = fooRepos.getOne(foo1.getId());
		Foo foo = fooRepos.findById(foo1.getId()).get();
		
		Assert.assertEquals(foo1.getId(), foo.getId());
		Assert.assertEquals(foo1.getFoo(), foo.getFoo());
		
		List<Foo> fooList = fooRepos.findAll();
		
		Assert.assertEquals(1, fooList.size());
		
		foo = fooList.get(0);
		
		Assert.assertEquals(foo1.getId(), foo.getId());
		Assert.assertEquals(foo1.getFoo(), foo.getFoo());
		
		Bar bar = barRepos.findById(bar1.getId()).get();
		
		Assert.assertEquals(bar1.getId(), bar.getId());
		Assert.assertEquals(bar1.getBar(), bar.getBar());
		
		List<Bar> barList = barRepos.findAll();
		
		Assert.assertEquals(1, barList.size());
		
		bar = barList.get(0);
		
		Assert.assertEquals(bar1.getId(), bar.getId());
		Assert.assertEquals(bar1.getBar(), bar.getBar());
		
		fooRepos.deleteById(foo1.getId());
		
		Assert.assertEquals(0,fooRepos.count());
		
		barRepos.deleteById(bar1.getId());
		
		Assert.assertEquals(0,barRepos.count());
	}

}

