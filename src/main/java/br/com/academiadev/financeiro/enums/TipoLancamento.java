package br.com.academiadev.financeiro.enums;

public enum TipoLancamento {
	PAGAR(1),
	RECEBER(2);
	
	private Integer id;
	
	TipoLancamento(Integer id){
		this.id = id;
	}
}
