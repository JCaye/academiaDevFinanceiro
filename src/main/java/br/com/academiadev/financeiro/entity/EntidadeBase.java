package br.com.academiadev.financeiro.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class EntidadeBase<PK> implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private PK id;
}
