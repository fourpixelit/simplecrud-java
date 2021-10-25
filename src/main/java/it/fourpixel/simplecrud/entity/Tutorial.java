package it.fourpixel.simplecrud.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity @Table(name = "tutorial")
@Getter
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", length = 512)
    private String description;
    @Column(name = "published")
    private boolean published;

    protected Tutorial() {}

    public Tutorial(String title, String description) {
        this.title = title;
        this.description = description;
        published = false;
    }
}
