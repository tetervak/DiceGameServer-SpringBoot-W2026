package ca.tetervak.dicegame.erros;

public record ApiError(
        int status,
        String error,
        String message
){}
