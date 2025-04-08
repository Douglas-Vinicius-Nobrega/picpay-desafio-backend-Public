package br.com.desafio.picpay.service;

import br.com.desafio.picpay.domain.model.User;
import br.com.desafio.picpay.dto.UserRequestDTO;
import br.com.desafio.picpay.exception.CpfJaCadastradoException;
import br.com.desafio.picpay.exception.EmailJaCadastradoException;
import br.com.desafio.picpay.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequestDTO userDTO) {
        if (userRepository.existsByCpf(userDTO.cpf())) {
            log.warn("Tentativa de cadastro com CPF já existente: {}", userDTO.cpf());
            throw new CpfJaCadastradoException("CPF já cadastrado.");
        }

        if (userRepository.existsByEmail(userDTO.email())) {
            log.warn("Tentativa de cadastro com email já existente: {}", userDTO.email());
            throw new EmailJaCadastradoException("Email já cadastrado.");
        }

        User user = new User();
        user.setNomeCompleto(userDTO.nomeCompleto());
        user.setCpf(userDTO.cpf());
        user.setEmail(userDTO.email());
        user.setSenha(userDTO.senha());
        user.setUserType(userDTO.userType());

        log.info("Novo usuário cadastrado com sucesso: {}", user.getEmail());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
