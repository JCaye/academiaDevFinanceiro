package br.com.academiadev.financeiro.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "usuarios")
@Entity
public class Usuario extends EntidadeAuditavel<Long> {
	
	@NotNull
	private String nome;
	
	@NotNull
	private String email;
	
	@NotNull
	private StatusTipo status;
	
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "usuario",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@Builder.Default
	private List<Entidade> entidades = new ArrayList<>();
	
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "usuario",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@Builder.Default
	private List<Lancamento> lancamentos = new ArrayList<>();
	
	@OneToMany(
			fetch = FetchType.EAGER,
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
	private List<Conta> contas = new ArrayList<>();
	
	@Builder.Default
	private BigDecimal saldo = BigDecimal.ZERO;
	
	@Builder.Default
	private BigDecimal pagar = BigDecimal.ZERO;
	
	@Builder.Default
	private BigDecimal receber = BigDecimal.ZERO;
}
