package main.entity;

public class Wards {
    private Long id;
    private String name;
    private Integer maxCount;

    public Wards() {
    }

    public Wards(long id, String name, Integer maxCount) {
        this.id = id;
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
