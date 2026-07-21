package ca.tetervak.dicegame.controller;

import ca.tetervak.dicegame.domain.DiceRollData;
import ca.tetervak.dicegame.service.DiceRollerService;
import ca.tetervak.dicegame.service.impl.DiceRollDataImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/dice-game", produces = "application/json")
@Tag(name = "Dice Game API", description = "Randomly generated dice roll data.")
public class DiceGameController {


    private final DiceRollerService diceRollerService;

    public DiceGameController(DiceRollerService diceRollerService) {
        this.diceRollerService = diceRollerService;
    }

    @GetMapping(value = "/roll-dice", produces = "application/json")
    @Operation(summary = "Rolls a specified number of dice",
            description = "Generates a random roll of the specified number of dice, with each die having 6 sides.")
    @Parameters(
            @Parameter(name = "numberOfDice", description = "Number of dice to roll", required = false, example = "2")
    )
    @ApiResponses(
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = DiceRollDataImpl.class,
                                    description = "Dice roll data",
                                    example = """
                                        {
                                            "values": [2, 3, 6],
                                            "total": 11,
                                            "numberOfDice": 3
                                        }
                                    """
                            )

                    )))
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
