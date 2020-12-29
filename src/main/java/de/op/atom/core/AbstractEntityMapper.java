package de.op.atom.core;

import org.modelmapper.ModelMapper;

public class AbstractEntityMapper<DTO, ENTITY> {

    protected final ModelMapper modelMapper;
    private final Class<ENTITY> entityClass;
    private final Class<DTO> dtoClass;

    protected AbstractEntityMapper(Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        modelMapper = new ModelMapper();
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public DTO createDtoFromEntity(ENTITY e) {
        if(e == null) {
            return null;
        }
        return this.modelMapper.map(e, this.dtoClass);
    }

    public ENTITY createEntityFromDto(DTO e) {
        if(e == null) {
            return null;
        }
        return this.modelMapper.map(e , this.entityClass);
    }

}
