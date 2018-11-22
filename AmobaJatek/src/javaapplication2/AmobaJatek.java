/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *A játéktér és logika
 * @author Felhasználó
 */
public class AmobaJatek extends JFrame implements ActionListener{
        public int palyaszam=NyitoAblak.szam;
        public int gyozelemszam=NyitoAblak.nyerszam;
        public boolean player1=true;
        public Icon xIcon;
        public Icon oIcon;
        AmobaButton[][] cella=new AmobaButton[palyaszam][palyaszam];
        JLabel label=new JLabel();
        AmobaButton vissza=new AmobaButton("Visszavonás");
        AmobaButton felad=new AmobaButton("Feladás");
        AmobaButton visszaGomb=new AmobaButton();
        String[] ujJatek={"Igen","Nem"};
        int holtverseny=0;
        public AmobaJatek(){
            init();
            setVisible(true);
        }
        void init(){
            if(palyaszam<=10)setSize(600,600);
            if(palyaszam>=11 && palyaszam<=19)setSize(800,800);
            if(palyaszam>=20)setSize(1000,1000);
            
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel border=new JPanel();
            border.setLayout(new BorderLayout());
            JPanel gombok=new JPanel();
            gombok.setLayout(new GridLayout(1,3));
            
            label.setFont(new Font("Serif", Font.PLAIN, 40));
            label.setText("1. játékos köre");
            vissza.setState(3);
            felad.setState(4);
            gombok.add(label);
            setResizable(false);
            
            JPanel panel=new JPanel();
            panel.setLayout(new GridLayout(palyaszam,palyaszam));
            border.add(gombok, BorderLayout.NORTH);
            border.add(panel, BorderLayout.CENTER);
            setContentPane(border);
            gombok.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            vissza.addActionListener(this);
            felad.addActionListener(this);
            gombok.add(vissza);
            gombok.add(felad);
            
            for (int i = 0; i < palyaszam; i++) {
                for (int j = 0; j < palyaszam; j++) {
                    cella[i][j]=new AmobaButton();
                    panel.add(cella[i][j]);
                    cella[i][j].addActionListener(this);
                    cella[i][j].setKoordi(i);
                    cella[i][j].setKoordj(j);
                    cella[i][j].setState(0);
                }
            }
        }

/**
 * X és O képek tulajdonságainak beillesztése
 */        
        public void init_icons(){
            try{
            Image x= ImageIO.read(AmobaJatek.class.getResource("/x.png"));
            Image o= ImageIO.read(AmobaJatek.class.getResource("/o.png"));
            
            xIcon=new ImageIcon(x.getScaledInstance(cella[0][0].getWidth(), cella[0][0].getHeight(), Image.SCALE_SMOOTH));
            oIcon=new ImageIcon(o.getScaledInstance(cella[0][0].getWidth(), cella[0][0].getHeight(), Image.SCALE_SMOOTH));

            }
            catch(IOException e){
                System.out.println("Hiba!");
            }
            }

//    public static void main(String[] args) { }

/**
 * Visszavonásgomb, feladásgomb, játéktér gombjainak kezelése
 * @param ae 
 */    
    @Override
    public void actionPerformed(ActionEvent ae) {
        AmobaButton button=(AmobaButton)(ae.getSource());
 
//visszavonás        
        if(button.getText()==vissza.getText()){
            visszaGomb.setIcon(felad.getIcon());
            vissza.setIcon(felad.getIcon());
            visszaGomb.setState(0);
            vissza.setEnabled(false);
            if(player1){
                player1=false;
                label.setText("2. játékos köre");
            }
            else{
                player1=true;
                label.setText("1. játékos köre");
            }
        }
        
//játék feladása 
        if(button.getState()==4){
            if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Feladtad!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Feladtad!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
        }
        
//Első játékos kattintása        
        if(player1 && button.getState()==0){
            
            init_icons();
            button.setIcon(xIcon);
            label.setText("2. játékos köre");
            player1=false;
            visszaGomb=button;
            button.setState(1);  
            holtverseny++;
            wincheck(button);
            vissza.setEnabled(true);
        }

//Második játékos kattintása        
        else if(player1==false && button.getState()==0){
          init_icons();
          button.setIcon(oIcon);
          label.setText("1. játékos köre");
          player1=true;
          visszaGomb=button;
          button.setState(2); 
          holtverseny++;
          wincheck(button);
          vissza.setEnabled(true);
        }
    }

/**
 * Ellenőrzi minden lépés után, hogy van-e nyertes
 * @param button 
 */    
    public void wincheck(AmobaButton button){
        int kordi=button.getKoordi();
        int kordj=button.getKoordj();
        int gyozott=1;
        
        
        //lefele-felfele
        for (int i = 1; i < gyozelemszam; i++) {
            if(kordi+i<palyaszam) {
            if(cella[kordi+i][kordj].getState()==button.getState()) {
                    gyozott++;
                    System.out.println(gyozott);
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                    
                }
            } else break;
            }
        }
        
        for (int i = 1; i < gyozelemszam; i++) {
            if(kordi-i>=0) {
            if(cella[kordi-i][kordj].getState()==button.getState()) {
                    gyozott++;
                    System.out.println(gyozott);
                if(gyozott==gyozelemszam) 
                {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                    
                }
            }else break;
            }
        }
        
        //jobbra-balra
        gyozott=1;
        for (int j = 1; j < gyozelemszam; j++) {
            if(kordj+j<palyaszam) {
            if(cella[kordi][kordj+j].getState()==button.getState()) {
                    gyozott++;                   
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                   NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            } else break;
            }
        }
        
        for (int j = 1; j < gyozelemszam; j++) {
            if(kordj-j>=0) {
            if(cella[kordi][kordj-j].getState()==button.getState()) {
                    gyozott++;
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            }else break;
            }
        }
        //átlósan jobbra föl
        gyozott=1;
        int i;
        for (int j = 1; j < gyozelemszam; j++) {
            i=j;
            
            if(kordj+j<palyaszam && kordi-i>=0) {               
            if(cella[kordi-i][kordj+j].getState()==button.getState()) {
                gyozott++;                   
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            } else break;
            }
        }      
        
        //átlósan balra le
        for (int j = 1; j < gyozelemszam; j++) {
            i=j;
            if(kordi+i<palyaszam && kordj-j>=0) {
            if(cella[kordi+i][kordj-j].getState()==button.getState()) {
                    gyozott++;
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            }else break;
            }
        }
        //átlósan balra föl
        gyozott=1;
        for (int j = 1; j < gyozelemszam; j++) {
            i=j;
            if(kordj-j>=0 && kordi-i>=0) {
            if(cella[kordi-i][kordj-j].getState()==button.getState()) {
                    gyozott++;
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            } else break;
            }
        }
        //átlósan jobbra le
        
        for (int j = 1; j < gyozelemszam; j++) {
            i=j;
            if(kordj+j<palyaszam && kordi+i<palyaszam) {
            if(cella[kordi+i][kordj+j].getState()==button.getState()) {
                    gyozott++;                    
                if(gyozott==gyozelemszam) {
                    if(player1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
                }
            }else break;
            }
        }
        
//holtverseny megállapytása        
        if(holtverseny==palyaszam*palyaszam && gyozott<gyozelemszam) {
            int holt=JOptionPane.showOptionDialog(this, "Nincs győztes. Szeretnétek még játszani?", "Holtverseny!", WIDTH, WIDTH, null, ujJatek,"");
                if(holt==0){
                    NyitoAblak asd= new NyitoAblak();                  
                    asd.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
        }
    }
}
