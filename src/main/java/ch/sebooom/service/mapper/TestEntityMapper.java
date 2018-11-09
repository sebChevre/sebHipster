package ch.sebooom.service.mapper;

import ch.sebooom.domain.*;
import ch.sebooom.service.dto.TestEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TestEntity and its DTO TestEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestEntityMapper extends EntityMapper<TestEntityDTO, TestEntity> {



    default TestEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        TestEntity testEntity = new TestEntity();
        testEntity.setId(id);
        return testEntity;
    }
}
