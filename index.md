## Ravaan Techky
REST Example along with Request validation and HATEOAS framework

### Overview:
For Spring request validation, we need to add **spring-boot-validation-starter** dependency.
For HATEOAS (Hyperlink As The Engine Of Application State), we need to add **spring-boot-hateoas-starter** dependency

```markdown
	<dependency> 
		<groupId>org.springframework.boot</groupId> 
		<artifactId>spring-boot-starter-validation</artifactId> 
	</dependency>

	<dependency> 
		<groupId>org.springframework.boot</groupId> 
		<artifactId>spring-boot-starter-hateoas</artifactId> 
	</dependency>
```

### Important Points:
- Given sample example is work on Spring Boot version - **2.4.2**
- 

### Validation:
- To enabled validation we need add **@Validated** annotation in request method.
```markdown
	/**
	 * Save user.
	 *
	 * @param userObject the user object
	 * @return the response entity
	 */
	@PostMapping(path = "/user")
	public ResponseEntity<Object> saveUser(@Validated @RequestBody User userObject) {
		User newUser = this.userService.saveUser(userObject);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(newUser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
```

- We need to provide field level validation in Pojo class which created/bind from request.
```markdown
public class User {	
	/** The first name. */
	@Size(min = 2, message = "Minimum allowed size is 2 character")
	private String firstName;

	/** The enroll date. */
	@Past
	private Date enrollDate;
	...
```

- In above example, If requested User json does not pass validation then it throw HTTP 400 (Bad Request) code. To provide field level error message, we need to modify **handleMethodArgumentNotValid** method from ResponseEntityExceptionHandler class

```markdown
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder fieldErrorBuilder = new StringBuilder();
		exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
			fieldErrorBuilder.append("Field " + fieldError.getField() + " value " + fieldError.getRejectedValue() + " is rejected because of " + fieldError.getDefaultMessage());
		});
		ExceptionResponse exceptionResponse = new ExceptionResponse("Validation Fail. " + fieldErrorBuilder.toString(), exception.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
```

### HATEOAS (Hyperlink As The Engine Of Application State):
- HATEOAS framework is available in Spring Boot startup kit.
- With the help HATEOAS, we can able to send other request endpoints along with response which is useful from consumer prospective. Example, - If consumer requested user endpoints then in response along with user object it also bing some useful URI like - list-user, save-user, delete-user, etc.

 ```markdown
 	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	@GetMapping(path = "/user")
	public CollectionModel<User> getAllUser() {
		List<User> userList = this.userService.getAllUser();
		CollectionModel<User> resource = CollectionModel.of(userList);
		WebMvcLinkBuilder userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findUser(123));
		resource.add(userLink.withRel("user-link"));
		WebMvcLinkBuilder deleteUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUser(123));
		resource.add(deleteUserLink.withRel("delete-user-link"));
		User userObject = new User(-1, "firstName", "lastName", "address");
		WebMvcLinkBuilder saveUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(userObject));
		resource.add(saveUserLink.withRel("save-user-link"));
		return resource;
	}


	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the entity model
	 */
	@GetMapping(path = "/user/{userId}")
	public EntityModel<User> findUser(@PathVariable(name = "userId") Integer userId) {
		User resultUser = this.userService.findUser(userId);
		if(resultUser == null) {
			throw new ResourceNotFoundException("Requested user not found. [User ID : " + userId + "]");
		}
		EntityModel<User> resource = EntityModel.of(resultUser);
		WebMvcLinkBuilder deleteUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUser(123));
		resource.add(deleteUserLink.withRel("delete-user-link"));
		WebMvcLinkBuilder allUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
		resource.add(allUserLink.withRel("all-user"));
		return resource;
	}
 ```
 
- **Please note**, above code is with reference to new HATEOAS update.
- In Old HATEOAS framework, -

 ```markdown
 	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the entity model
	 */
	@GetMapping(path = "/user/{userId}")
	public Resource<User> findUser(@PathVariable(name = "userId") Integer userId) {
		User resultUser = this.userService.findUser(userId);
		if(resultUser == null) {
			throw new ResourceNotFoundException("Requested user not found. [User ID : " + userId + "]");
		}
		 Resource<User> userResource = new Resource<>(userResultObject);
		 ControllerLinkBinder linkTo = ControllerLinkBinder.linkTo(ControllerLinkBinder.methodOn(this.getClass()).getAllUsers());
		 resource.add(allUserLink.withRel("all-user"))
		 return resource;
	}
 ```


<br/><br/>
[<i class="fa fa-arrow-left"></i> **Back**](/documentation/)
