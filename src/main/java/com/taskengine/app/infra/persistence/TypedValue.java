package com.taskengine.app.infra.persistence;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypedValue {

    private String type;
    private Object value;
}
