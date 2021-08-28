package com.avaliavao.carlosjunior.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
	
	private Long id;
	private String nome;
	private String cpf;
	private String dataNascimento;
}
