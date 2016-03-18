package com.icube.investframe;

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

@RestController
public class AccountController {

	@Autowired
	AccountRepository arepo;
	
	@Autowired
	UserRepository urepo;
	
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	ResponseEntity<Iterable<Account>> getAllAccounts(){
		Iterable<Account> accounts = arepo.findAll();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
		
	}

	@RequestMapping(value="/users/{id}/accounts", method=RequestMethod.POST)
	public ResponseEntity<?> createAccount(@PathVariable Long id, @RequestBody Account inputAccount){
		User user = urepo.findOne(id);
		Account account = new Account();
		account.setName(inputAccount.getName());
		account.setUser(user);
		
		Account savedAccount = arepo.save(account);
		
        // Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAccount.getId()).toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

	}
	
	
	
/*	
	@RequestMapping(value="/users/{id}/accounts", method=RequestMethod.POST)
	public ResponseEntity<?> createAccount(@PathVariable Long id, @RequestBody Account account){
		
		arepo.save(account);
		User user = urepo.getOne(id);
		user.getAccounts().add(account);
		urepo.save(user);
		
        // Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/users/{id}/accounts", method=RequestMethod.POST)
	public ResponseEntity<?> createAccount(@PathVariable Long id, @RequestBody Account account){
		//save the account (get the key)
		User user = urepo.findOne(id);
		account.setUser(user);
		Account savedAccount = arepo.save(account);
		
		//Fetch and wire objects correctly
		user.getAccounts().add(savedAccount);
		
		//Finally save the user 
		urepo.save(user);
		
        // Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/users/{id}/accounts", method=RequestMethod.POST)
	public ResponseEntity<?> createAccount(@PathVariable Long id, @RequestBody Account account){
		//save the account (get the key)
		User user = urepo.findOne(id);
		
		account.setUser(user);
//		Account savedAccount = arepo.save(account);
		
		//Fetch and wire objects correctly
		user.getAccounts().add(account);
		
		//Finally save the user 
		urepo.save(user);
		
        // Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
*/
	
}
