package com.vitalmovs.services;

import com.vitalmovs.dtos.UserDTO;
import com.vitalmovs.entities.User;

public interface UserService {

    public User findById(Long id);
    public UserDTO findByIdDTO (Long id);
    public User findByUsername(String username);
    public User add(UserDTO userDTO);
    public User add(User user);
}
