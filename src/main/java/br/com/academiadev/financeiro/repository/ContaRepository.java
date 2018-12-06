package br.com.academiadev.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.financeiro.entity.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

}
