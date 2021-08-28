package securityexample1.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;


    private String role_name;

    @ManyToMany(mappedBy = "role")
    private Set<UserModel> userModelSet = new HashSet<>();
}
