package com.avaliavao.carlosjunior.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliavao.carlosjunior.exception.RegraNegocioException;
import com.avaliavao.carlosjunior.model.entity.Pessoa;
import com.avaliavao.carlosjunior.model.repository.PessoaRepository;
import com.avaliavao.carlosjunior.service.PessoaService;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository repository;
	
	public PessoaServiceImpl(PessoaRepository repository) {
		this.repository= repository;
	}
	
	public boolean assertValid(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> err = cpfValidator.invalidMessagesFor(cpf);
		
		if (err.size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void validarCampos(Pessoa pessoa){
		if (!assertValid(pessoa.getCpf())) {
			throw new RegraNegocioException("CPF Inválido");
		}
		
		if (pessoa.getDataNascimento() == null) {
			throw new RegraNegocioException("informe uma data de nascimento valida");
		}
		
		if (pessoa.getNome() == null || pessoa.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome valido");
		}
	}
	
	@Override
	public Pessoa salvarPessoa(Pessoa pessoa) {
		validarCampos(pessoa);		
		pessoa.setDataCadastro(LocalDate.now());	
		return repository.save(pessoa);
	}

	@Override
	public void deletarPessoa(Pessoa pessoa) {
		Objects.requireNonNull(pessoa.getId());
		repository.delete(pessoa);
		
	}

	@Override
	public Pessoa atualizarPessoa(Pessoa pessoa) {
		Objects.requireNonNull(pessoa.getId());
		pessoa.setDataCadastro(LocalDate.now());
		return repository.save(pessoa);
	}

	@Override
	public Optional<Pessoa> buscarPorId(Long id) {
		return repository.findById(id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<Pessoa> buscar(Pessoa pessoaFiltro) {
		Example example  = Example.of(pessoaFiltro, 
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

}
