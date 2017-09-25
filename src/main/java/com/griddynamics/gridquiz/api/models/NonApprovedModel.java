package com.griddynamics.gridquiz.api.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NonApprovedModel {
    private Long id;
    private String name;
    private int points;
}
