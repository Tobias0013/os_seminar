import java.util.ArrayList;
import java.util.List;

public class ErrorLog {
    List<String> errors = new ArrayList<>();

    public ErrorLog() {
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getError(int index){
        return errors.get(index);
    }

    public void addError(String err){
        this.errors.add(err);
    }

    public int errorSize(){
        return this.errors.size();
    }
}
