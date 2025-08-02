package com.bwp.async.distribiutionsimulation.util;

import javafx.scene.paint.Color;

import java.security.SecureRandom;
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
}
