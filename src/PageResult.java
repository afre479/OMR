import java.util.ArrayList;

public class PageResult {
private String studentId;
ArrayList<String> answers=new ArrayList<>();

    public void setStudentId(String id) {
        studentId=id;
    }

    public void addAnswers(ArrayList<String>data) {
        answers=data;
    }

    public ArrayList<String> getAnswers(){
    return answers;
}

    public String getStudentId(){
        return studentId;
}
}
