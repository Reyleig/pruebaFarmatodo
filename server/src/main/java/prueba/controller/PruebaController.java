package prueba.controller;

import prueba.micellaneus.dto.GenericDto;
import prueba.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prueba")
public class PruebaController {

    @Autowired
    private PruebaService pruebaService;

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET})
    @GetMapping("/getDataEpisode/{id}")
    public ResponseEntity<GenericDto> getDataEpisode(@PathVariable("id") int idEpisode) {
        return ResponseEntity.ok().body(GenericDto.sucess(pruebaService.getDataEpisode(idEpisode)));
    }
}
