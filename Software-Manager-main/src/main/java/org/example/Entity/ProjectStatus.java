package org.example.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "status")
@Entity
public class ProjectStatus extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
