package com.icube.investframe;

import java.net.URI;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.icube.investframe.entity.Account;
import com.icube.investframe.entity.User;
import com.icube.investframe.exception.ResourceNotFoundException;

@RestController
public class UserContoller {

	@Autowired
	UserRepository urepo;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	ResponseEntity<Iterable<User>> getAllUsers(){
		Iterable<User> users = urepo.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	ResponseEntity<?> createUser(@Valid @RequestBody User user){
		user = urepo.save(user);
	
		Set<Account> accounts = user.getAccounts();
		if(accounts != null){
			for(Account account : accounts){
				account.setUser(user);
			}
			
			user = urepo.save(user);
			
		}
		
		//Set location header for newly created entity
		HttpHeaders headers = new HttpHeaders();
		URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		headers.setLocation(newUserUri);
		
		
		return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
		
	}

	@RequestMapping(value="/users/{userId}", method=RequestMethod.GET)
	ResponseEntity<?> getUser(@PathVariable Long userId){
		verifyUser(userId);
		User user = urepo.findOne(userId);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
	ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId){
		verifyUser(userId);
		user = urepo.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
	ResponseEntity<?> deleteUser(@PathVariable Long userId){
		verifyUser(userId);
		urepo.delete(userId);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	protected void verifyUser(Long userId){
		User user = urepo.findOne(userId);
		
		if(user == null){
			throw new ResourceNotFoundException("User with id " + userId + " not found.");
		}
		
		
	}
	
	
}
