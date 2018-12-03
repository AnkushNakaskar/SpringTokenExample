package com.token.ankush.config;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "project")
@Validated
@Data
public class ProjectConfig {

    private String group;

    private String team;

    private String env;


    private String application;

    private Boolean enableTask;

}