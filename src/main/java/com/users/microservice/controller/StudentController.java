package com.users.microservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.microservice.controller.CommonController;
import com.common.microservice.users.entity.Student;
import com.users.microservice.service.StudentService;


@RestController
@RequestMapping("/api/students")
public class StudentController extends CommonController<Student, StudentService> {
	
	
	@GetMapping("/students-per-course")
	public ResponseEntity<?> getStudentsPerCourse(@RequestParam List<Long> ids){
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStud(@PathVariable("id") Long id,@Valid @RequestBody Student student,
			BindingResult result){
		
		if(result.hasErrors()) {
			return this.validate(result);
		}
		
		Optional<Student> StudOptional = service.findById(id);
		
		if(StudOptional.isEmpty()) {
			ResponseEntity.notFound().build();
		}
		
		Student studDb = StudOptional.get();
		
		studDb.setName(student.getName());
		studDb.setEmail(student.getEmail());
		studDb.setLastName(student.getLastName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studDb));
	}
	
	@GetMapping("/search/{term}")
	public ResponseEntity<?> search(@PathVariable("term") String term){
		return ResponseEntity.ok(service.findByNameAndLastName(term));
	}
	
	@PostMapping("/add-photo")
	public ResponseEntity<?> createPhoto(@Valid Student stud, 
										BindingResult result, 
										@RequestParam MultipartFile archive) throws IOException{ 
		
		if(!archive.isEmpty()) {
			stud.setPhoto(archive.getBytes());
		}
		return super.addUser(stud, result);
	}
	
	
	@PutMapping("/update-photo/{id}")
	public ResponseEntity<?> updatePhoto(@PathVariable("id") Long id,
			@Valid Student student,
			BindingResult result,@RequestParam MultipartFile archive) throws IOException{
		
		if(result.hasErrors()) {
			return this.validate(result);
		}
		
		Optional<Student> StudOptional = service.findById(id);
		
		if(StudOptional.isEmpty()) {
			ResponseEntity.notFound().build();
		}
		
		Student studDb = StudOptional.get();
		
		studDb.setName(student.getName());
		studDb.setEmail(student.getEmail());
		studDb.setLastName(student.getLastName());
		
		if(!archive.isEmpty()) {
			studDb.setPhoto(archive.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studDb));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> showPhoto(@PathVariable("id") Long id){
		Optional<Student> stud = service.findById(id);
		
		if(stud.isEmpty() || stud.get().getPhoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource image = new ByteArrayResource(stud.get().getPhoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
	}
	
}

