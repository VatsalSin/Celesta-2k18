package in.org.celesta2k17.data;

/**
 * Created by mayank on 26/11/17.
 */

public class lAndwData {
    public String headers;
    public String date;
    public String time;
    public String topics;
    public String venue;
    public String intro;
    public String intentClass;
    public int img;
    public String description;


    public lAndwData(String header,String date,String time,String venue,String intro,String description,String topics,String intentClass,int img) {
        this.headers=header;
        this.intro=intro;
        this.topics=topics;
        this.intentClass = intentClass;
        this.img = img;
        this.description=description;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }

}
