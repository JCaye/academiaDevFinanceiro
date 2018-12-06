package br.com.academiadev.financeiro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.academiadev.financeiro.dto.ContaCreatedDTO;
import br.com.academiadev.financeiro.dto.EntidadeCreatedDTO;
import br.com.academiadev.financeiro.dto.LancamentoCreatedDTO;
import br.com.academiadev.financeiro.dto.UsuarioCreatedDTO;
import br.com.academiadev.financeiro.entity.Baixa;
import br.com.academiadev.financeiro.entity.Conta;
import br.com.academiadev.financeiro.entity.Entidade;
import br.com.academiadev.financeiro.entity.Lancamento;
import br.com.academiadev.financeiro.entity.Usuario;
import br.com.academiadev.financeiro.enums.TipoLancamento;
import br.com.academiadev.financeiro.exceptions.ContaNaoEncontradaException;
import br.com.academiadev.financeiro.exceptions.EntidadeNaoEncontradaException;
import br.com.academiadev.financeiro.exceptions.LancamentoNaoEncontradoException;
import br.com.academiadev.financeiro.exceptions.UsuarioNaoEncontradoException;
import br.com.academiadev.financeiro.mapper.ContaMapper;
import br.com.academiadev.financeiro.mapper.EntidadeMapper;
import br.com.academiadev.financeiro.mapper.LancamentoMapper;
import br.com.academiadev.financeiro.mapper.UsuarioMapper;
import br.com.academiadev.financeiro.repository.BaixaRepository;
import br.com.academiadev.financeiro.repository.ContaRepository;
import br.com.academiadev.financeiro.repository.EntidadeRepository;
import br.com.academiadev.financeiro.repository.LancamentoRepository;
import br.com.academiadev.financeiro.repository.UsuarioRepository;

@Service
public class FinanceiroService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EntidadeRepository entidadeRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired 
	private BaixaRepository baixaRepository;
	
	public Usuario criarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarUsuario(Long idUsuario){
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());
	}
	
	public List<Usuario> buscarUsuarios(){
		return usuarioRepository.findAll();
	}

	public Lancamento criarLancamento(Long idUsuario, Long idEntidade, Lancamento lancamento) {
		Usuario usuario = usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());
		
		Entidade exemplo = new Entidade();
		exemplo.setUsuario(usuario);
		exemplo.setId(idEntidade);
		Entidade entidade = entidadeRepository
				.findOne(Example.of(exemplo))
				.orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		if (lancamento.getTipo().equals(TipoLancamento.PAGAR))
			usuario.setPagar(usuario.getPagar().add(lancamento.getValor()));
		if (lancamento.getTipo().equals(TipoLancamento.RECEBER))
			usuario.setReceber(usuario.getReceber().add(lancamento.getValor()));
		
		lancamento.setUsuario(usuario);
		lancamento.setEntidade(entidade);
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento buscarLancamento(Long idUsuario, Long idLancamento) {
		Usuario usuario = usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());
		
		Lancamento lancamento = new Lancamento();
		lancamento.setId(idLancamento);
		lancamento.setUsuario(usuario);
		return lancamentoRepository
				.findOne(Example.of(lancamento))
				.orElseThrow(() -> new LancamentoNaoEncontradoException());
	}
	
	public List<Lancamento> buscarLancamentosDoUsuario(Long idUsuario){
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getLancamentos();
	}
	
	public Entidade criarEntidade(Long idUsuario, Entidade entidade) {
		entidade.setUsuario(
				usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		return entidadeRepository.save(entidade);
	}
	
	public Entidade buscarEntidade(Long idUsuario, Long idEntidade) {
		Entidade entidade = new Entidade();
		entidade.setId(idEntidade);
		entidade.setUsuario(
				usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		
		return entidadeRepository
				.findOne(Example.of(entidade))
				.orElseThrow(() -> new EntidadeNaoEncontradaException());
	}
	
	public List<Entidade> buscarEntidadesDoUsuario(Long idUsuario){
		Entidade entidade = new Entidade();
		entidade.setUsuario(usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		
		return entidadeRepository.findAll(Example.of(entidade));
	}
	
	public Conta criarConta(Long idUsuario, Conta conta) {
		conta.setUsuario(usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		
		return contaRepository.save(conta);
	}
	
	public Conta buscarConta(Long idUsuario, Long idConta) {
		Conta conta = new Conta();
		conta.setUsuario(usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		conta.setId(idConta);
		return contaRepository
				.findOne(Example.of(conta))
				.orElseThrow(() -> new ContaNaoEncontradaException());
	}
	
	public List<Lancamento> buscarLancamentosDaConta(Long idConta) {
		return contaRepository
				.findById(idConta)
				.orElseThrow(() -> new ContaNaoEncontradaException())
				.getBaixas()
				.stream()
				.map(b -> b.getLancamento())
				.collect(Collectors.toList());
	}
	
	public List<Conta> buscarContasDoUsuario(Long idUsuario){
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getContas();
	}
	
	public void resolverLancamento(Long idUsuario, Long idConta, Long idLancamento, String acao) {
		Usuario usuario = usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());
		
		Conta exemploConta = new Conta();
		exemploConta.setUsuario(usuario);
		exemploConta.setId(idConta);
		Conta conta = contaRepository
				.findOne(Example.of(exemploConta))
				.orElseThrow(() -> new ContaNaoEncontradaException());
		
		Lancamento exemploLanc = new Lancamento();
		exemploLanc.setUsuario(usuario);
		exemploLanc.setId(idLancamento);
		Lancamento lancamento = lancamentoRepository
				.findOne(Example.of(exemploLanc))
				.orElseThrow(() -> new LancamentoNaoEncontradoException());
		
		if (acao.equals("baixar")) {
			if (lancamento.getTipo().equals(TipoLancamento.PAGAR)) {
				usuario.setSaldo(usuario.getSaldo().subtract(lancamento.getValor()));
				usuario.setPagar(usuario.getPagar().subtract(lancamento.getValor()));
			}
			if (lancamento.getTipo().equals(TipoLancamento.RECEBER)) {
				usuario.setSaldo(usuario.getSaldo().add(lancamento.getValor()));
				usuario.setReceber(usuario.getReceber().subtract(lancamento.getValor()));
			}
				
			Baixa baixa = Baixa
				.builder()
				.conta(conta)
				.lancamento(lancamento)
				.dataDeBaixa(LocalDateTime.now())
				.valor(lancamento.getValor())
				.build();
			lancamento.setBaixa(baixa);
			lancamento.setDataDeBaixa(LocalDateTime.now());
			lancamentoRepository.save(lancamento);
		}
		
		if (acao.equals("estornar")) {
			if (lancamento.getTipo().equals(TipoLancamento.PAGAR)) {
				usuario.setSaldo(usuario.getSaldo().add(lancamento.getValor()));
				usuario.setPagar(usuario.getPagar().add(lancamento.getValor()));
			}
			if (lancamento.getTipo().equals(TipoLancamento.RECEBER)) {
				usuario.setSaldo(usuario.getSaldo().subtract(lancamento.getValor()));
				usuario.setReceber(usuario.getReceber().add(lancamento.getValor()));
			}
			lancamento.setDataDeBaixa(null);
			baixaRepository.delete(lancamento.getBaixa());
		}
		
	}
}
