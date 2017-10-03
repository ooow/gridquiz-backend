package com.griddynamics.gridquiz.api.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DashboardModel {
    String quizName;
    List<DashboardResultModel> results;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class DashboardResultModel {
        public String position;
        public String name;
        public String result;
        public String time;
    }
}
