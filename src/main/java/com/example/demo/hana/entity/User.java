package com.example.demo.hana.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="Hana_Postgree_Test")
public class User {
	     @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="USER_ID")
	    private  int id;
	    private String username;
	    private String email;
	    private String ph_no;
}
