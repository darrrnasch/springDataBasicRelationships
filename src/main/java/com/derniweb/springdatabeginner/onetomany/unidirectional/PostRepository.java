package com.derniweb.springdatabeginner.onetomany.unidirectional;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
