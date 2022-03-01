package prueba.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import prueba.micellaneus.dto.SumNumber;
import prueba.service.impl.PruebaServiceImpl;


@RunWith(MockitoJUnitRunner.class)
class PruebaServiceImplTests {

    @InjectMocks
    private PruebaServiceImpl pruebaService;

    @Test
    public void testSum() {
        pruebaService= new PruebaServiceImpl();
        SumNumber sumatoriaDto = pruebaService.sumNaturalNumbers(5);
        Assert.assertEquals(15, Long.parseLong(String.valueOf(sumatoriaDto.getResult())));
    }

    @Test
    public void testSum2() {
        pruebaService= new PruebaServiceImpl();
        SumNumber sumatoriaDto = pruebaService.sumNaturalNumbers(10);
        Assert.assertEquals(55, Long.parseLong(String.valueOf(sumatoriaDto.getResult())));
    }


}
