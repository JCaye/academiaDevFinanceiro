package br.com.academiadev.financeiro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import br.com.academiadev.financeiro.dto.EntidadeCreatedDTO;
import br.com.academiadev.financeiro.dto.EntidadeDTO;
import br.com.academiadev.financeiro.entity.Entidade;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class EntidadeMapper {
	@Mappings({
		@Mapping(target = "nome"),
		@Mapping(target = "tipo")
	})
	public abstract Entidade toEntity(EntidadeDTO dto);

	@Mappings({
		@Mapping(target = "id"),
		@Mapping(target = "id_usuario", source = "usuario.id"),
		@Mapping(target = "nome"),
		@Mapping(target = "tipo"),
		@Mapping(target = "status"),
		@Mapping(target = "data_criacao", source = "dataDeCriacao"),
		@Mapping(target = "data_alteracao", source = "dataDeAlteracao")
	})
	public abstract EntidadeCreatedDTO toDto(Entidade entity);
	
	public abstract List<EntidadeCreatedDTO> toDtos(List<Entidade> entities);
}
