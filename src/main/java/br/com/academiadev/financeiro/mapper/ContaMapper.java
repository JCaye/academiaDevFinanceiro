package br.com.academiadev.financeiro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import br.com.academiadev.financeiro.dto.ContaCreatedDTO;
import br.com.academiadev.financeiro.dto.ContaDTO;
import br.com.academiadev.financeiro.entity.Conta;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ContaMapper {
	@Mappings({
		@Mapping(target = "nome")
	})
	public abstract Conta toEntity(ContaDTO dto);

	@Mappings({
		@Mapping(target = "id"),
		@Mapping(target = "id_usuario", source = "usuario.id"),
		@Mapping(target = "nome"),
		@Mapping(target = "saldo"),
		@Mapping(target = "data_criacao", source = "dataDeCriacao"),
		@Mapping(target = "data_alteracao", source = "dataDeAlteracao")
	})
	public abstract ContaCreatedDTO toDto(Conta entity);
	
	public abstract List<ContaCreatedDTO> toDtos(List<Conta> entities);
}
