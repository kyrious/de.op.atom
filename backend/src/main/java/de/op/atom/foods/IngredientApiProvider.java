package de.op.atom.foods;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import de.op.atom.core.ErrorResponseBuilder;
import de.op.atom.foods.domain.entity.Ingredient;
import de.op.atom.foods.mapper.IngredientMapper;
import de.op.atom.foods.services.IngredientService;
import de.op.atom.gen.api.NotFoundException;
import de.op.atom.gen.foods.model.IngredientDTO;

@ApplicationScoped
public class IngredientApiProvider {

    @Inject
    IngredientService ingredientService;
    @Inject
    IngredientMapper ingredientMapper;

    @Transactional
    public Response getIngredientById(Long id, SecurityContext securityContext) throws NotFoundException {
        Ingredient ingredientWithId = this.ingredientService.getIngredientWithId(id);
        IngredientDTO dto = this.ingredientMapper.createDtoFromEntity(ingredientWithId);
        return Response.ok(dto)
                       .build();
    }

    @Transactional
    public Response getIngredients(SecurityContext securityContext) throws NotFoundException {
        List<Ingredient> allIngredients = this.ingredientService.getAllIngredients();
        List<IngredientDTO> allIngredientDTOs = allIngredients.stream()
                                                              .map(this.ingredientMapper::createDtoFromEntity)
                                                              .collect(Collectors.toList());
        return Response.ok(allIngredientDTOs)
                       .build();
    }

    @Transactional
    public Response postNewIngredients(IngredientDTO ingredientDTO, SecurityContext securityContext)
            throws NotFoundException {
        if (ingredientDTO.getVersion() != null) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "There must not be a version, when creating a new entity")
                                       .build();
        }
        Ingredient newEntity = this.ingredientMapper.createEntityFromDto(ingredientDTO);
        this.ingredientService.updateOrCreate(newEntity);
        IngredientDTO returnedDto = this.ingredientMapper.createDtoFromEntity(newEntity);
        return Response.ok(returnedDto)
                       .build();
    }

    @Transactional
    public Response putIngredientToId(Long id, IngredientDTO ingredientDTO, SecurityContext securityContext) {
        if (ingredientDTO.getVersion() == null) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "There must be a version, when updating an existing entity")
                                       .build();
        }
        if (ingredientDTO.getId() == null) {
            return ErrorResponseBuilder.error(Status.CONFLICT, "There must be an ID, when updating an existing entity")
                                       .build();
        }
        if (!Objects.equals(ingredientDTO.getId(), id)) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "Given ID must match entities ID, when updating an existing entity. Pathparameter-ID='%d'; Entity-ID='%d'",
                                              id,
                                              ingredientDTO.getId())
                                       .build();
        }
        Ingredient newEntity = this.ingredientMapper.createEntityFromDto(ingredientDTO);
        try {
            this.ingredientService.updateOrCreate(newEntity);
            IngredientDTO returnedDto = this.ingredientMapper.createDtoFromEntity(newEntity);
            return Response.ok(returnedDto)
                           .build();
        } catch (OptimisticLockException e) {
            return ErrorResponseBuilder.error(Status.CONFLICT,
                                              "Given entity was out of date. Reread the entity, make your changes and re-put it again.")
                                       .build();
        }
    }

    @Transactional
    public Response deleteIngredientWithId(Long id, SecurityContext securityContext) {
        if (id == null) {
            return Response.status(Status.BAD_REQUEST)
                           .build();
        }
        try {
            this.ingredientService.deleteIngredientWithId(id);
            return Response.ok()
                           .build();
        } catch (NoSuchElementException e) {
            return Response.status(Status.NOT_FOUND)
                           .build();
        }
    }

}
