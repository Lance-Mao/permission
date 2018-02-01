package com.mlw.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull
    private Integer id;
}
