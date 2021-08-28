package com.avaliavao.carlosjunior.service;

import java.util.List;
import java.util.Optional;

import com.avaliavao.carlosjunior.model.entity.Pessoa;



public interface PessoaService {

	Pessoa salvarPessoa(Pessoa pessoa);
	
	void deletarPessoa(Pessoa pessoa);
	
	Pessoa atualizarPessoa(Pessoa pessoa);
	
	Optional<Pessoa> buscarPorId(Long id);
	
	List<Pessoa> buscar(Pessoa pessoaFiltro);
	
}
