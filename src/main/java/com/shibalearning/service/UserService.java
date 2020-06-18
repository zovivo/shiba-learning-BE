package com.shibalearning.service;

import com.shibalearning.entity.Role;
import com.shibalearning.entity.User;
import com.shibalearning.entity.enu.ExceptionCode;
import com.shibalearning.entity.enu.SystemException;
import com.shibalearning.input.create.UserInput;
import com.shibalearning.input.create.UserLoginInput;
import com.shibalearning.input.update.UserUpdateInput;
import com.shibalearning.repository.RoleRepository;
import com.shibalearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User login(UserLoginInput userLoginInput) {
        User user = userRepository.findFirstByUserName(userLoginInput.getUserName());
        if (user == null)
            return null;
        else if (!user.getPassword().equals(userLoginInput.getPassword()))
            return null;
        return user;
    }

    public User create(UserInput userInput) throws SystemException {
        if (userRepository.findFirstByUserName(userInput.getUserName()) != null) {
            throw new SystemException(ExceptionCode.USER_NAME_EXIST);
        }
        if (userRepository.findFirstByEmail(userInput.getEmail()) != null) {
            throw new SystemException(ExceptionCode.EMAIL_EXIST);
        }
        User user = new User(userInput);
        if (userInput.getType() == 1) {
            Role teacher = roleRepository.findById(1);
            user.setRole(teacher);
        } else if (userInput.getType() == 0) {
            Role student = roleRepository.findById(0);
            user.setRole(student);
        } else {
            Role student = roleRepository.findById(2);
            user.setRole(student);
        }
        if (userInput.getAvatar() != null && userInput.getAvatar().getSize() != 0) {
            String avatar = cloudinaryService.uploadFile(userInput.getAvatar());
            user.setAvatar(avatar);
        }
        user = userRepository.save(user);
        return user;
    }

    public User updateProfile(UserUpdateInput userUpdateInput) throws SystemException {
        User user = userRepository.findFirstByUserName(userUpdateInput.getUserName());
        if (user == null)
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        if (userUpdateInput.getNewAvatar() != null && userUpdateInput.getNewAvatar().getSize() != 0)
            user.setAvatar(cloudinaryService.uploadFile(userUpdateInput.getNewAvatar()));
        if (userUpdateInput.getNewEmail() != null)
            user.setEmail(userUpdateInput.getNewEmail());
        return userRepository.save(user);
    }

    public User changePassword(UserUpdateInput userUpdateInput) {
        User user = userRepository.findFirstByUserName(userUpdateInput.getUserName());
        if (user == null)
            return null;
        if (userUpdateInput.getNewPassword() != null && userUpdateInput.getPassword().equals(user.getPassword()))
            user.setPassword(userUpdateInput.getNewPassword());
        else
            return null;
        return userRepository.save(user);
    }

    public Page<User> search(int page, int size, String userName, String email, String sortBy) {
        Pageable pageable = PageRequest.of(page, size);
        if (userName == null)
            userName = "";
        if (email == null)
            email = "";
        if (sortBy != null && !sortBy.isEmpty())
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<User> pageUser = userRepository.findAllByUserNameContainingAndAndEmailContaining(pageable, userName, email);
        return pageUser;
    }

    public User getByUserName(String userName) {
        User user = userRepository.findFirstByUserName(userName);
        return user;
    }

    public List<User> getAllTeacher() {
        return userRepository.findAllByRole_Id(1);
    }

    public void deleteById(long id) throws SystemException {
        try {
            userRepository.deleteById((Long) id);
        }catch (EmptyResultDataAccessException e){
            throw new SystemException(ExceptionCode.USER_NOT_FOUND);
        }
    }

}
