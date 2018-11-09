package ch.sebooom.service.impl;

import ch.sebooom.service.TestEntityService;
import ch.sebooom.domain.TestEntity;
import ch.sebooom.repository.TestEntityRepository;
import ch.sebooom.service.dto.TestEntityDTO;
import ch.sebooom.service.mapper.TestEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TestEntity.
 */
@Service
@Transactional
public class TestEntityServiceImpl implements TestEntityService {

    private final Logger log = LoggerFactory.getLogger(TestEntityServiceImpl.class);

    private final TestEntityRepository testEntityRepository;

    private final TestEntityMapper testEntityMapper;

    public TestEntityServiceImpl(TestEntityRepository testEntityRepository, TestEntityMapper testEntityMapper) {
        this.testEntityRepository = testEntityRepository;
        this.testEntityMapper = testEntityMapper;
    }

    /**
     * Save a testEntity.
     *
     * @param testEntityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TestEntityDTO save(TestEntityDTO testEntityDTO) {
        log.debug("Request to save TestEntity : {}", testEntityDTO);
        TestEntity testEntity = testEntityMapper.toEntity(testEntityDTO);
        testEntity = testEntityRepository.save(testEntity);
        return testEntityMapper.toDto(testEntity);
    }

    /**
     * Get all the testEntities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TestEntityDTO> findAll() {
        log.debug("Request to get all TestEntities");
        return testEntityRepository.findAll().stream()
            .map(testEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one testEntity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TestEntityDTO findOne(Long id) {
        log.debug("Request to get TestEntity : {}", id);
        TestEntity testEntity = testEntityRepository.findOne(id);
        return testEntityMapper.toDto(testEntity);
    }

    /**
     * Delete the testEntity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestEntity : {}", id);
        testEntityRepository.delete(id);
    }
}
