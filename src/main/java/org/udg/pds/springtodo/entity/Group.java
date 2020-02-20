package org.udg.pds.springtodo.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


@Entity(name = "usergroup")
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    public Group() {
    }

    public Group(String groupname, String description) {
        this.groupname = groupname;
        this.description = description;
    }
    @NotNull
    private String groupname;

    @NotNull
    private String description;

    @Id
    // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> users = new ArrayList<>();

    @Column(name = "fk_user", insertable = false, updatable = false)
    private Long userId;
}
