package in.org.celesta2k17.activities;

public class Faq_answer {
    private String Question;
    private String Answer;
    private String Rating;
    Faq_answer(String question,String answer,String rating){
        this.Question = question;
        this.Answer = answer;
        this.Rating = rating;
    }
    public String getQuestion(){
        return Question;
    }
    public String getAnswer(){
        return Answer;
    }
    public String getRating(){return Rating;}
}
