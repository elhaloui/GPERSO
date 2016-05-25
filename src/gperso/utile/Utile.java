/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gperso.utile;


import java.sql.Date;

/**
 *
 * @author F43538
 */
public class Utile {
    public static int getDiffYears(Date first, Date last) {
        System.out.println("first :" +first);
        System.out.println("last :" +last); 
 double diff = last.getTime() - first.getTime();
double d = 1000 * 60 * 60 * 24 * 365;
return (int) Math.round(diff / d);
    
}

    
}
