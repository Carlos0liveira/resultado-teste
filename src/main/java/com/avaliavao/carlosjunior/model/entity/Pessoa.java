package com.avaliavao.carlosjunior.model.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name="pessoa", schema="application")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue( strategy = GenerationType.IDENTITY ) 
	Long id;
	
	@Column(name = "nome", nullable = false)
	String nome;
	
	@Column(name = "cpf", nullable = false)
	String cpf;
	
	@Column(name = "data_cadastro")
	LocalDate dataCadastro;
	
	@Column(name = "data_nascimento", nullable = false)
	Date dataNascimento;
	
}
