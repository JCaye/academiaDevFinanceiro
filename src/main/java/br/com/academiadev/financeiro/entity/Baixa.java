package br.com.academiadev.financeiro.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "baixas")
@Entity
public class Baixa extends EntidadeAuditavel<Long>{
	@ManyToOne
	@JoinColumn(name = "id_conta")
	@NotNull
	private Conta conta;
	
	@OneToOne
	@JoinColumn(name = "id_lancamento")
	@NotNull
	private Lancamento lancamento;
	
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	private LocalDateTime dataDeBaixa;
	
}
