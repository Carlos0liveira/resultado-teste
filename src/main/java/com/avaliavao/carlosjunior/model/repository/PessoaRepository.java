package com.avaliavao.carlosjunior.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avaliavao.carlosjunior.model.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
