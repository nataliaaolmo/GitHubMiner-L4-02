package aiss.proyecto.githubminer.controller;

import aiss.proyecto.githubminer.model.Project;
import aiss.proyecto.githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
public class ToGitMiner {
    @Autowired //importante para que se reinicializa y así se carga todo
    ProjectService projectService;

    @Autowired
    RestTemplate restTemplate; //hacer las lalmadas de getForObject y eso
    //para hacer el GET
    //POST apipath/{id}[?sinceCommits=5&sinceIssues=30&maxPages=2]
    public Project getProject(@PathVariable String id,
                              @RequestParam(required = false) Integer sinceCommits,
                              @RequestParam(required = false) Integer sinceIssues,
                              @RequestParam(required = false) Integer maxPages
    ) {
        Project project = null;

        // Chequer que los parámetros sean no nulos
        // ¿Dónde se meten después?
        if(sinceCommits !=null){
        }

        if(sinceIssues!=null){
        }

        if(maxPages!=null){
        }

        // A gitminer hay que pasarlo con las clases de exportmodel, no de model.
        // ProjectService trabaja con las de model
        project = projectService.getProjectService(id, commits, listIssues, listComments);

        return null;
    }


    //llamaba a findProject del service y le metia los parametros

    //Para hacer el POST
    //2º creamos otro metodo para mandar el project a GitMiner
    // hacía un create project con getPoject pasandole los parametro pra mandarlo a GitMiner
    public ToGitMiner(ProjectService projectService) {

    }

}
