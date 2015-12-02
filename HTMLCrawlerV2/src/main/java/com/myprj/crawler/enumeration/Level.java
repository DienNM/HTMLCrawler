package com.myprj.crawler.enumeration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author DienNM (DEE)
 */

public enum Level {

    Level0("ROOT", 0, "Level1"), 
    Level1("LEVEL_1", 1, "Level2"), 
    Level2("LEVEL_2", 2, "Level3"), 
    Level3("LEVEL_3", 3, "Level4"), 
    Level4("LEVEL_4", 4, "Level5"), 
    Level5("LEVEL_5", 5, null);

    private String text;

    private int order;
    
    private String nextLevel;

    private Level(String text, int order, String nextLevel) {
        this.text = text;
        this.order = order;
        this.nextLevel = nextLevel;
    }

    public static Level fromText(String text) {
        if (StringUtils.isEmpty(text)) {
            return Level0;
        }
        for (Level level : Level.values()) {
            if (level.name().toLowerCase().equals(text.toLowerCase())) {
                return level;
            }
        }
        return Level0;
    }
    
    public static Level goNextLevel(Level level) {
        if(level.getNextLevel() == null) {
            return null;
        }
        return fromText(level.getNextLevel());
    }

    public static List<Level> sort() {
        List<Level> levels = new ArrayList<Level>();
        for (Level level : Level.values()) {
            levels.add(level);
        }

        Collections.sort(levels, new Comparator<Level>() {
            @Override
            public int compare(Level o1, Level o2) {
                if (o1.order >= o2.order) {
                    return 1;
                }
                return -1;
            }
        });
        return levels;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }
}
