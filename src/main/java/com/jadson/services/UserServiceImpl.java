package com.jadson.services;



import com.jadson.config.JwtTokenProvider;
import com.jadson.dto.requests.TokenDTO;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final   JwtTokenProvider jwtTokenProvider;
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

        // 1) Obtém o usuário autenticado (assumo que getCurrentUser lê do SecurityContext)
        User current = getCurrentUser();

        // 2) Se veio password, codifica e seta
        if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
            current.setPassword(passwordEncoder.encode(requestDTO.password()));
        }

        // 3) Se veio name, setar
        if (requestDTO.name() != null && !requestDTO.name().isBlank()) {
            current.setName(requestDTO.name());
        }

        boolean emailChanged = false;
        String oldEmail = current.getEmail();
        if (requestDTO.email() != null && !requestDTO.email().isBlank()
                && !requestDTO.email().equals(oldEmail)) {

            // Verifica se o novo email já existe em outro usuário
            boolean emailExiste = repository.findByEmail(requestDTO.email())
                    .filter(user -> !user.getId().equals(current.getId()))
                    .isPresent();

            if (emailExiste) {
                throw new IllegalArgumentException("Email já está em uso por outro usuário.");
            } else {
                current.setEmail(requestDTO.email());
                emailChanged = true;
            }
        }

        // 4) (tratar outros campos do DTO aqui)

        User updated = repository.save(current); // saved and managed

        // 6) Atualiza o SecurityContext para refletir o novo username/email (se a aplicação usa UserDetails)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            UsernamePasswordAuthenticationToken newAuth =
                    new UsernamePasswordAuthenticationToken(updated, auth.getCredentials(), updated.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        String newToken = jwtTokenProvider.generateToken(updated.getEmail(), updated.getTokenVersion());
        TokenDTO tokenDTO = new TokenDTO(newToken, updated.getEmail());


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
