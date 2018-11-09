package ch.sebooom.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TestEntity.
 */
@Entity
@Table(name = "test_entity")
public class TestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10)
    @Column(name = "test", nullable = false)
    private String test;

    @NotNull
    @Size(min = 10)
    @Column(name = "jhi_desc", nullable = false)
    private String desc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public TestEntity test(String test) {
        this.test = test;
        return this;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDesc() {
        return desc;
    }

    public TestEntity desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestEntity testEntity = (TestEntity) o;
        if (testEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestEntity{" +
            "id=" + getId() +
            ", test='" + getTest() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
