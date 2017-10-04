package kinoview.webservlet.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexfomin on 13.07.17.
 */
public class ModelAndView {
    private View view;
    private Map<String, Object> parameters = new HashMap<>();
    private int status;

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(int status) {
        view = View.ERROR;
        this.status = status;
    }

    public void addParam(String name, Object value) {
        parameters.put(name, value);
    }

    public void addAllParams(Map<String, Object> params){
        parameters.putAll(params);
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
