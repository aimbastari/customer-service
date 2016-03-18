package com.icube.investframe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icube.investframe.entity.User;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private UserRepository urepo;
	
	

	
	
}
