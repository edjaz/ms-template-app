package fr.edjaz.document.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DocumentEntity.
 */
@Document(collection = "document_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documententity")
public class DocumentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("text")
    private String text;

    @NotNull
    @Field("resume")
    private String resume;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public DocumentEntity text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResume() {
        return resume;
    }

    public DocumentEntity resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
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
        DocumentEntity documentEntity = (DocumentEntity) o;
        if (documentEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentEntity{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", resume='" + getResume() + "'" +
            "}";
    }
}
