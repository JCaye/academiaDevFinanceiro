package br.com.academiadev.financeiro.enums;

public enum StatusTipo {
	ATIVO(1),
	INATIVO(2);
	
	private Integer id;
	
	StatusTipo(Integer id){
		this.id = id;
	}
	
	public static StatusTipo findById(Integer id) {
		for (StatusTipo status: values())
			if (status.getId().equals(id))
				return status;
		throw new IllegalArgumentException();
	}
	
	public Integer getId() {
		return this.id;
	}
}
