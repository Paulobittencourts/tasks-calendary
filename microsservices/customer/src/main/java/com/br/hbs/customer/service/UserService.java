package com.br.hbs.customer.service;

import com.br.hbs.customer.dto.ItemsTask;
import com.br.hbs.customer.dto.UserDTO;
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

    public Page<UserDTO> getUsers(Pageable pageable) {
        List<UserDTO> listUser = userRepository.findAll(pageable)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();

        for(UserDTO user: listUser){
            UserDTO usersById = getUsersById(user.getId());
            listUser.stream()
                    .filter(userTasks -> userTasks.getId().equals(usersById.getId()))
                    .forEach(userTasks -> userTasks.setTasks(usersById.getTasks()));
        }
        return new PageImpl<>(listUser);
    }


    public UserDTO getUsersById(Long id) {
        UserModel userID = userRepository.findById(id).orElseThrow(EntityExistsException::new);
        UserDTO dto = modelMapper.map(userID, UserDTO.class);
        dto.setTasks(client.getByIDTask(userID.getId())
                .stream()
                .map(tasks -> modelMapper.map(tasks, ItemsTask.class))
                .toList());
        return dto;
    }

    public UserDTO createUsers(UserDTO userDTO) {
        UserModel userCreate = modelMapper.map(userDTO, UserModel.class);
        List<RolesModel> roles = rolesRepository.findAll();
        userCreate.setPassword(passwordEncoder(userDTO.getPassword()));
        userCreate.setRoles(Collections.singletonList(roles.get(0)));
        userRepository.save(userCreate);
        return modelMapper.map(userCreate, UserDTO.class);
    }

    public UserDTO updatedUsers(Long id, UserDTO user) {
        UserModel userUpdate = userRepository.findById(id).orElseThrow(EntityExistsException::new);
        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPassword(passwordEncoder(user.getPassword()));
        userRepository.save(userUpdate);
        return modelMapper.map(userUpdate, UserDTO.class);
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
