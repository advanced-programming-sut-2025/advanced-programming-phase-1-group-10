package Common.Models;

public record Result(boolean state, String message) {
    public Result(boolean state, String message){
        this.state = state;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
