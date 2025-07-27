package com.taskengine.app.parser.converter;

import com.taskengine.app.TUserTask;
import com.taskengine.app.core.Util;
import com.taskengine.app.core.data.om.UserTaskNode;

import java.util.Map;

public class UserTaskNodeConverter implements Converter<TUserTask, UserTaskNode>{

    @Override
    public Class<TUserTask> getSourceType() {
        return TUserTask.class;
    }

    @Override
    public UserTaskNode convert(Context context, TUserTask source) {
        Map<String, String> attributes = Util.convertMap(source.getOtherAttributes());
        String assignee = attributes.get("assignee");
        String group = attributes.get("group");
        return new UserTaskNode(
                assignee,
                group,
                source.getId(),
                context.getCurrentProcessNode(),
                attributes
        );
    }
}
