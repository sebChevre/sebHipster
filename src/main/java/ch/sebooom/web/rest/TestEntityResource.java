package ch.sebooom.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.sebooom.service.TestEntityService;
import ch.sebooom.web.rest.errors.BadRequestAlertException;
import ch.sebooom.web.rest.util.HeaderUtil;
import ch.sebooom.service.dto.TestEntityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TestEntity.
 */
@RestController
@RequestMapping("/api")
public class TestEntityResource {

    private final Logger log = LoggerFactory.getLogger(TestEntityResource.class);

    private static final String ENTITY_NAME = "testEntity";

    private final TestEntityService testEntityService;

    public TestEntityResource(TestEntityService testEntityService) {
        this.testEntityService = testEntityService;
    }

    /**
     * POST  /test-entities : Create a new testEntity.
     *
     * @param testEntityDTO the testEntityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testEntityDTO, or with status 400 (Bad Request) if the testEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-entities")
    @Timed
    public ResponseEntity<TestEntityDTO> createTestEntity(@Valid @RequestBody TestEntityDTO testEntityDTO) throws URISyntaxException {
        log.debug("REST request to save TestEntity : {}", testEntityDTO);
        if (testEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new testEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestEntityDTO result = testEntityService.save(testEntityDTO);
        return ResponseEntity.created(new URI("/api/test-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-entities : Updates an existing testEntity.
     *
     * @param testEntityDTO the testEntityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testEntityDTO,
     * or with status 400 (Bad Request) if the testEntityDTO is not valid,
     * or with status 500 (Internal Server Error) if the testEntityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-entities")
    @Timed
    public ResponseEntity<TestEntityDTO> updateTestEntity(@Valid @RequestBody TestEntityDTO testEntityDTO) throws URISyntaxException {
        log.debug("REST request to update TestEntity : {}", testEntityDTO);
        if (testEntityDTO.getId() == null) {
            return createTestEntity(testEntityDTO);
        }
        TestEntityDTO result = testEntityService.save(testEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-entities : get all the testEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testEntities in body
     */
    @GetMapping("/test-entities")
    @Timed
    public List<TestEntityDTO> getAllTestEntities() {
        log.debug("REST request to get all TestEntities");
        return testEntityService.findAll();
        }

    /**
     * GET  /test-entities/:id : get the "id" testEntity.
     *
     * @param id the id of the testEntityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testEntityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/test-entities/{id}")
    @Timed
    public ResponseEntity<TestEntityDTO> getTestEntity(@PathVariable Long id) {
        log.debug("REST request to get TestEntity : {}", id);
        TestEntityDTO testEntityDTO = testEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(testEntityDTO));
    }

    /**
     * DELETE  /test-entities/:id : delete the "id" testEntity.
     *
     * @param id the id of the testEntityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestEntity(@PathVariable Long id) {
        log.debug("REST request to delete TestEntity : {}", id);
        testEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
