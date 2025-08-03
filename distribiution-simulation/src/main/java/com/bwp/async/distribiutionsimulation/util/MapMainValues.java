package com.bwp.async.distribiutionsimulation.util;

import com.bwp.async.distribiutionsimulation.threads.Generator;
import com.bwp.async.distribiutionsimulation.threads.Person;
import com.bwp.async.distribiutionsimulation.threads.Remover;
import com.bwp.async.distribiutionsimulation.threads.Switch;
import javafx.scene.paint.Color;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class MapMainValues {

    public static final SecureRandom S_RAND = new SecureRandom();

    public static final int MAP_HEIGHT = 31;        //Y
    public static final int MAP_WIDTH = 40;         //X
    public static final int MAP_MID_POINT_Y = 15;   //floor(Y/2)
    public static final int MAP_MID_POINT_X = 30;

    public static final Color LINES_COLOR = Color.RED;
    public static final Color MAIN_POINTS_COLOR = Color.rgb(200, 200, 200);
    public static final Color DEFAULT_COLOR = Color.CORNSILK;

    public static AtomicReference<Direction> MAP_SWITCH_DIRECTION;

    public static final DynamicSlotsController CLIENT_SLOTS = new DynamicSlotsController(10);

    public static final LinkedList<Person> CLIENTS_LIST = new LinkedList<>();

    public static final Generator GENERATOR = new Generator();
    public static final Remover REMOVER = new Remover();
    public static final Switch DIRECTION_SWITCH = new Switch();
}
