package br.com.academiadev.financeiro.enums;

public enum TipoBaixa {
	ENTRADA(1),
	SAIDA(2);
	
	private Integer id;
	
	TipoBaixa(Integer id){
		this.id = id;
	}
	
	public static TipoBaixa findById(Integer id) {
		for (TipoBaixa status: values())
			if (status.getId().equals(id))
				return status;
		throw new IllegalArgumentException();
	}
	
	public Integer getId() {
		return this.id;
	}
}
