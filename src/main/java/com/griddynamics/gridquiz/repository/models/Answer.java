package com.griddynamics.gridquiz.repository.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "correctly")
public class Answer {
    @Id
    private String id;

    private String text;

    @JsonIgnore
    private String textFieldAnswer;

    @JsonIgnore
    private boolean correctly;
}
