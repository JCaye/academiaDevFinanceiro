package br.com.academiadev.financeiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UsuarioDetailDTO extends UsuarioCreatedDTO {
	@ApiModelProperty(value = "Saldo", example = "2000")
	private Double saldo;
	
	@ApiModelProperty(value = "A pagar", example = "800")
	private Double pagar;
	
	@ApiModelProperty(value = "A receber", example = "500")
	private Double receber;
}