package ch.sebooom.repository;

import ch.sebooom.domain.TestEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TestEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {

}
