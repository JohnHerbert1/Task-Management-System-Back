package com.jadson.services;



import com.jadson.dto.requests.UserDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.models.entities.User;
import com.jadson.models.enumerations.ActiveType;
import com.jadson.models.enumerations.UserType;
import com.jadson.repositories.UserRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


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


    @Transactional
    public  void updateUser(UserDTO requestDTO) {

        // 1) Obtem o usuário autenticado
        User current = getCurrentUser();

        // 2) Se veio password, codifica e seta
        if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
            current.setPassword(passwordEncoder.encode(requestDTO.password()));
        }

        // 3) Se veio name, email ou outros campos, setar
        if (requestDTO.name() != null && !requestDTO.name().isBlank()) {
            current.setName(requestDTO.name());
        }
        if (requestDTO.email() != null && !requestDTO.email().isBlank() &&
                !requestDTO.email().equals(current.getEmail())) {

            // opcional: validar se novo email já existe
            if (repository.findByEmail(requestDTO.email()).isPresent()) {
                current.setEmail(current.getEmail());//caso o email que ele queira atualizar já pre exista então so seta pra o email anterior.
            }
            current.setEmail(requestDTO.email());
        }

        // 4) (Se tiver outros campos no DTO, trate aqui)

        // 5) Salvando alterações
        repository.save(current);
    }



    @Transactional
    public void incrementTokenVersion(String email) {
        repository.findByEmail(email).ifPresent(user -> {
            user.setTokenVersion(user.getTokenVersion() + 1);
            repository.save(user);
        });
    }

    public Boolean deleteUser(long id) {
        Optional<User> userOptional = repository.findById(id);
        if(userOptional.isPresent()){
            repository.delete(userOptional.get());
        }else {
            return false;
        }
        return  Boolean.TRUE;
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
