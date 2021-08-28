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
import com.avaliavao.carlosjunior.model.entity.Processo;
import com.avaliavao.carlosjunior.model.repository.ProcessoRepository;
import com.avaliavao.carlosjunior.model.repository.ProcessoRepositoryTest;
import com.avaliavao.carlosjunior.service.impl.ProcessoServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProcessoServiceTest {

	@SpyBean
	ProcessoServiceImpl service;
	
	@MockBean
	ProcessoRepository repository;
	
	public Processo criarProcesso() {
		return ProcessoRepositoryTest.criarProcesso();
	}
	
	@Test
	public void salvarProcesso() {
		Processo processoSalvar = criarProcesso();
		doNothing().when(service).validar(processoSalvar);
		
		Processo processoSalvo = criarProcesso();
		processoSalvo.setId(1l);
		when(repository.save(processoSalvar)).thenReturn(processoSalvo);
		
		Processo processo = service.salvarProcesso(processoSalvar);
		
		Assertions.assertThat(processo.getId()).isEqualTo(processoSalvo.getId());
	}
	
	@Test
	public void falhaSalvarProcesso() {
		Processo processoSalvar = criarProcesso();
		doThrow(RegraNegocioException.class).when(service).validar(processoSalvar);
		
		Assertions.catchThrowableOfType(() -> service.salvarProcesso(processoSalvar), RegraNegocioException.class);
		Mockito.verify(repository, never()).save(processoSalvar);
	}
	
	@Test
	public void atualizarProcesso() {
		Processo processoSalvo = criarProcesso();
		processoSalvo.setId(1l);
		
		doNothing().when(service).validar(processoSalvo);
		
		when(repository.save(processoSalvo)).thenReturn(processoSalvo);
		
		service.atualizarProcesso(processoSalvo);
		
		Mockito.verify(repository, times(1)).save(processoSalvo);
	}
	
	@Test
	public void falhaAtualizarProcesso() {
		Processo processo = criarProcesso();
		
		Assertions.catchThrowableOfType( () -> service.validar(processo), RegraNegocioException.class );
		Mockito.verify(repository, never()).save(processo);
	}
	
	@Test
	public void deletarProcesso() {
		Processo processo = criarProcesso();
		processo.setId(1l);

		service.deletarProcesso(processo);
		
		Mockito.verify( repository ).delete(processo);
	}
	
	@Test
	public void falhaDeletarProcesso() {
		Processo processo = criarProcesso();
		
		Assertions.catchThrowableOfType( () -> service.deletarProcesso(processo), NullPointerException.class );
		
		Mockito.verify( repository, never() ).delete(processo);
	}
	
	@Test
	public void buscarProcessoPorId() {
		Long id = 1l;
		
		Processo processo = criarProcesso();
		processo.setId(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(processo));
		
		Optional<Processo> resultado =  service.buscarPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}
	
	
	@Test
	public void falhaBuscarProcesso() {
		Long id = 1l;
		
		Processo processo = criarProcesso();
		processo.setId(id);
		
		when( repository.findById(id) ).thenReturn( Optional.empty() );
		
		Optional<Processo> resultado =  service.buscarPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
}
