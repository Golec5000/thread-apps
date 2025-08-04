package com.bwp.async.distribiutionsimulation.util;

import com.bwp.async.distribiutionsimulation.threads.Generator;
import com.bwp.async.distribiutionsimulation.threads.Person;
import com.bwp.async.distribiutionsimulation.threads.Remover;
import com.bwp.async.distribiutionsimulation.threads.Switch;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MapMainValues {

    public static final SecureRandom S_RAND = new SecureRandom();

    public static final int MAP_HEIGHT = 31;        //Y
    public static final int MAP_WIDTH = 40;         //X
    public static final int MAP_MID_POINT_Y = 15;   //floor(Y/2)
    public static final int MAP_MID_POINT_X = 30;

    public static final Color LINES_COLOR = Color.RED;
    public static final Color MAIN_POINTS_COLOR = Color.rgb(200, 200, 200);
    public static final Color DEFAULT_COLOR = Color.BLACK;

    public static AtomicReference<Direction> MAP_SWITCH_DIRECTION;

    public static final DynamicSlotsController CLIENT_SLOTS = new DynamicSlotsController(10);

    public static final LinkedList<Person> CLIENTS_LIST = new LinkedList<>();

    public static final Generator GENERATOR = new Generator();
    public static final Remover REMOVER = new Remover();
    public static final Switch DIRECTION_SWITCH = new Switch();

    public static final List<Color> colors;

    static {
        try {
            colors = allColors();
            colors.removeAll(List.of(Color.RED, Color.BLACK));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private static List<Color> allColors() throws ClassNotFoundException, IllegalAccessException {
        List<Color> colors = new ArrayList<>();
        Class<?> clazz = Class.forName("javafx.scene.paint.Color");
        Field[] field = clazz.getFields();
        for (Field f : field) {
            Object obj = f.get(null);
            if (obj instanceof Color) {
                colors.add((Color) obj);
            }

        }
        return colors;
    }

    public static Color pickRngColor() {
        return colors.get(S_RAND.nextInt(50, 2000) % colors.size());
    }
}
