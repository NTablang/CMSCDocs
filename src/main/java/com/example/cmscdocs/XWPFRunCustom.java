package com.example.cmscdocs;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * This class is a utility based class that helps with the Controller class' XWPFRun customizations.
 * This class initially extends the XWPFRun class from Apache POI-OOXMl but due to certain modularization
 * technicalities, it was then instead used as a standalone class which primarily consists of static
 * methods.
 * @author Nathan Tablang
 */
public class XWPFRunCustom {
    /**
     * Sets this XWPFRun instantiation to specific settings such that it is primarily used for Headings:
     * It is bold.
     * It is sized 14.
     * It is customized with a blue colored-font.
     * It is customized with the Times New Roman font.
     * @param line - XWPFRun instance to be customized.
     */
    public static void setCustomHeadingSettings(XWPFRun line) {
        line.setBold(true);
        line.setFontSize(14);
        line.setColor("001959");
        line.setFontFamily("Times New Roman");
    }
    /**
     * Sets this XWPFRun instantiation to specific settings such that it is primarily used for SubHeadings:
     * It is Itacialized.
     * It is sized 14.
     * It is customized with a blue colored-font.
     * It is customized with the Times New Roman font.
     * @param line - XWPFRun instance to be customized.
     */
    public static void setSubHeadingsSettings(XWPFRun line) {
        line.setItalic(true);
        line.setFontSize(14);
        line.setColor("001959");
        line.setFontFamily("Times New Roman");
    }
    /**
     * Sets this XWPFRun instantiation to specific settings such that it is primarily used for prefaces:
     * It is bold.
     * It is sized 12.
     * It is customized with a blue colored-font.
     * It is customized with the Times New Roman font.
     * @param line - XWPFRun instance to be customized.
     */
    public static void setPrefaceSettings(XWPFRun line) {
        line.setBold(true);
        line.setFontSize(12);
        line.setColor("001959");
        line.setFontFamily("Times New Roman");
    }
    /**
     * Sets this XWPFRun instantiation to specific settings such that it is primarily used for Headings:
     * It is NOT bold.
     * It is sized 12.
     * It is customized with a blue colored-font.
     * It is customized with the Times New Roman font.
     * @param line - XWPFRun instance to be customized.
     */
    public static void setNormalPrefaceSettings(XWPFRun line) {
        line.setBold(false);
        line.setFontSize(12);
        line.setColor("001959");
        line.setFontFamily("Times New Roman");
    }
}
