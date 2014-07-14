package com.kaajavi.trackmycar;


public final class Config {    
    public static boolean isPositionActive = false;
    public static boolean sendSmS = false;    
    public static String code = "BUSCAR";
    public static boolean isSmsActive=true;
    
    
    public static String print(){
    	return "Activado: "+isPositionActive + "\n"
    + "Activado SMS: " + isSmsActive+ "\n"
    + "Codigo: " + code;
    }
}