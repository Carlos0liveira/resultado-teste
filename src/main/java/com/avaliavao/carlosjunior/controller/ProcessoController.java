package com.avaliavao.carlosjunior.controller;

import java.util.List;

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

import com.avaliavao.carlosjunior.dto.ProcessoDTO;
import com.avaliavao.carlosjunior.exception.RegraNegocioException;
import com.avaliavao.carlosjunior.model.entity.Pessoa;
import com.avaliavao.carlosjunior.model.entity.Processo;
import com.avaliavao.carlosjunior.service.PessoaService;
import com.avaliavao.carlosjunior.service.ProcessoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/processos")
@RequiredArgsConstructor
public class ProcessoController {
	
	private final ProcessoService service;
	private final PessoaService pessoaService;
	
	private Processo converter( ProcessoDTO dto ) {
		Processo processo = new Processo();
		processo.setNumero(dto.getNumero());
		processo.setAno(dto.getAno());
		Pessoa pessoa = pessoaService.buscarPorId(dto.getPessoa())
				.orElseThrow( () -> new RegraNegocioException("Usuario não encontrado na base de dados"));
		
		processo.setPessoa(pessoa);
		
		return processo;
	}
	
	
	private ProcessoDTO converter(Processo processo ) {
		return ProcessoDTO.builder()
				.id(processo.getId())
				.numero(processo.getNumero())
				.ano(processo.getAno())
				.pessoa(processo.getPessoa().getId())
				.build();
				
	} 
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping
	public ResponseEntity salvar( @RequestBody ProcessoDTO dto ) {
		try {
			Processo entidade = converter(dto);
			entidade = service.salvarProcesso(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody ProcessoDTO dto ) {
		return service.buscarPorId(id).map( entity -> {
			try {
				Processo processo = converter(dto);
				processo.setId(entity.getId());
				service.atualizarProcesso(processo);
				return ResponseEntity.ok(processo);
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
			new ResponseEntity<>("Processo nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deletar (@PathVariable("id") Long id) {
		return service.buscarPorId(id).map( entidade -> {
			service.deletarProcesso(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> 
			new ResponseEntity<>("Processo nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("{id}")
	public ResponseEntity pessoaPorId( @PathVariable("id") Long id ) {
		return service.buscarPorId(id)
				.map( processo -> new ResponseEntity(converter(processo), HttpStatus.OK))
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity buscarPessoas(
			@RequestParam( value = "numero", required = false ) Long numero,
			@RequestParam( value = "ano", required = false ) Integer ano ) {
		
		Processo processoFiltro = new Processo();
		processoFiltro.setNumero(numero);
		processoFiltro.setAno(ano);
		
		List<Processo> processos = service.buscar(processoFiltro);
		
		return ResponseEntity.ok(processos);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
