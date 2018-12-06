package br.com.academiadev.financeiro.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academiadev.financeiro.dto.UsuarioCreatedDTO;
import br.com.academiadev.financeiro.mapper.UsuarioMapper;
import br.com.academiadev.financeiro.service.FinanceiroService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FinanceiroControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private FinanceiroService financeiroService;
	
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Test
	public void criarUsuario_quandoValido_entaoSucesso() throws JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo(usuario.get("nome"))))
				.andExpect(jsonPath("$.email", equalTo(usuario.get("email"))))
				.andExpect(jsonPath("$.status", equalTo(usuario.get("status"))))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.data_criacao", notNullValue()))
				.andExpect(jsonPath("$.data_alteracao", notNullValue()));
	}
	
	@Test
	public void criarUsuario_quandoIvalido_entaoFalha() throws JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		
		mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void buscarUsuarios_quandoSemUsuarios_entaoListaVazia() throws Exception {
		mvc.perform(get("/usuario"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void buscarUsuarios_quandoExistemUsuarios_entaoSucesso() throws Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		mvc.perform(get("/usuario"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome", equalTo(usuario.get("nome"))))
				.andExpect(jsonPath("$[0].email", equalTo(usuario.get("email"))))
				.andExpect(jsonPath("$[0].status", equalTo(usuario.get("status"))))
				.andExpect(jsonPath("$[0].data_criacao", notNullValue()))
				.andExpect(jsonPath("$[0].data_alteracao", notNullValue()));
	}
	
	@Test
	public void buscarUsuarioPorId_quandoIdInexistente_entaoFalha() throws Exception {
		mvc.perform(get("/usuario/1"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code", equalTo(404)))
			.andExpect(jsonPath("$.message", equalTo("Usuario não encontrado.")));
	}
	
	@Test
	public void buscarUsuarioPorId_quandoExistente_entaoSucesso() throws JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		String result = mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> usuarioRetornado = objectMapper.readValue(result, Map.class);
		
		mvc.perform(get(String.format("/usuario/%s", usuarioRetornado.get("id"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo(usuario.get("nome"))))
				.andExpect(jsonPath("$.email", equalTo(usuario.get("email"))))
				.andExpect(jsonPath("$.status", equalTo(usuario.get("status"))))
				.andExpect(jsonPath("$.data_criacao", notNullValue()))
				.andExpect(jsonPath("$.data_alteracao", notNullValue()))
				.andExpect(jsonPath("$.saldo", equalTo(0.0)))
				.andExpect(jsonPath("$.pagar", equalTo(0.0)))
				.andExpect(jsonPath("$.receber", equalTo(0.0)));
	}
}
