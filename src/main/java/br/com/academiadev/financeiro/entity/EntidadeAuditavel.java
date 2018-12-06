package br.com.academiadev.financeiro.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class EntidadeAuditavel<PK> extends EntidadeBase<PK> {
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime dataDeCriacao;
	
	@LastModifiedDate
	private LocalDateTime dataDeAlteracao;
}
