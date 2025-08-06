package com.loupfit.loupfit.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_itens")
@Builder
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item", nullable = false)
    private String item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "supplier", nullable = false)
    private String supplier;

    @Column(name = "observation")
    private String observation;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

}
