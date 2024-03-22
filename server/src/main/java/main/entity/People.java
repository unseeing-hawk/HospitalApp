package main.entity;

import javax.persistence.*;

@Entity
@Table(name = "People")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PATHER_NAME")
    private String patherName;

    @ManyToOne
    @JoinColumn(name = "DIAGNOSIS_ID")
    private Diagnosis diagnose;

    @ManyToOne
    @JoinColumn(name = "WARD_ID")
    private Wards ward;

    public People() {
    }

    public People(String firstName, String lastName, String patherName, Diagnosis diagnose, Wards ward) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
        this.diagnose = diagnose;
        this.ward = ward;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatherName() {
        return patherName;
    }

    public void setPatherName(String patherName) {
        this.patherName = patherName;
    }

    public Diagnosis getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnosis diagnose) {
        this.diagnose = diagnose;
    }

    public Wards getWard() {
        return ward;
    }

    public void setWard(Wards ward) {
        this.ward = ward;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patherName='" + patherName + '\'' +
                ", diagnose=" + diagnose +
                ", ward=" + ward +
                '}';
    }
}
