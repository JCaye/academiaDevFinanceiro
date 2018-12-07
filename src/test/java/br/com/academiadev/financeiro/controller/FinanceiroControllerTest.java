package br.com.academiadev.financeiro.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FinanceiroControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void criarUsuario_quandoInvalido_entaoFalha() throws JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		
		mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}
	
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
				.andExpect(jsonPath("$", not(empty())));
	}
	
	@Test
	public void buscarUsuarioPorId_quandoIdInexistente_entaoFalha() throws Exception {
		mvc.perform(get("/usuario/999"))
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
	
	@Test
	public void criarEntidade_quandoUsuarioInexistente_entaoFalha() throws JsonProcessingException, Exception {
		Map<String, String> entidade = new HashMap<>();
		entidade.put("nome", "fulano");
		entidade.put("tipo", "FORNECEDOR");
		
		mvc.perform(post("/usuario/999/entidade")
				.content(objectMapper.writeValueAsString(entidade))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code", equalTo(404)))
				.andExpect(jsonPath("$.message", equalTo("Usuario não encontrado.")));
	}
	
	@Test
	public void criarEntidade_quandoEntidadeInvalida_entaoFalha() throws UnsupportedEncodingException, JsonProcessingException, Exception {
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
		
		Map<String, String> entidade = new HashMap<>();
		entidade.put("nome", "fulano");
		
		mvc.perform(post(String.format("/usuario/%s/entidade", usuarioRetornado.get("id")))
				.content(objectMapper.writeValueAsString(entidade))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void criarEntidade_quandoValida_entaoSucesso() throws UnsupportedEncodingException, JsonProcessingException, Exception {
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
		
		Map<String, String> entidade = new HashMap<>();
		entidade.put("nome", "fulano");
		entidade.put("tipo", "FORNECEDOR");
		
		mvc.perform(post(String.format("/usuario/%s/entidade", usuarioRetornado.get("id")))
				.content(objectMapper.writeValueAsString(entidade))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo(entidade.get("nome"))))
				.andExpect(jsonPath("$.tipo", equalTo(entidade.get("tipo"))))
				.andExpect(jsonPath("$.id_usuario", equalTo(usuarioRetornado.get("id"))))
				.andExpect(jsonPath("$.status", equalTo("ATIVO")))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.data_criacao", notNullValue()))
				.andExpect(jsonPath("$.data_alteracao", notNullValue()));
	}

	@Test
	public void buscarEntidades_quandoUsuarioInexistente_entaoFalha() throws Exception {
		mvc.perform(get("/usuario/999/entidade"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code", equalTo(404)))
			.andExpect(jsonPath("$.message", equalTo("Usuario não encontrado.")));
	}

	@Test
	public void buscarEntidades_quandoSemEntidades_entaoListaVazia() throws UnsupportedEncodingException, JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		
		String resultUsuario = mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> usuarioRetornado = objectMapper.readValue(resultUsuario, Map.class);

		mvc.perform(get(String.format("/usuario/%s/entidade", usuarioRetornado.get("id"))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void buscarEntidades_quandoTemEntidades_entaoSucesso() throws UnsupportedEncodingException, JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		String resultUsuario = mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> usuarioRetornado = objectMapper.readValue(resultUsuario, Map.class);
		
		Map<String, String> entidade = new HashMap<>();
		entidade.put("nome", "fulano");
		entidade.put("tipo", "FORNECEDOR");
		
		mvc.perform(post(String.format("/usuario/%s/entidade", usuarioRetornado.get("id")))
			.content(objectMapper.writeValueAsString(entidade))
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isCreated());
		
		mvc.perform(get(String.format("/usuario/%s/entidade", usuarioRetornado.get("id"))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].nome", equalTo(entidade.get("nome"))))
			.andExpect(jsonPath("$[0].tipo", equalTo(entidade.get("tipo"))))
			.andExpect(jsonPath("$[0].id_usuario", equalTo(usuarioRetornado.get("id"))))
			.andExpect(jsonPath("$[0].status", equalTo("ATIVO")))
			.andExpect(jsonPath("$[0].id", notNullValue()))
			.andExpect(jsonPath("$[0].data_criacao", notNullValue()))
			.andExpect(jsonPath("$[0].data_alteracao", notNullValue()));
	}

	@Test
	public void buscarEntidadePorId_quandoUsuarioInexistente_entaoFalha() throws Exception {
		mvc.perform(get("/usuario/999/entidade/999"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code", equalTo(404)))
			.andExpect(jsonPath("$.message", equalTo("Usuario não encontrado.")));
	}
	
	@Test
	public void buscarEntidadePorId_quandoEntidadeInexistente_entaoFalha() throws UnsupportedEncodingException, JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		String resultUsuario = mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> usuarioRetornado = objectMapper.readValue(resultUsuario, Map.class);
		
		mvc.perform(get(String.format("/usuario/%s/entidade/999", usuarioRetornado.get("id"))))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code", equalTo(404)))
			.andExpect(jsonPath("$.message", equalTo("Entidade não encontrada.")));
	}
	
	@Test
	public void buscarEntidadePorId_quandoEntidadeExistente_entaoSucesso() throws JsonProcessingException, Exception {
		Map<String, String> usuario = new HashMap<>();
		usuario.put("nome", "fulano");
		usuario.put("email", "fulano@test.com");
		usuario.put("status", "ATIVO");
		
		String resultUsuario = mvc.perform(post("/usuario")
				.content(objectMapper.writeValueAsString(usuario))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> usuarioRetornado = objectMapper.readValue(resultUsuario, Map.class);
		
		Map<String, String> entidade = new HashMap<>();
		entidade.put("nome", "fulano");
		entidade.put("tipo", "FORNECEDOR");
		
		String resultEntidade = mvc.perform(post(String.format("/usuario/%s/entidade", usuarioRetornado.get("id")))
				.content(objectMapper.writeValueAsString(entidade))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		Map<String, String> entidadeRetornada = objectMapper.readValue(resultEntidade, Map.class);
		
		mvc.perform(get(String.format("/usuario/%s/entidade/%s", usuarioRetornado.get("id"), entidadeRetornada.get("id"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", not(empty())));
	}
}
