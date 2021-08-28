package com.avaliavao.carlosjunior.model.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

import com.avaliavao.carlosjunior.model.entity.Pessoa;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PessoaRepositoryTest {

	@Autowired
	PessoaRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	public static Pessoa criarPessoa() {
		Pessoa pessoa = Pessoa.builder()
				.cpf("65489316020")
				.nome("Carlos")
				.dataCadastro(LocalDate.now())
				.build();
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data = formato.parse("16/02/2001");
			pessoa.setDataNascimento(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return pessoa;
	}
	
	@Test
	public void salvarPessoa() {
		Pessoa pessoa = criarPessoa();
		pessoa =  repository.save(pessoa);
		Assertions.assertThat(pessoa.getId()).isNotNull();
	}
	
	
	@Test 
	public void deletarPessoa() {
		Pessoa pessoa = criarPessoa();
		entityManager.persist(pessoa);
		
		pessoa  = entityManager.find(Pessoa.class, pessoa.getId());
		repository.delete(pessoa);
		
		Pessoa pessoaDel = entityManager.find(Pessoa.class, pessoa.getId());
		Assertions.assertThat(pessoaDel).isNull();
	}
	
	@Test
	public void alterarPessoa() {
		Pessoa pessoa = criarPessoa();
		entityManager.persist(pessoa);
		
		pessoa.setNome("Teste");
		repository.save(pessoa);
		
		Pessoa pessoaAtt = entityManager.find(Pessoa.class, pessoa.getId());
		
		Assertions.assertThat(pessoaAtt.getNome()).isEqualTo("Teste");
	}
	
	
	@Test 
	public void buscarPessoa() {
		Pessoa pessoa = criarPessoa();
		entityManager.persist(pessoa);
		
		Optional<Pessoa> pessoaFind = repository.findById(pessoa.getId());
		
		Assertions.assertThat(pessoaFind.isPresent()).isTrue();
	}
	
}
