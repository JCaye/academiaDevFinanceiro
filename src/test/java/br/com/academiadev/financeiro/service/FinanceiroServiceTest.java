package br.com.academiadev.financeiro.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academiadev.financeiro.entity.Entidade;
import br.com.academiadev.financeiro.entity.Usuario;
import br.com.academiadev.financeiro.enums.StatusTipo;
import br.com.academiadev.financeiro.enums.TipoEntidade;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FinanceiroServiceTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private FinanceiroService financeiroService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void criarUsuario_quandoValido_entaoSucesso() {
		Usuario usuario = financeiroService.criarUsuario(
				Usuario
				.builder()
				.nome("fulano")
				.email("fulano@test.com")
				.status(StatusTipo.ATIVO)
				.build()
				);
		
		Usuario usuarioSalvo = financeiroService.buscarUsuario(usuario.getId());
		
		assertThat(usuario.getId(), equalTo(usuarioSalvo.getId()));
		assertThat(usuario.getNome(), equalTo("fulano"));
		assertThat(usuario.getEmail(), equalTo("fulano@test.com"));
		assertThat(usuario.getStatus(), equalTo(StatusTipo.ATIVO));
		assertThat(usuario.getId(), notNullValue());
		assertThat(usuario.getDataDeCriacao(), notNullValue());
		assertThat(usuario.getDataDeAlteracao(), notNullValue());
		assertThat(usuario.getContas(), hasSize(0));
		assertThat(usuario.getEntidades(), hasSize(0));
		assertThat(usuario.getLancamentos(), hasSize(0));
	}
	
	@Test
	public void criarEntidade_quandoValida_entaoSucesso() {
		Usuario usuario = financeiroService.criarUsuario(
				Usuario
				.builder()
				.nome("fulano")
				.email("fulano@test.com")
				.status(StatusTipo.ATIVO)
				.build()
				);
		
		Entidade entidade = financeiroService.criarEntidade(
				usuario.getId(),
				Entidade.builder()
				.nome("entidade_do_fulano")
				.tipo(TipoEntidade.CLIENTE)
				.build()
				);
		
		assertThat(entidade.getNome(), equalTo("entidade_do_fulano"));
		assertThat(entidade.getTipo(), equalTo(TipoEntidade.CLIENTE));
		assertThat(entidade.getStatus(), equalTo(StatusTipo.ATIVO));
		assertThat(entidade.getUsuario().getId(), equalTo(usuario.getId()));
		assertThat(entidade.getId(), notNullValue());
		assertThat(entidade.getDataDeCriacao(), notNullValue());
		assertThat(entidade.getDataDeAlteracao(), notNullValue());
	}
	
	@Test
	public void buscarEntidades_quandoSemEntidades_entaoListaVazia() {
		Usuario usuario = financeiroService.criarUsuario(
				Usuario
				.builder()
				.nome("fulano")
				.email("fulano@test.com")
				.status(StatusTipo.ATIVO)
				.build()
				);
		
		List<Entidade> entidadesDoUsuario = financeiroService.buscarEntidadesDoUsuario(usuario.getId());
		assertThat(entidadesDoUsuario, hasSize(0));
	}
	
	@Test
	public void buscarEntidades_quandoTemEntidades_entaoSucesso() {
		Usuario usuario = financeiroService.criarUsuario(
				Usuario
				.builder()
				.nome("fulano")
				.email("fulano@test.com")
				.status(StatusTipo.ATIVO)
				.build()
				);
		
		financeiroService.criarEntidade(
				usuario.getId(),
				Entidade.builder()
				.nome("entidade_do_fulano")
				.tipo(TipoEntidade.CLIENTE)
				.build()
				);
		
		List<Entidade> entidadesDoUsuario = financeiroService.buscarEntidadesDoUsuario(usuario.getId());
		assertThat(entidadesDoUsuario, hasSize(1));
		
		Entidade entidade = entidadesDoUsuario.get(0);
		
		assertThat(entidade.getNome(), equalTo("entidade_do_fulano"));
		assertThat(entidade.getTipo(), equalTo(TipoEntidade.CLIENTE));
		assertThat(entidade.getStatus(), equalTo(StatusTipo.ATIVO));
		assertThat(entidade.getUsuario().getId(), equalTo(usuario.getId()));
		assertThat(entidade.getId(), notNullValue());
		assertThat(entidade.getDataDeCriacao(), notNullValue());
		assertThat(entidade.getDataDeAlteracao(), notNullValue());
	}

	@Test
	public void buscarEntidadePorId_quantoTemEntidade_entaoSucesso() {
		Usuario usuario = financeiroService.criarUsuario(
				Usuario
				.builder()
				.nome("fulano")
				.email("fulano@test.com")
				.status(StatusTipo.ATIVO)
				.build()
				);
		
		Entidade entidade = financeiroService.criarEntidade(
				usuario.getId(),
				Entidade.builder()
				.nome("entidade_do_fulano")
				.tipo(TipoEntidade.CLIENTE)
				.build()
				);
		
		entidade = financeiroService.buscarEntidade(usuario.getId(), entidade.getId());
		
		assertThat(entidade.getNome(), equalTo("entidade_do_fulano"));
		assertThat(entidade.getTipo(), equalTo(TipoEntidade.CLIENTE));
		assertThat(entidade.getStatus(), equalTo(StatusTipo.ATIVO));
		assertThat(entidade.getUsuario().getId(), equalTo(usuario.getId()));
		assertThat(entidade.getId(), notNullValue());
		assertThat(entidade.getDataDeCriacao(), notNullValue());
		assertThat(entidade.getDataDeAlteracao(), notNullValue());
	}
}
