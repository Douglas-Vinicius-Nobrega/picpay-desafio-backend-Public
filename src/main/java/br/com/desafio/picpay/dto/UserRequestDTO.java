package br.com.desafio.picpay.dto;

import br.com.desafio.picpay.domain.enums.UserType;

public record UserRequestDTO(
        String nomeCompleto,
        String cpf,
        String email,
        String senha,
        UserType userType
) {}
