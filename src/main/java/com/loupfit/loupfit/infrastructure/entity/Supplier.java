package com.loupfit.loupfit.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fantasyName", nullable = false, unique = true)
    private String fantasyName;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "linkProfile")
    private String linkProfile;

    @Column(name = "email")
    private String email;

    @Column(name = "site")
    private String site;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
