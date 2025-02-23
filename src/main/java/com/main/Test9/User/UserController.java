package com.main.Test9.User;

import com.main.Test9.Exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
// Head Method

    @RequestMapping(value = "/{id}",method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkUserExist(@Parameter(description = "User Id to test HEAD method.") @PathVariable Integer id){
        userService.findById(id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getAllowedMethods() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        return ResponseEntity.ok().headers(headers).build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleUsersOptions() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserMasterDto userMasterDto){
        UserMaster  user =  userService.addUser(userMasterDto);
        return  ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserMaster>> getAllUsers( ){

        List<UserMaster> listOfUsers = userService.findAllUsers();
        if(listOfUsers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserMaster>>(listOfUsers,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMaster> getUser(@Valid @PathVariable Integer id) throws UserNotFoundException {
        UserMaster user = this.userService.findById(id);
        return ResponseEntity.ok(user);
    }

    
    
    @Operation(summary = "Delete User by User Name",description = "Remove the User from system")
    @ApiResponse(responseCode = "200",description = "User deleted successfully")
    @ApiResponse(responseCode = "400",description = "User not found...")
    @DeleteMapping("/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName) throws UserNotFoundException {
        return this.userService.deleteByUsernama(userName);
    }


}
