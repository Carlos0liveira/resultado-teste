package com.avaliavao.carlosjunior.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avaliavao.carlosjunior.dto.PessoaDTO;
import com.avaliavao.carlosjunior.exception.RegraNegocioException;
import com.avaliavao.carlosjunior.model.entity.Pessoa;
import com.avaliavao.carlosjunior.service.PessoaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pessoas")
public class PessoaController {
	
	private final PessoaService service;
	
	public boolean dataMenorQueHoje(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataVerificada = LocalDate.parse(data, dtf);
		LocalDate hoje = LocalDate.now();
		return dataVerificada.compareTo(hoje) <= 0;
		}
	
	private Pessoa converter(PessoaDTO dto) {
		if (dataMenorQueHoje(dto.getDataNascimento()) == false ) {
			throw new RegraNegocioException("a data nao pode ser maior que a data de hoje");
		}
		
		Pessoa pessoa = new Pessoa();
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data = formato.parse(dto.getDataNascimento());
			pessoa.setDataNascimento(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		pessoa.setCpf(dto.getCpf());
		pessoa.setNome(dto.getNome());
		
		return pessoa;
	}
	
	private PessoaDTO converter(Pessoa pessoa) {
		return PessoaDTO.builder()
				.id(pessoa.getId())
				.cpf(pessoa.getCpf())
				.nome(pessoa.getNome())
				.build();
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/salvarpessoa")
	public ResponseEntity salvar(@Valid @RequestBody PessoaDTO dto) {
		Pessoa pessoa = converter(dto);
		
		try {
			Pessoa pessoaSalva = service.salvarPessoa(pessoa);
			return new ResponseEntity<Pessoa>(pessoaSalva, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id")Long id, @RequestBody PessoaDTO dto) {
		return service.buscarPorId(id).map( entity -> {
			try {
				Pessoa pessoa = converter(dto);
				pessoa.setId(entity.getId());
				service.atualizarPessoa(pessoa);
				return ResponseEntity.ok(pessoa);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
				new ResponseEntity("Pessoa nao encontrada na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.buscarPorId(id).map( entity -> { 
			service.deletarPessoa(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> 
		new ResponseEntity("Pessoa nao encontrada na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("{id}")
	public ResponseEntity pessoaPorId( @PathVariable("id") Long id ) {
		return service.buscarPorId(id)
				.map( pessoa -> new ResponseEntity(converter(pessoa), HttpStatus.OK))
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity buscarPessoas(
			@RequestParam( value = "cpf", required = false ) String cpf,
			@RequestParam( value = "nome", required = false ) String nome ) {
		
		Pessoa pessoaFiltro = new Pessoa();
		pessoaFiltro.setCpf(cpf);
		pessoaFiltro.setNome(nome);
		
		List<Pessoa> pessoas = service.buscar(pessoaFiltro);
		
		return ResponseEntity.ok(pessoas);
	}
	
	
	
	
	
	
	
	
	
}
