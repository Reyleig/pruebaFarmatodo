package prueba.micellaneus.dto;

import lombok.Data;

@Data
class charactersDto {
    private String name;
    private String species;
    private String gender;
    private String image;
    private locationDto location;
}
