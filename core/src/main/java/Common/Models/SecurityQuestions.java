package Common.Models;

public enum SecurityQuestions {

    FAVORITE_COLOR(1, "What is your favorite color?"),
    CHILDHOOD_FRIEND(2, "Who was your best friend in childhood?"),
    FIRST_PET(3, "What was the name of your first pet?"),
    FAVORITE_BOOK(4, "What was the name of your favorite book?"),
    FIRST_SCHOOL(5, "What was the name of your first school?"),
    BIRTH_CITY(6, "In which city were you born?"),
    FAVORITE_FOOD(7, "What is your favorite food?"),
    FAVORITE_MOVIE(8, "What is your favorite movie?"),
    DREAM_JOB(9, "What was your childhood dream job?"),
    FAVORITE_SPORT(10, "What is your favorite sport?")
    ;

    public int number;
    public String question;

    SecurityQuestions(int number, String question){
        this.number = number;
        this.question = question;
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public static String getQuestionByNumber(int number){
        for(SecurityQuestions questions : SecurityQuestions.values()){
            if(questions.getNumber() == number){
                return questions.getQuestion();
            }
        }
        return null;
    }
}
