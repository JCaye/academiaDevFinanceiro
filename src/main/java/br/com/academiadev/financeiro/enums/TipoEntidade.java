package br.com.academiadev.financeiro.enums;

public enum TipoEntidade {
	FORNECEDOR(1),
	CLIENTE(2);
	
	private Integer id;
	
	TipoEntidade(Integer id){
		this.id = id;
	}
	
	public static TipoEntidade findById(Integer id) {
		for (TipoEntidade tipo: values())
			if (tipo.getId().equals(id))
				return tipo;
		throw new IllegalArgumentException();
	}
	
	public Integer getId() {
		return this.id;
	}
}
