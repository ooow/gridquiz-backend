package com.griddynamics.gridquiz.repository.models;

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

    private List<Color> colors;

    private List<Question> questions;

    private Type type;

    public enum Type {
        TEST,
        QUIZ
    }
}
