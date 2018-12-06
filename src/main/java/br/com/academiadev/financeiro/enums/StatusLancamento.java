package br.com.academiadev.financeiro.enums;

public enum StatusLancamento {
	ABERTO(1),
	BAIXADO(2);
	
	private Integer id;
	
	StatusLancamento(Integer id){
		this.id = id;
	}
	
	public static StatusLancamento findById(Integer id) {
		for (StatusLancamento status: values())
			if (status.getId().equals(id))
				return status;
		throw new IllegalArgumentException();
	}
	
	public Integer getId() {
		return this.id;
	}
}
