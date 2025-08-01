package com.jadson.services;



import com.jadson.dto.requests.UserDTO;
import com.jadson.models.entities.User;
import com.jadson.models.enumerations.ActiveType;
import com.jadson.models.enumerations.UserType;
import com.jadson.repositories.UserRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl {


    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void creat (UserDTO requestDTO){

        if(repository.findByEmail(requestDTO.email()).isPresent()){
            System.out.println("Erro msg");
        }
        var entity = toEntity(requestDTO);

        entity.setActiveType(ActiveType.ATIVO);
        entity.setType(UserType.USUARIO_PADRAO);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        repository.save(entity);
    }

    public boolean login(String email,String password){
        return repository.findByEmail(email).map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }


    public List<UserDTO> getAll(){
        return  repository.findAll().stream().map(this::toDTO).toList();
    }


    public  void updateUser(UserDTO requestDTO){

    }

    public void BlockUser(Id id){

    }

    public void deleteUser(long id){
        repository.deleteById(id);
        log.info("Conta deletada do sistema: " );

    }

    //Utils from user:

    private User toEntity(UserDTO dto){
        var u = new User();
        BeanUtils.copyProperties(dto,u,"id");
        return  u;
    }

    private UserDTO toDTO(User u){
        return new UserDTO(u.getName(), u.getEmail(),u.getPassword());
    }


}
