package com.griddynamics.gridquiz.repository.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> colors;
    private List<Question> questions;
}
