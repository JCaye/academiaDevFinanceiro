package br.com.academiadev.financeiro.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import br.com.academiadev.financeiro.dto.UsuarioCreatedDTO;
import br.com.academiadev.financeiro.dto.UsuarioDTO;
import br.com.academiadev.financeiro.dto.UsuarioDetailDTO;
import br.com.academiadev.financeiro.entity.Usuario;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UsuarioMapper {
	
	@Mappings({
			@Mapping(target = "nome"),
			@Mapping(target = "email"),
			@Mapping(target = "status")
	})
	public abstract Usuario toEntity(UsuarioDTO dto);
	
	@Mappings({
		@Mapping(target = "id"),
		@Mapping(target = "data_criacao", source = "dataDeCriacao"),
		@Mapping(target = "nome"),
		@Mapping(target = "email"),
		@Mapping(target = "status")
	})
	@Named("toDto")
	public abstract UsuarioCreatedDTO toDto(Usuario entity);
	
	@IterableMapping(qualifiedByName = "toDto")
	public abstract List<UsuarioCreatedDTO> toDtos(List<Usuario> entities);
	
	@Mappings({
		@Mapping(target = "id"),
		@Mapping(target = "data_criacao", source = "dataDeCriacao"),
		@Mapping(target = "nome"),
		@Mapping(target = "email"),
		@Mapping(target = "status"),
		@Mapping(target = "saldo"),
		@Mapping(target = "pagar"),
		@Mapping(target = "receber")
	})
	public abstract UsuarioDetailDTO toDetailDto(Usuario entity);
}
