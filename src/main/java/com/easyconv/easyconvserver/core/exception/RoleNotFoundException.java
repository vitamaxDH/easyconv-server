package com.easyconv.easyconvserver.core.exception;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RoleNotFoundException extends RuntimeException{

    private String message;
}
