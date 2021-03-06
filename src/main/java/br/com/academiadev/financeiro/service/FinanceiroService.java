package br.com.academiadev.financeiro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.financeiro.entity.Baixa;
import br.com.academiadev.financeiro.entity.Conta;
import br.com.academiadev.financeiro.entity.Entidade;
import br.com.academiadev.financeiro.entity.Lancamento;
import br.com.academiadev.financeiro.entity.Usuario;
import br.com.academiadev.financeiro.enums.StatusLancamento;
import br.com.academiadev.financeiro.enums.TipoLancamento;
import br.com.academiadev.financeiro.exception.ContaNaoEncontradaException;
import br.com.academiadev.financeiro.exception.EntidadeNaoEncontradaException;
import br.com.academiadev.financeiro.exception.LancamentoNaoEncontradoException;
import br.com.academiadev.financeiro.exception.UsuarioNaoEncontradoException;
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
		
		Entidade entidade = usuario
				.getEntidades()
				.stream()
				.filter(ent -> ent.getId().equals(idEntidade))
				.findFirst()
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
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getLancamentos()
				.stream()
				.filter(lanc -> lanc.getId().equals(idLancamento))
				.findFirst()
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
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				);
		return entidadeRepository.save(entidade);
	}
	
	public Entidade buscarEntidade(Long idUsuario, Long idEntidade) {
		return usuarioRepository
			.findById(idUsuario)
			.orElseThrow(() -> new UsuarioNaoEncontradoException())
			.getEntidades()
			.stream()
			.filter(ent -> ent.getId().equals(idEntidade))
			.findFirst()
			.orElseThrow(() -> new EntidadeNaoEncontradaException());
	}
	
	public List<Entidade> buscarEntidadesDoUsuario(Long idUsuario){
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getEntidades();
	}
	
	public Conta criarConta(Long idUsuario, Conta conta) {
		conta.setUsuario(usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException()));
		return contaRepository.save(conta);
	}
	
	public Conta buscarConta(Long idUsuario, Long idConta) {
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getContas()
				.stream()
				.filter(cnt -> cnt.getId().equals(idConta))
				.findFirst()
				.orElseThrow(() -> new ContaNaoEncontradaException());
	}
	
	public List<Lancamento> buscarLancamentosDaConta(Long idUsuario, Long idConta) {
		return usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException())
				.getContas()
				.stream()
				.filter(cnt -> cnt.getId().equals(idConta))
				.findFirst()
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
	
	public String resolverLancamento(Long idUsuario, Long idConta, Long idLancamento, String acao) {
		Usuario usuario = usuarioRepository
				.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());
		
		Conta conta = usuario
				.getContas()
				.stream()
				.filter(cnt -> cnt.getId().equals(idConta))
				.findFirst()
				.orElseThrow(() -> new ContaNaoEncontradaException());
		
		Lancamento lancamento = usuario
				.getLancamentos()
				.stream()
				.filter(lnc -> lnc.getId().equals(idLancamento))
				.findFirst()
				.orElseThrow(() -> new LancamentoNaoEncontradoException());
		
		if (acao.equals("baixar")) {
			if (lancamento.getTipo().equals(TipoLancamento.PAGAR)) {
				conta.setSaldo(conta.getSaldo().subtract(lancamento.getValor()));
				usuario.setSaldo(usuario.getSaldo().subtract(lancamento.getValor()));
				usuario.setPagar(usuario.getPagar().subtract(lancamento.getValor()));
			}
			if (lancamento.getTipo().equals(TipoLancamento.RECEBER)) {
				conta.setSaldo(conta.getSaldo().add(lancamento.getValor()));
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
			baixaRepository.save(baixa);
			lancamento.setBaixa(baixa);
			lancamento.setDataDeBaixa(LocalDateTime.now());
			lancamento.setStatus(StatusLancamento.BAIXADO);
			lancamentoRepository.save(lancamento);
			return "Lancamento baixado com sucesso";
		}
		
		if (acao.equals("estornar")) {
			if (!lancamento.getStatus().equals(StatusLancamento.BAIXADO))
				throw new UnsupportedOperationException();
			if (lancamento.getTipo().equals(TipoLancamento.PAGAR)) {
				conta.setSaldo(conta.getSaldo().add(lancamento.getValor()));
				usuario.setSaldo(usuario.getSaldo().add(lancamento.getValor()));
				usuario.setPagar(usuario.getPagar().add(lancamento.getValor()));
			}
			if (lancamento.getTipo().equals(TipoLancamento.RECEBER)) {
				conta.setSaldo(conta.getSaldo().subtract(lancamento.getValor()));
				usuario.setSaldo(usuario.getSaldo().subtract(lancamento.getValor()));
				usuario.setReceber(usuario.getReceber().add(lancamento.getValor()));
			}
			lancamento.setDataDeBaixa(null);
			lancamento.setStatus(StatusLancamento.ABERTO);
			baixaRepository.delete(lancamento.getBaixa());
			lancamentoRepository.save(lancamento);
			return "Lancamento estornado com sucesso";
		}
		
		throw new UnsupportedOperationException();
	}
}
