package com.avaliavao.carlosjunior.service;

import java.util.List;
import java.util.Optional;

import com.avaliavao.carlosjunior.model.entity.Processo;

public interface ProcessoService {

	Processo salvarProcesso(Processo processo);
	
	void deletarProcesso(Processo processo);
	
	Processo atualizarProcesso(Processo processo);
	
	Optional<Processo> buscarPorId(Long id);
	
	List<Processo> buscar(Processo processoFiltro);
	
}
