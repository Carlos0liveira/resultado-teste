package com.avaliavao.carlosjunior.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table( name="processos", schema="application")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processo {
	
	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue( strategy = GenerationType.IDENTITY ) 
	Long id;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	Pessoa pessoa;
	
	@Column(name="numero", nullable = false)
	Long numero;
	
	@Column(name="ano", nullable = false)
	Integer ano;
	
	@Column(name="data_cadastro", nullable = false)
	LocalDate dataCadastro;

}
