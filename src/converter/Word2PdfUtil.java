package converter;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;

import java.io.File;

public class Word2PdfUtil {
    static final int wdFormatPDF = 17;// word转PDF 格式

    public static void word2pdf(String source, String target) {
        ComThread.InitSTA();
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            Dispatch.call(doc, "Close", false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (app != null) {
                app.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

    public static void main(String[] args) {
        word2pdf("D:\\word\\两小时学会Git玩转Github.doc","D:\\word\\两小时学会Git玩转Github.pdf");
    }
}
