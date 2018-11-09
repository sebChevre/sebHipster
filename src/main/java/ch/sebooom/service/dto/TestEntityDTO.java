package ch.sebooom.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TestEntity entity.
 */
public class TestEntityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10)
    private String test;

    @NotNull
    @Size(min = 10)
    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestEntityDTO testEntityDTO = (TestEntityDTO) o;
        if(testEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestEntityDTO{" +
            "id=" + getId() +
            ", test='" + getTest() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
