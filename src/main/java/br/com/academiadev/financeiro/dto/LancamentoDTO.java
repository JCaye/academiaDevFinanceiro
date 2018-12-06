package br.com.academiadev.financeiro.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LancamentoDTO {
	@ApiModelProperty(value = "Descrição", example = "Lanchonete", required = true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(value = "Observação", example = "Esqueci o cartão", required = true)
	@NotBlank
	private String observacao;
	
	@ApiModelProperty(value = "Valor", example = "16.50", required = true)
	@NotNull
	private Long valor;
	
	@ApiModelProperty(value = "Tipo", allowableValues = "PAGAR,RECEBER", required = true)
	@NotNull
	private String tipo;
}
