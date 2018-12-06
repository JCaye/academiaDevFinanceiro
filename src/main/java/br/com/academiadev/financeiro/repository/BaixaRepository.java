package br.com.academiadev.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.financeiro.entity.Baixa;

@Repository
public interface BaixaRepository extends JpaRepository<Baixa, Long> {

}
