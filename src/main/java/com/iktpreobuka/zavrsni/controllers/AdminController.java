package com.iktpreobuka.zavrsni.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.services.AdminService;
import com.iktpreobuka.zavrsni.services.UserService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(method=RequestMethod.GET, value="/download")
	public ResponseEntity<ByteArrayResource>  downloadFile() throws IOException {

		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=spring-boot-logging.log");

	    Path path1 = Paths.get("logs/spring-boot-logging.log");
	    
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path1));

	    return ResponseEntity.ok()
	    		.headers(header)
	            .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);

	}
}
