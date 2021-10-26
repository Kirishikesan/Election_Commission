package com.crypto.electionCommission.transfer;


import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


@Controller
public class TransferController {
	
	@Autowired
	private TransferService transferService;
	
	@RequestMapping(value = "/transfer")
	public void sendCertificate(HttpServletRequest request, HttpServletResponse response){
		transferService.getCertificate(request, response);
	}
	
	@GetMapping(value="/initiatetransfer")
	public void initiateTransfer() {
		String uri = "http://localhost:8080/initiatetransfer";
		RestTemplate restTemplate = new RestTemplate();
		File result = restTemplate.getForObject(uri, File.class);
		
	}
	
	@RequestMapping(value = "/config")
	public void getCertificate(HttpServletRequest request, HttpServletResponse response){
		transferService.getCertificate(request, response);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/config/getRandomID")
	public void sendRandomID(@RequestBody String request) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		transferService.sendRandomID(request);
	}
}
