package com.myprj.crawler.domain;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.annotation.EntityTransfer;
import com.myprj.crawler.model.RuleScriptModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class RuleScriptData extends AuditData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("code")
    @EntityTransfer("code")
    private String code;

    @DataTransfer("script")
    @EntityTransfer("script")
    private String script;

    @DataTransfer("file")
    @EntityTransfer("file")
    private String file;

    @DataTransfer("enabled")
    @EntityTransfer("enabled")
    private boolean enabled;

    public RuleScriptData() {
    }

    public static void toDatas(List<RuleScriptModel> sources, List<RuleScriptData> dests) {
        for (RuleScriptModel source : sources) {
            RuleScriptData dest = new RuleScriptData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toModels(List<RuleScriptData> sources, List<RuleScriptModel> dests) {
        for (RuleScriptData source : sources) {
            RuleScriptModel dest = new RuleScriptModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(RuleScriptModel source, RuleScriptData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModel(RuleScriptData source, RuleScriptModel dest) {
        EntityConverter.convert2Entity(source, dest);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
