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
public class Question {
    @Id
    private String id;

    private String text;

    private String title;

    private Type type;

    public enum Type {
        TEXT,
        CODE,
        INPUT
    }

    private List<Answer> answers;
}
