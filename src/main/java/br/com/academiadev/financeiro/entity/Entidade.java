package br.com.academiadev.financeiro.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.academiadev.financeiro.enums.StatusTipo;
import br.com.academiadev.financeiro.enums.TipoEntidade;
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
@Table(name = "entidades")
@Entity
public class Entidade extends EntidadeAuditavel<Long> {
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	@NotNull
	private Usuario usuario;
	
	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "entidade",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@Builder.Default
	private List<Lancamento> lancamentos = new ArrayList<>();
	
	@NotBlank
	private String nome;
	
	@NotNull
	private TipoEntidade tipo;
	
	@Builder.Default
	@NotNull
	private StatusTipo status = StatusTipo.ATIVO;
}
