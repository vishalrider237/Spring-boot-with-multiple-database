package com.example.demo.hana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.hana.entity.User;
import com.example.demo.hana.repo.HanaRepo;



@RestController
@RequestMapping("imo/hana")
public class HanaController {
	@Autowired
	private HanaRepo userrepo;
	
	@PostMapping("/add")
    public ResponseEntity<?>addData(@RequestBody User user){
    	return new ResponseEntity<>(this.userrepo.save(user),HttpStatus.OK);
    }
	@GetMapping("/get")
	public ResponseEntity<?>getData(){
		return new ResponseEntity<>(this.userrepo.findAll(),HttpStatus.OK);	
	}
	@PutMapping("/update")
	public ResponseEntity<?>updateData(@RequestBody User user){
		User userget=this.userrepo.findById(user.getId()).get();
		if(userget==null) {
			return new ResponseEntity<>("Data with this id not found",HttpStatus.NOT_FOUND);
		}
		else {
			userget.setEmail(user.getEmail());
			userget.setPh_no(user.getPh_no());
			userget.setUsername(user.getUsername());
			this.userrepo.save(userget);
			return new ResponseEntity<>("Data is Updated sucessfully",HttpStatus.OK);
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteData(@PathVariable int id){
		this.userrepo.deleteById(id);
		return new ResponseEntity<>("Data deleted successfully",HttpStatus.OK);
	}

}
