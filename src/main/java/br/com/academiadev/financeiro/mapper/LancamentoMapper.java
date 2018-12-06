package br.com.academiadev.financeiro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import br.com.academiadev.financeiro.dto.LancamentoCreatedDTO;
import br.com.academiadev.financeiro.dto.LancamentoDTO;
import br.com.academiadev.financeiro.entity.Lancamento;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LancamentoMapper {
	@Mappings({
		@Mapping(target = "descricao"),
		@Mapping(target = "observacao"),
		@Mapping(target = "valor"),
		@Mapping(target = "tipo")
	})
	public abstract Lancamento toEntity(LancamentoDTO dto);
	
	@Mappings({
		@Mapping(target = "id"),
		@Mapping(target = "status"),
		@Mapping(target = "data_criacao", source = "dataDeCriacao")
	})
	public abstract LancamentoCreatedDTO toDto(Lancamento entity);

	public abstract List<LancamentoCreatedDTO> toDtos(List<Lancamento> entities);
}
