package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.Project;
import com.niam.kardan.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> saveProject(@RequestBody Project project) {
        return responseEntityUtil.ok(projectService.saveProject(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return responseEntityUtil.ok(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return responseEntityUtil.ok("Project deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findProject(@PathVariable Long id) {
        return responseEntityUtil.ok(projectService.getProject(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> findAllProjects() {
        return responseEntityUtil.ok(projectService.getAllProjects());
    }
}