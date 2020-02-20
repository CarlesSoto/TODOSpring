package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.service.TaskService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path="/groups")
@RestController
public class GroupController extends BaseController {

    @Autowired
    GroupService GroupService;

    @GetMapping(path="/{id}")
    public Group getGroup(HttpSession session,
                         @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);

        return GroupService.getGroup(userId, id);
    }

    @GetMapping
    @JsonView(Views.Private.class)
    public Collection<Group> listAllGroups(HttpSession session,
                                         @RequestParam(value = "from", required = false) Date from) {
        Long userId = getLoggedUser(session);

        return GroupService.getGroups(userId);
    }

    @PostMapping(consumes = "application/json")
    public IdObject addGroup(HttpSession session, @Valid @RequestBody R_Group group) {

        Long userId = getLoggedUser(session);

        return GroupService.addGroup(group.description group.groupname);
    }

    @DeleteMapping(path="/{id}")
    public String deleteTask(HttpSession session,
                             @PathVariable("id") Long taskId) {
        getLoggedUser(session);
        taskService.crud().deleteById(taskId);
        return BaseController.OK_MESSAGE;
    }

    @PostMapping(path="/{id}/tags")
    public String addTags(@RequestBody Collection<Long> tags, HttpSession session,
                          @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        taskService.addTagsToTask(userId, taskId, tags);
        return BaseController.OK_MESSAGE;
    }

    @GetMapping(path="/{id}/tags")
    public Collection<Tag> getTaskTags(HttpSession session,
                                       @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        return taskService.getTaskTags(userId, taskId);
    }

    static class R_Group {

        @NotNull
        public String description;
        @NotNull
        public String groupname;
    }

}
