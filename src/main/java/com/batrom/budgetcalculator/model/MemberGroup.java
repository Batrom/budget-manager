package com.batrom.budgetcalculator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "member_group")
public class MemberGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "member_member_group", joinColumns = @JoinColumn(name = "member_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private Set<Member> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberGroup)) return false;

        MemberGroup memberGroup = (MemberGroup) o;
        return Objects.equals(id, memberGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
