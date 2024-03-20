package com.example.demo.postgree.controller;
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

import com.example.demo.postgree.entity.*;
@RestController
@RequestMapping("imo/postgrees")
public class PostgreesController {
	@Autowired
	private com.example.demo.postgree.repo.ProductRepo  productrepo;
	
	@PostMapping("/add")
    public ResponseEntity<?>addData(@RequestBody Product prod){
    	return new ResponseEntity<>(this.productrepo.save(prod),HttpStatus.OK);
    }
	@GetMapping("/get")
	public ResponseEntity<?>getData(){
		return new ResponseEntity<>(this.productrepo.findAll(),HttpStatus.OK);	
	}
	@PutMapping("/update")
	public ResponseEntity<?>updateData(@RequestBody Product product){
		Product productget=this.productrepo.findById(product.getId()).get();
		System.out.println("Product is:"+productget);
		if(productget==null) {
			return new ResponseEntity<>("Data with this id not found",HttpStatus.NOT_FOUND);
		}
		else {
			productget.setDescription(product.getDescription());
			productget.setLive(product.isLive());
			productget.setName(product.getName());
			productget.setPrice(product.getPrice());
			this.productrepo.save(productget);
			return new ResponseEntity<>("Data updated sucessfully",HttpStatus.OK);
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteData(@PathVariable int id){
		this.productrepo.deleteById(id);
		return new ResponseEntity<>("Data deleted successfully",HttpStatus.OK);
	}
}
