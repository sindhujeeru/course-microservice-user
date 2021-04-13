package com.users.microservice.service;

import java.util.List;
import com.common.microservice.service.CommonService;
import com.common.microservice.users.entity.Student;

public interface StudentService extends CommonService<Student>{
	
	public List<Student> findByNameAndLastName(String term);
	/*
	 * public Iterable<Student> findAll();
	 * 
	 * public Optional<Student> findById(Long id);
	 * 
	 * public Student save(Student student);
	 * 
	 * public void deleteById(Long id);
	 */
	
	Iterable<Student> findAllById(Iterable<Long> ids);
	
	public void removeCourseStudentById(Long id);

	
	
	
}
