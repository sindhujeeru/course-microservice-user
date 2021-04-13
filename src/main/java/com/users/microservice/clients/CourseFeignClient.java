package com.users.microservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-microservice")
public interface CourseFeignClient {
	
	@DeleteMapping("api/courses/remove-student/{id}")
	public void removeCourseStudentById(@PathVariable("id") Long id);

}
