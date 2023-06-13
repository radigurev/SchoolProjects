package org.example.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    private String name;
    private long hours;
    private Employee leader;
    private Collection<Employee> team;
    private ProjectType type;
    private LocalDateTime dueDate;

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    @ManyToOne
    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public Collection<Employee> getTeam() {
        return team;
    }

    public void setTeam(Collection<Employee> team) {
        this.team = team;
    }

    @ManyToOne
    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
