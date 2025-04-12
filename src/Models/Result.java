package src.Models;

public record Result(boolean state, String message) {
    public Result(boolean state, String message){
        this.state = state;
        this.message = message;
    }
}