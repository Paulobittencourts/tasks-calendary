package com.br.hbs.customer.service;

import com.br.hbs.customer.dto.response.ItemsTaskResponse;
import com.br.hbs.customer.dto.request.UserRequest;
import com.br.hbs.customer.dto.response.UserResponse;
import com.br.hbs.customer.http.TasksClient;
import com.br.hbs.customer.model.RolesModel;
import com.br.hbs.customer.model.UserModel;
import com.br.hbs.customer.reporitory.RolesRepository;
import com.br.hbs.customer.reporitory.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private TasksClient client;

    public Page<UserResponse> getUsers(Pageable pageable) {
        List<UserResponse> listUser = userRepository.findAll(pageable)
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        for(UserResponse user: listUser){
            UserResponse usersById = getUsersById(user.getId());
            listUser.stream()
                    .filter(userTasks -> userTasks.getId().equals(usersById.getId()))
                    .forEach(userTasks -> userTasks.setTasks(usersById.getTasks()));
        }
        return new PageImpl<>(listUser);
    }


    public UserResponse getUsersById(Long id) {
        UserModel userID = userRepository.findById(id).orElseThrow(EntityExistsException::new);
        UserResponse dto = modelMapper.map(userID, UserResponse.class);
        dto.setTasks(client.getByIDTask(userID.getId())
                .stream()
                .map(tasks -> modelMapper.map(tasks, ItemsTaskResponse.class))
                .toList());
        return dto;
    }

    public UserResponse createUsers(UserRequest userRequest) {
        UserModel userCreate = modelMapper.map(userRequest, UserModel.class);
        List<RolesModel> roles = rolesRepository.findAll();
        userCreate.setPassword(passwordEncoder(userRequest.getPassword()));
        userCreate.setRoles(Collections.singletonList(roles.get(0)));
        userRepository.save(userCreate);
        return modelMapper.map(userCreate, UserResponse.class);
    }

    public UserResponse updatedUsers(Long id, UserRequest user) {
        UserModel userUpdate = userRepository.findById(id).orElseThrow(EntityExistsException::new);
        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPassword(passwordEncoder(user.getPassword()));
        userRepository.save(userUpdate);
        return modelMapper.map(userUpdate, UserResponse.class);
    }

    public void updatedStatus(String tasks, Long id){
        client.updatedStatus(tasks, id);
    }

    public void delete(Long id) {
        UserModel deleteUsers = userRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        userRepository.delete(deleteUsers);
    }


    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    protected String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
