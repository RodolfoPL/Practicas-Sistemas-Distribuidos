/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

/**
 *
 * @author Lenovo
 */
public class Reloj implements Runnable{
    private final int noReloj;
    private final Calendar calendario;
    Thread hilo;
    private int h, m, s;
    private long delay;
    private boolean pausa;
    JLabel text;
   // private javax.swing.JLabel lblhora;
    
    //construtor relor hora actual
    public Reloj(int noReloj){
        this.noReloj = noReloj;
        calendario = new GregorianCalendar();
        h = calendario.get(Calendar.HOUR_OF_DAY);
        m = calendario.get(Calendar.MINUTE);
        s = calendario.get(Calendar.SECOND);
        delay = 1000;
        pausa = false;
        hilo = new Thread (this);
        hilo.start();
    }
    
    public Reloj(int noReloj, JLabel text){
        this.noReloj = noReloj;
        this.text = text;
        calendario = new GregorianCalendar();
        h = calendario.get(Calendar.HOUR_OF_DAY);
        m = calendario.get(Calendar.MINUTE);
        s = calendario.get(Calendar.SECOND);
        delay = 1000;
        pausa = false;
        hilo = new Thread (this);
        hilo.start();
    }

    
    //constructor de reloj con tiempo especifico
    public Reloj(int noReloj, boolean rand){
        this.noReloj = noReloj;
        calendario = new GregorianCalendar();
        h =  (int)(Math.random() * 24);
        m =  (int)(Math.random() * 60);
        s =  (int)(Math.random() * 60);
        delay = 1000;
        pausa = false;
        hilo = new Thread(this);
        hilo.start();
    }
    
    public Reloj(int noReloj, boolean rand, JLabel text){
        this.noReloj = noReloj;
        calendario = new GregorianCalendar();
        h =  (int)(Math.random() * 24);
        m =  (int)(Math.random() * 60);
        s =  (int)(Math.random() * 60);
        delay = 1000;
        this.text = text;
        pausa = false;
        hilo = new Thread(this);
        hilo.start();
    }
    
    @Override
    public void run() 
    {
       
        while(true){
            try
            { 
                formatTime();
                try{
                    text.setText(this.toString());
                }catch(Exception e){
                    //System.out.print(e);
                    System.out.println("Reloj " + noReloj +" : "+ h+":"+m+":"+s);
                }
                
                Thread.sleep(delay);
                s = !pausa ? s+1: s;
                //s++;
            } 
            catch (Exception e) 
            { 
                System.out.println ("Error de hora"); 
            } 
        }
    } 
    
    private void formatTime(){
        if(s >= 60){
            s = 0;
            m++;
        }
        if(m >= 60){
            m = 0;
            h++;
        }
        if(h >= 24){
            h = 0;
        }
    }
    public void updateHour(int hora){
        //calendario.set(Calendar.HOUR_OF_DAY, hora);
        h = hora;
    }
    
    public void updateMinute(int minute){
        //Date date = new Date(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH), calendario.get(Calendar.HOUR_OF_DAY), m, calendario.get(Calendar.SECOND));
        //calendario.set(Calendar.MINUTE, minute);
        //calendario.setTime(date);
        m = minute;
    }
    
    public void updateSecond(int second){
        s = second;
    }
    
    public void updateDelay(int seconds){
        delay = seconds;
    }
    
    public void pause(){
        pausa = !pausa;
    }
    
    @Override
    public String toString(){
        return String.format("%02d", h) +":"+ String.format("%02d", m) +":"+ String.format("%02d", s);
    }
}
