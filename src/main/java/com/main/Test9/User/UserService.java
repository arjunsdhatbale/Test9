package com.main.Test9.User;

import com.main.Test9.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;



    @Transactional
    public UserMaster addUser(UserMasterDto userMasterDto) {

        UserMaster user;
        user = new UserMaster();
        user.setUserName(userMasterDto.getUserName());
        user.setPassword(userMasterDto.getPassword());

        return userRepo.save(user);

    }


    public List<UserMaster> findAllUsers() {
        return  this.userRepo.findAll();
    }

    public UserMaster findById(Integer id)  {
          return this.userRepo.findById(id).orElseThrow( () -> new UserNotFoundException("User not found with id : " + id));
    }


    public ResponseEntity<String> deleteByUsernama(String userName) throws UserNotFoundException {

        Optional<UserMaster> user = this.userRepo.findByuserName(userName);
        if(user.isPresent()){
            this.userRepo.delete(user.get());
            return ResponseEntity.ok("User Deleted successfully. " + userName);
        }else {
            throw new UserNotFoundException("User not found with userName : " + userName);
        }

    }
}
