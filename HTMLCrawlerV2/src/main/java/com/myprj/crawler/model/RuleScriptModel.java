package com.myprj.crawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author DienNM (DEE)
 */
@Entity
@Table(name = "rule_script")
public class RuleScriptModel extends AuditModel {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "code")
    private String code;
    
    @Column(name = "script")
    @Lob
    private String script;
    
    @Column(name = "file", length = 200)
    private String file;

    @Column(name = "enabled")
    private boolean enabled;

    public RuleScriptModel() {
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
