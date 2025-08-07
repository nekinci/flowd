package com.taskengine.app.application.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClaimTaskAction {

    private UUID taskId;
    private String username;

}
