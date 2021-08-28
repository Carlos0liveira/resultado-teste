package com.avaliavao.carlosjunior.model.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.avaliavao.carlosjunior.model.entity.Processo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProcessoRepositoryTest {
	@Autowired
	ProcessoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	public static Processo criarProcesso() {
		Processo processo = Processo.builder()
				.numero(1l)
				.ano(1)
				.dataCadastro(LocalDate.now())
				.build();
		
		return processo;
	}
	
	@Test
	public void salvarprocesso() {
		Processo processo = criarProcesso();
		processo =  repository.save(processo);
		Assertions.assertThat(processo.getId()).isNotNull();
	}
	
	
	@Test 
	public void deletarprocesso() {
		Processo processo = criarProcesso();
		entityManager.persist(processo);
		
		processo  = entityManager.find(Processo.class, processo.getId());
		repository.delete(processo);
		
		Processo processoDel = entityManager.find(Processo.class, processo.getId());
		Assertions.assertThat(processoDel).isNull();
	}
	
	@Test
	public void alterarprocesso() {
		Processo processo = criarProcesso();
		entityManager.persist(processo);
		
		processo.setAno(2050);
		repository.save(processo);
		
		Processo processoAtt = entityManager.find(Processo.class, processo.getId());
		
		Assertions.assertThat(processoAtt.getAno()).isEqualTo(2050);
	}
	
	
	@Test 
	public void buscarprocesso() {
		Processo processo = criarProcesso();
		entityManager.persist(processo);
		
		Optional<Processo> processoFind = repository.findById(processo.getId());
		
		Assertions.assertThat(processoFind.isPresent()).isTrue();
	}
}
