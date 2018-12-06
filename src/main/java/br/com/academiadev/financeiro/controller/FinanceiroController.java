package br.com.academiadev.financeiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.financeiro.dto.ContaCreatedDTO;
import br.com.academiadev.financeiro.dto.ContaDTO;
import br.com.academiadev.financeiro.dto.EntidadeCreatedDTO;
import br.com.academiadev.financeiro.dto.EntidadeDTO;
import br.com.academiadev.financeiro.dto.LancamentoCreatedDTO;
import br.com.academiadev.financeiro.dto.LancamentoDTO;
import br.com.academiadev.financeiro.dto.UsuarioCreatedDTO;
import br.com.academiadev.financeiro.dto.UsuarioDTO;
import br.com.academiadev.financeiro.dto.UsuarioDetailDTO;
import br.com.academiadev.financeiro.mapper.ContaMapper;
import br.com.academiadev.financeiro.mapper.EntidadeMapper;
import br.com.academiadev.financeiro.mapper.LancamentoMapper;
import br.com.academiadev.financeiro.mapper.UsuarioMapper;
import br.com.academiadev.financeiro.service.FinanceiroService;

@RestController
@RequestMapping("/usuario")
public class FinanceiroController {
	@Autowired
	private FinanceiroService financeiroService;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Autowired
	private LancamentoMapper lancamentoMapper;
	
	@Autowired
	private ContaMapper contaMapper;
	
	@Autowired
	private EntidadeMapper entidadeMapper;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioCreatedDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioMapper.toDto(
				financeiroService.criarUsuario(
						usuarioMapper.toEntity(usuarioDTO)
						));
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<UsuarioCreatedDTO> listarUsuarios(){
		return usuarioMapper.toDtos(
				financeiroService.buscarUsuarios()
				);
	}
	
	@GetMapping("/{idUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDetailDTO buscarUsuario(@PathVariable Long idUsuario) {
		return usuarioMapper.toDetailDto(
				financeiroService.buscarUsuario(idUsuario)
				);
	}
	
	@PostMapping("/{idUsuario}/entidade")
	@ResponseStatus(HttpStatus.CREATED)
	public EntidadeCreatedDTO criarEntidade(@PathVariable Long idUsuario, @RequestBody EntidadeDTO entidade) {
		return entidadeMapper.toDto(
				financeiroService.criarEntidade(
						idUsuario, entidadeMapper.toEntity(entidade)
						));
	}
	
	@GetMapping("/{idUsuario}/entidade")
	@ResponseStatus(HttpStatus.OK)
	public List<EntidadeCreatedDTO> buscarEntidades(@PathVariable Long idUsuario){
		return entidadeMapper.toDtos(
				financeiroService.buscarEntidadesDoUsuario(idUsuario)
				);
	}
	
	@GetMapping("/{idUsuario}/entidade/{idEntidade}")
	@ResponseStatus(HttpStatus.OK)
	public EntidadeDTO buscarEntidade(@PathVariable Long idUsuario, @PathVariable Long idEntidade) {
		return entidadeMapper.toDto(
				financeiroService.buscarEntidade(idUsuario, idEntidade));
	}
	
	@PostMapping("/{idUsuario}/entidade/{idEntidade}/lancamento")
	@ResponseStatus(HttpStatus.CREATED)
	public LancamentoCreatedDTO criarLancamento(@RequestBody LancamentoDTO lancamento, @PathVariable Long idUsuario, @PathVariable Long idEntidade) {
		return lancamentoMapper.toDto(
				financeiroService.criarLancamento(
						idUsuario, idEntidade, lancamentoMapper.toEntity(lancamento)
						));
	}
	
	@GetMapping("/{idUsuario}/lancamento")
	@ResponseStatus(HttpStatus.OK)
	public List<LancamentoCreatedDTO> buscarLancamentosDoUsuario(@PathVariable Long idUsuario){
		return lancamentoMapper.toDtos(
				financeiroService.buscarLancamentosDoUsuario(idUsuario)
				);
	}
	
	@PostMapping("/{idUsuario}/conta")
	@ResponseStatus(HttpStatus.CREATED)
	public ContaCreatedDTO criarConta(@PathVariable Long idUsuario, @RequestBody ContaDTO conta) {
		return contaMapper.toDto(
				financeiroService.criarConta(
						idUsuario, contaMapper.toEntity(conta)
						));
	}
	
	@GetMapping("/{idUsuario}/conta")
	@ResponseStatus(HttpStatus.OK)
	public List<ContaCreatedDTO> buscarContasDoUsuario(@PathVariable Long idUsuario){
		return contaMapper.toDtos(
				financeiroService.buscarContasDoUsuario(idUsuario)
				);
	}
	
	@GetMapping("/{idUsuario}/conta/{idConta}")
	@ResponseStatus(HttpStatus.OK)
	public ContaCreatedDTO buscarConta(@PathVariable Long idUsuario, @PathVariable Long idConta) {
		return contaMapper.toDto(
				financeiroService.buscarConta(idUsuario, idConta)
				);
	}
	
	@GetMapping("{idUsuario}/conta/{idConta}/lancamentos")
	@ResponseStatus(HttpStatus.OK)
	public List<LancamentoCreatedDTO> buscarLancamentosDaConta(@PathVariable Long idUsuario, @PathVariable Long idConta){
		return lancamentoMapper.toDtos(
				financeiroService.buscarLancamentosDaConta(idUsuario, idConta)
				);
	}
	
	@PostMapping("/{idUsuario}/conta/{idConta}/{idLancamento}")
	@ResponseStatus(HttpStatus.OK)
	public String resolverLancamento(@PathVariable Long idUsuario, @PathVariable Long idConta, @PathVariable Long idLancamento, @RequestParam("do") String acao) {
		return new String();
	}
}
