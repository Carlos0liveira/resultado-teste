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
import com.avaliavao.carlosjunior.model.entity.Processo;
import com.avaliavao.carlosjunior.model.repository.ProcessoRepository;
import com.avaliavao.carlosjunior.service.ProcessoService;

@Service
public class ProcessoServiceImpl implements ProcessoService{

	private ProcessoRepository repository;
	
	public ProcessoServiceImpl( ProcessoRepository repository ) {
		this.repository = repository;
	}
	
	public void validar(Processo processo) {
		if (processo.getNumero() == null) {
			throw new RegraNegocioException("Numero de processo invalido");
		}
		
		if (processo.getAno() == null || processo.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Ano do processo invalido");
		}
		
		if (processo.getPessoa() == null || processo.getPessoa().getId() == null) {
			throw new RegraNegocioException("Pessoa Invalida");
		}
	}
	
	@Override
	public Processo salvarProcesso(Processo processo) {
		validar(processo);
		processo.setDataCadastro(LocalDate.now());
		return repository.save(processo);
	}

	@Override
	public void deletarProcesso(Processo processo) {
		Objects.requireNonNull(processo.getId());
		repository.delete(processo);
		
	}

	@Override
	public Processo atualizarProcesso(Processo processo) {
		Objects.requireNonNull(processo.getId());
		processo.setDataCadastro(LocalDate.now());
		return repository.save(processo);
	}

	@Override
	public Optional<Processo> buscarPorId(Long id) {
		return repository.findById(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public List<Processo> buscar(Processo processoFiltro) {
		Example example = Example.of(processoFiltro, 
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

}
