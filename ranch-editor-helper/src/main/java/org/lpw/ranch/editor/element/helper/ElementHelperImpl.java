package org.lpw.ranch.editor.element.helper;

import com.alibaba.fastjson.JSONObject;
import org.lpw.ranch.util.Carousel;
import org.lpw.tephra.util.Numeric;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Controller("ranch.editor.element.helper")
public class ElementHelperImpl implements ElementHelper {
    @Inject
    private Validator validator;
    @Inject
    private Numeric numeric;
    @Inject
    private Carousel carousel;
    @Value("${ranch.editor.element.key:ranch.editor.element}")
    private String key;
    private String saveKey;

    @Override
    public JSONObject save(String id, String editor, String parent, int sort, Map<String, String> map) {
        return save(null, id, editor, parent, sort, map);
    }

    @Override
    public JSONObject save(Map<String, String> header, String id, String editor, String parent, int sort, Map<String, String> map) {
        if (saveKey == null)
            saveKey = key + ".save";

        if (map == null)
            map = new HashMap<>();
        if (!validator.isEmpty(id))
            map.put("id", id);
        map.put("editor", editor);
        if (!validator.isEmpty(parent))
            map.put("parent", parent);
        map.put("sort", numeric.toString(sort, "0"));

        return carousel.service(saveKey, header, map, false, JSONObject.class);
    }
}
