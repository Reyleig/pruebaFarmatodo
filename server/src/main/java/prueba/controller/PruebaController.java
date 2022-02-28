package prueba.controller;

import prueba.micellaneus.dto.GenericDto;
import prueba.micellaneus.dto.ResponseDto;
import prueba.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prueba")
public class PruebaController {

    @Autowired
    private PruebaService pruebaService;

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET})
    @GetMapping("/getDataEpisode/{id}")
    public ResponseDto getDataEpisode(@PathVariable("id") int idEpisode) {
        return this.pruebaService.getDataEpisode(idEpisode);
    }
}
