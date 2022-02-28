package prueba.micellaneus.exeption;

public class ExceptionHelper {

    public static void procesar(Exception e, String messageException) {
        if (e instanceof BusinessException) {
            throw (RuntimeException) e;
        }

        throw new BusinessException(
                messageException, e);
    }
}
