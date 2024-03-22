package main.entity;

import javax.persistence.*;

@Entity
@Table(name = "Wards")
public class Wards {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAX_COUNT")
    private Integer maxCount;

    public Wards() {
    }

    public Wards(String name, Integer maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public String toString() {
        return "Wards{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxCount=" + maxCount +
                '}';
    }
}
