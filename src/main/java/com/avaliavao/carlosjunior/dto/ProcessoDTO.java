package com.avaliavao.carlosjunior.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoDTO {
	
	private Long id;
	private Long numero;
	private Integer ano;
	private Long pessoa;
}
