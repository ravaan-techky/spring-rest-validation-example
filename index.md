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


<br/><br/>
[<i class="fa fa-arrow-left"></i> **Back**](/documentation/)
