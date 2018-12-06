package br.com.academiadev.financeiro.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.academiadev.financeiro.enums.StatusTipo;
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
@Table(name = "contas")
@Entity
public class Conta extends EntidadeAuditavel<Long> {
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	@NotNull
	private Usuario usuario;
	
	@OneToMany(
			mappedBy = "conta",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@Builder.Default
	private List<Baixa> baixas = new ArrayList<>();
	
	@NotNull
	private String nome;
	
	@Builder.Default
	@NotNull
	private BigDecimal saldo = BigDecimal.ZERO;
	
	@Builder.Default
	@NotNull
	private StatusTipo status = StatusTipo.ATIVO;
}
