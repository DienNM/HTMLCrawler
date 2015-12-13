package com.myprj.crawler.web.dto;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.domain.RuleScriptData;
import com.myprj.crawler.model.RuleScriptModel;
import com.myprj.crawler.util.converter.DomainConverter;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class RuleScriptDTO extends AuditDTO {

    private static final long serialVersionUID = 1L;

    @DataTransfer("code")
    private String code;

    @DataTransfer("script")
    private String script;

    @DataTransfer("file")
    private String file;

    @DataTransfer("enabled")
    private boolean enabled;

    public RuleScriptDTO() {
    }

    public static void toDTOs(List<RuleScriptData> sources, List<RuleScriptDTO> dests) {
        for (RuleScriptData source : sources) {
            RuleScriptDTO dest = new RuleScriptDTO();
            toDTO(source, dest);
            dests.add(dest);
        }
    }

    public static void toDTO(RuleScriptData source, RuleScriptDTO dest) {
        DomainConverter.convert(source, dest);
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
