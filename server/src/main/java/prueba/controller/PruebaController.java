package prueba.controller;

import prueba.micellaneus.dto.*;
import prueba.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @CrossOrigin(origins = "*", methods = {RequestMethod.POST})
    @PostMapping("/happyNumber")
    public Numbers happyNumber(@RequestBody NumberListDto numberListDto) {
        return this.pruebaService.happyNumber(numberListDto);
    }
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET})
    @GetMapping("/sumNaturalNumbers/{number}")
    public SumNumber sumNaturalNumbers(@PathVariable("number") int number) {
        return this.pruebaService.sumNaturalNumbers(number);
    }
}
