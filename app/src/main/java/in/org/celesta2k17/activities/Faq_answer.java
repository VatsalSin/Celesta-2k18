package in.org.celesta2k17.activities;

public class Faq_answer {
    private String Question;
    private String Answer;
    Faq_answer(String question,String answer){
        this.Question = question;
        this.Answer = answer;
    }
    public String getQuestion(){
        return Question;
    }
    public String getAnswer(){
        return Answer;
    }
}
