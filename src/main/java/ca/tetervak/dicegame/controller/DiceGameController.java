package ca.tetervak.dicegame.controller;

import ca.tetervak.dicegame.domain.DiceRollData;
import ca.tetervak.dicegame.service.DiceRollerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class DiceGameController {


    private final DiceRollerService diceRollerService;

    public DiceGameController(DiceRollerService diceRollerService) {
        this.diceRollerService = diceRollerService;
    }

    @GetMapping("/roll-dice")
    public ResponseEntity<DiceRollData> rollDice(
            @RequestParam(defaultValue = "3") int numberOfDice
    ) {

        if(numberOfDice < 1 || numberOfDice > 5){
            throw new IllegalArgumentException(
                    "Illegal Number of Dice = %d; must be between 1 and 5."
                            .formatted(numberOfDice)
            );
        }

        DiceRollData rollData = diceRollerService.getRollData(numberOfDice);

        log.debug("Rolling {} dice: {}", numberOfDice, rollData);

        return ResponseEntity.ok(rollData);
    }

}
