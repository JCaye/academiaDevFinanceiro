package br.com.academiadev.financeiro.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.academiadev.financeiro.enums.StatusLancamento;
import br.com.academiadev.financeiro.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "lancamentos")
@Entity
public class Lancamento extends EntidadeAuditavel<Long> {
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	@NotNull
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_entidade")
	@NotNull
	private Entidade entidade;
	
	@OneToOne
	private Baixa baixa;
	
	@NotBlank
	private String descricao;
	
	@NotBlank
	private String observacao;
	
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@Builder.Default
	private StatusLancamento status = StatusLancamento.ABERTO;
	
	@NotNull
	private TipoLancamento tipo;
	
	private LocalDateTime dataDeBaixa;
}
