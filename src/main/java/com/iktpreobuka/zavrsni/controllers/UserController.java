package com.iktpreobuka.zavrsni.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.LoginUserDto;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;
import com.iktpreobuka.zavrsni.repositories.UserRepository;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.PupilService;
import com.iktpreobuka.zavrsni.services.UserService;
import com.iktpreobuka.zavrsni.utils.Encryption;
import com.iktpreobuka.zavrsni.utils.RESTError;
import com.iktpreobuka.zavrsni.utils.UserCustomValidation;

import io.jsonwebtoken.Jwts;


@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PupilService pupilService;
	@Autowired
	private UserCustomValidation userValidator;
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	@Autowired
	private SecretKey secretKey;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(path = "login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("user") String email, @RequestParam("password") String pwd) {
		UserEntity userEntity = userService.findUserByEmail(email);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			LoginUserDto user = new LoginUserDto();
			user.setUser(email);
			user.setToken(token);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
	
	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration)).signWith(this.secretKey)
				.compact();
		return "Bearer " + token;
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping()
	public ResponseEntity<?> list() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDto newUser) {
		return new ResponseEntity<>(userService.saveUserDtoAsUserEntity(newUser), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/pupils", method = RequestMethod.POST)
	public ResponseEntity<?> addNewPupil(@Valid @RequestBody PupilDto newPupil) {
		return new ResponseEntity<>(pupilService.savePupilDtoAsPupilEntity(newPupil), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/parents", method = RequestMethod.POST)
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDto newParent) {
		return new ResponseEntity<>(userService.saveParentDtoAsParentEntity(newParent), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/teachers", method = RequestMethod.POST)
	public ResponseEntity<?> addNewTeacher(@Valid @RequestBody TeacherDto newTeacher) {
		return new ResponseEntity<>(userService.saveTeacherDtoAsTeacherEntity(newTeacher), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto newUser, @PathVariable int id) {
		newUser.setId(id);
		return addNewUser(newUser);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/pupils/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePupil(@Valid @RequestBody PupilDto newPupil, @PathVariable int id) {
		newPupil.setId(id);
		return addNewPupil(newPupil);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/parents/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParent(@Valid @RequestBody ParentDto newParent, @PathVariable int id) {
		newParent.setId(id);
		return addNewParent(newParent);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTeacher(@Valid @RequestBody TeacherDto newTeacher, @PathVariable int id) {
		newTeacher.setId(id);
		return addNewTeacher(newTeacher);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = "";
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
				errorMessage = error.getDefaultMessage();
			} else if (error instanceof ObjectError) {
				fieldName = ((ObjectError) error).getObjectName();
				errorMessage = error.getDefaultMessage();
			}
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
	
}
