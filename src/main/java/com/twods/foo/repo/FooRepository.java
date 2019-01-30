package com.twods.foo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twods.foo.domain.Foo;

@Repository
public interface FooRepository extends JpaRepository<Foo, Long> {
  

}