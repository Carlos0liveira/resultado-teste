package com.avaliavao.carlosjunior.model.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.avaliavao.carlosjunior.exception.RegraNegocioException;
import com.avaliavao.carlosjunior.model.entity.Pessoa;
import com.avaliavao.carlosjunior.model.repository.PessoaRepository;
import com.avaliavao.carlosjunior.model.repository.PessoaRepositoryTest;
import com.avaliavao.carlosjunior.service.impl.PessoaServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

	@SpyBean
	PessoaServiceImpl service;
	
	@MockBean
	PessoaRepository repository;
	
	public Pessoa criarPessoa() {
		return PessoaRepositoryTest.criarPessoa();
	}
	
	
	@Test
	public void salvarPessoa() {
		Pessoa pessoaSalvar = criarPessoa();
		doNothing().when(service).validarCampos(pessoaSalvar);
		
		Pessoa pessoaSalva = criarPessoa();
		pessoaSalva.setId(1l);
		when(repository.save(pessoaSalvar)).thenReturn(pessoaSalva);
		
		Pessoa pessoa = service.salvarPessoa(pessoaSalvar);
		
		Assertions.assertThat(pessoa.getId()).isEqualTo(pessoaSalva.getId());
	}
	
	@Test
	public void falhaSalvarPessoa() {
		Pessoa pessoaSalvar = criarPessoa();
		doThrow(RegraNegocioException.class).when(service).validarCampos(pessoaSalvar);
		
		Assertions.catchThrowableOfType(() -> service.salvarPessoa(pessoaSalvar), RegraNegocioException.class);
		Mockito.verify(repository, never()).save(pessoaSalvar);
	}
	
	@Test
	public void atualizarPessoa() {
		Pessoa pessoaSalva = criarPessoa();
		pessoaSalva.setId(1l);
		
		doNothing().when(service).validarCampos(pessoaSalva);
		
		when(repository.save(pessoaSalva)).thenReturn(pessoaSalva);
		
		service.atualizarPessoa(pessoaSalva);
		
		Mockito.verify(repository, times(1)).save(pessoaSalva);
	}
	
	@Test
	public void falhaAtualizarPessoa() {
		Pessoa lancamento = criarPessoa();
		
		Assertions.catchThrowableOfType( () -> service.validarCampos(lancamento), NullPointerException.class );
		Mockito.verify(repository, never()).save(lancamento);
	}
	
	@Test
	public void deletarPessoa() {
		Pessoa pessoa = criarPessoa();
		pessoa.setId(1l);

		service.deletarPessoa(pessoa);
		
		Mockito.verify( repository ).delete(pessoa);
	}
	
	
	@Test
	public void falhaDeletarPessoa() {
		Pessoa pessoa = criarPessoa();
		
		Assertions.catchThrowableOfType( () -> service.deletarPessoa(pessoa), NullPointerException.class );
		
		Mockito.verify( repository, never() ).delete(pessoa);
	}
	
	
	@Test
	public void buscarPessoaId() {
		Long id = 1l;
		
		Pessoa pessoa = criarPessoa();
		pessoa.setId(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(pessoa));
		
		Optional<Pessoa> resultado =  service.buscarPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}

	
	@Test
	public void falhaBuscarPessoa() {
		Long id = 1l;
		
		Pessoa pessoa = criarPessoa();
		pessoa.setId(id);
		
		when( repository.findById(id) ).thenReturn( Optional.empty() );
		
		Optional<Pessoa> resultado =  service.buscarPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
	
}
