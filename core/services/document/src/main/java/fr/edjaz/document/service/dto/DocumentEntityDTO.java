package fr.edjaz.document.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DocumentEntity entity.
 */
public class DocumentEntityDTO implements Serializable {

    private String id;

    @NotNull
    private String text;

    @NotNull
    private String resume;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentEntityDTO documentEntityDTO = (DocumentEntityDTO) o;
        if (documentEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentEntityDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", resume='" + getResume() + "'" +
            "}";
    }
}
