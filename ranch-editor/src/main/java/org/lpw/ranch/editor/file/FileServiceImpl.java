package org.lpw.ranch.editor.file;

import com.alibaba.fastjson.JSONObject;
import org.lpw.ranch.editor.EditorModel;
import org.lpw.ranch.editor.EditorService;
import org.lpw.ranch.editor.element.ElementModel;
import org.lpw.ranch.editor.element.ElementService;
import org.lpw.ranch.editor.screenshot.ScreenshotService;
import org.lpw.ranch.push.helper.PushHelper;
import org.lpw.ranch.temporary.Temporary;
import org.lpw.ranch.user.helper.UserHelper;
import org.lpw.tephra.ctrl.upload.UploadReader;
import org.lpw.tephra.office.pptx.PptxReader;
import org.lpw.tephra.pdf.PdfReader;
import org.lpw.tephra.scheduler.DateJob;
import org.lpw.tephra.util.Context;
import org.lpw.tephra.util.DateTime;
import org.lpw.tephra.util.Io;
import org.lpw.tephra.util.Validator;
import org.lpw.tephra.wormhole.WormholeHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lpw
 */
@Service(FileModel.NAME + ".service")
public class FileServiceImpl implements FileService, DateJob, org.lpw.tephra.pdf.MediaWriter, org.lpw.tephra.office.MediaWriter {
    @Inject
    private Validator validator;
    @Inject
    private Context context;
    @Inject
    private Io io;
    @Inject
    private DateTime dateTime;
    @Inject
    private WormholeHelper wormholeHelper;
    @Inject
    private PdfReader pdfReader;
    @Inject
    private PptxReader pptxReader;
    @Inject
    private Temporary temporary;
    @Inject
    private UserHelper userHelper;
    @Inject
    private PushHelper pushHelper;
    @Inject
    private EditorService editorService;
    @Inject
    private ElementService elementService;
    @Inject
    private ScreenshotService screenshotService;
    @Inject
    private FileDao fileDao;

    @Override
    public void save(String editor, String type, File file) {
        save(editor, type, wormholeHelper.file(null, null, null, file), file.length());
    }

    @Override
    public void save(String editor, String type, String uri, long size) {
        FileModel file = fileDao.find(editor, type);
        if (file == null) {
            file = new FileModel();
            file.setEditor(editor);
            file.setType(type);
        }
        file.setUri(uri);
        file.setSize(size);
        fileDao.save(file);
    }

    @Override
    public JSONObject upload(UploadReader uploadReader) throws IOException {
        File file = new File(context.getAbsolutePath(temporary.save(uploadReader.getInputStream(),
                uploadReader.getFileName().substring(uploadReader.getFileName().lastIndexOf('.')).toLowerCase())));
        String type = uploadReader.getParameter("type");
        List<String> list = type.equals("pdf") ? pdfReader.pngs(new FileInputStream(file), this, false) :
                pptxReader.pngs(new FileInputStream(file), this, false);
        JSONObject object = new JSONObject();
        if (validator.isEmpty(list)) {
            io.delete(file);
            object.put("success", false);

            return object;
        }

        FileModel model = fileDao.findById(uploadReader.getParameter("id"));
        if (model == null)
            model = fileDao.find(uploadReader.getParameter("editor"), type);
        if (model == null) {
            EditorModel editor = new EditorModel();
            editor.setType("");
            editor.setTemplate(3);
            editor.setImage(list.get(0));
            editor.setScreenshot(editor.getImage());

            model = new FileModel();
            model.setEditor(editorService.save(editor).getString("id"));
            model.setType(type);
        }
        model.setUri(wormholeHelper.file(null, null, null, file));
        model.setSize(file.length());
        model.setTime(dateTime.now());
        fileDao.save(model);
        for (int i = 0, size = list.size(); i < size; i++) {
            ElementModel element = new ElementModel();
            element.setEditor(model.getEditor());
            element.setParent(model.getEditor());
            element.setSort(i);
            screenshotService.create(model.getEditor(), i, elementService.save(element).getString("id"), list.get(i));
        }
        io.delete(file);

        object.put("success", true);
        object.put("path", model.getUri());
        object.put("name", uploadReader.getName());
        object.put("fileName", uploadReader.getFileName());
        object.put("fileSize", model.getSize());

        return object;
    }

    @Override
    public String write(org.lpw.tephra.pdf.MediaType mediaType, String fileName, InputStream inputStream) {
        return null;
    }

    @Override
    public String write(org.lpw.tephra.office.MediaType mediaType, String fileName, InputStream inputStream) {
        return null;
    }

    @Override
    public String download(String editor, String type, String email) {
        FileModel file = fileDao.find(editor, type);
        if (file == null)
            return null;

        String uri = wormholeHelper.temporary(file.getUri());
        if (validator.isEmpty(uri))
            return null;

        file.setDownload(file.getDownload() + 1);
        fileDao.save(file);

        String url = wormholeHelper.getUrl(uri, false);
        if (validator.isEmail(email)) {
            String user = userHelper.id();
            if (validator.isEmpty(user))
                user = UserHelper.SYSTEM_USER_ID;
            JSONObject args = new JSONObject();
            args.put("url", url);
            pushHelper.send(FileModel.NAME + ".download", user, email, args);
        }

        return url;
    }

    @Override
    public void executeDateJob() {
        editorService.download(fileDao.count());
    }
}
