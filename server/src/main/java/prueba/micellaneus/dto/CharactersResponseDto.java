package prueba.micellaneus.dto;

import lombok.Data;

import java.util.List;

@Data

public class CharactersResponseDto {
    private String name;
    private String species;
    private String gender;
    private String image;
    private LocationResponseDto location;
}
