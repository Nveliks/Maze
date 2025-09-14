package backend.academy;

import backend.academy.services.GameService;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;


@UtilityClass
@Log4j2
public class Main {
    public static void main(String[] args) throws Exception {

        GameService gameService = new GameService();
        gameService.start();
    }
}
