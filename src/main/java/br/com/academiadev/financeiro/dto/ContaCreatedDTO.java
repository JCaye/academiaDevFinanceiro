package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContaCreatedDTO extends ContaDTO {
	@ApiModelProperty(value = "Identificador", example = "123")
	private Long id;
	
	@ApiModelProperty(value = "Identificador do usuário", example = "123")
	private Long id_usuario;
	
	@ApiModelProperty(value = "Saldo", example = "0.0")
	private Double saldo;
	
	@ApiModelProperty(value = "Data de criação", example = "2018-01-01 00:00:00")
	private String data_criacao;
	
	@ApiModelProperty(value = "Data de alteração", example = "2018-01-01 00:00:00")
	private String data_alteracao;
}
