package com.users.microservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.common.microservice.service.CommonServiceImpl;
import com.common.microservice.users.entity.Student;
import com.users.microservice.clients.CourseFeignClient;
import com.users.microservice.repository.StudentRepository;

@Service
public class StudentServiceImpl extends CommonServiceImpl<Student, StudentRepository> implements StudentService {
	
	@Autowired
	private CourseFeignClient courseFeignClient;
	
	@Override
	@Transactional
	public List<Student> findByNameAndLastName(String term) {
		return repository.findByNameAndLastName(term);
	}

	@Override
	@Transactional
	public Iterable<Student> findAllById(Iterable<Long> ids) {
		
		return repository.findAllById(ids);
	}

	@Override
	@Transactional
	public void removeCourseStudentById(Long id) {
		courseFeignClient.removeCourseStudentById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.removeCourseStudentById(id);
		super.deleteById(id);
		
	}

	@Override
	@Transactional
	public Iterable<Student> findAll() {
		// TODO Auto-generated method stub
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional
	public Page<Student> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAllByOrderByIdAsc(pageable);
	}
	
	/*
	 * @Autowired private StudentRepository studentRepository;
	 * 
	 * @Override public Iterable<Student> findAll() { return
	 * studentRepository.findAll(); }
	 * 
	 * @Override public Optional<Student> findById(Long id) { return
	 * studentRepository.findById(id); }
	 * 
	 * @Override public Student save(Student student) { return
	 * studentRepository.save(student); }
	 * 
	 * @Override public void deleteById(Long id) { studentRepository.deleteById(id);
	 * 
	 * }
	 */
	
	

}
