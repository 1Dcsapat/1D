/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Amoba;

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
        public boolean jatekos1=true;
        public Icon xIkon;
        public Icon oIkon;
        AmobaGombok[][] cella=new AmobaGombok[palyaszam][palyaszam];
        JLabel cimke=new JLabel();
        AmobaGombok vissza=new AmobaGombok("Visszavonás");
        AmobaGombok felad=new AmobaGombok("Feladás");
        AmobaGombok visszaGomb=new AmobaGombok();
        String[] ujJatek={"Igen","Nem"};
        int holtverseny=0;
        public AmobaJatek(){
            kezdes();
            setVisible(true);
        }
        void kezdes(){
            if(palyaszam<=10)setSize(600,600);
            if(palyaszam>=11 && palyaszam<=19)setSize(800,800);
            if(palyaszam>=20)setSize(1000,1000);
            
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel border=new JPanel();
            border.setLayout(new BorderLayout());
            JPanel gombok=new JPanel();
            gombok.setLayout(new GridLayout(1,3));
            
            cimke.setFont(new Font("Serif", Font.PLAIN, 40));
            cimke.setText("1. játékos köre");
            vissza.setState(3);
            felad.setState(4);
            gombok.add(cimke);
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
                    cella[i][j]=new AmobaGombok();
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
        public void kezdes_ikonok(){
            try{
            Image x= ImageIO.read(AmobaJatek.class.getResource("/x.png"));
            Image o= ImageIO.read(AmobaJatek.class.getResource("/o.png"));
            
            xIkon=new ImageIcon(x.getScaledInstance(cella[0][0].getWidth(), cella[0][0].getHeight(), Image.SCALE_SMOOTH));
            oIkon=new ImageIcon(o.getScaledInstance(cella[0][0].getWidth(), cella[0][0].getHeight(), Image.SCALE_SMOOTH));

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
        AmobaGombok button=(AmobaGombok)(ae.getSource());
 
//visszavonás        
        if(button.getText()==vissza.getText()){
            visszaGomb.setIcon(felad.getIcon());
            vissza.setIcon(felad.getIcon());
            visszaGomb.setState(0);
            vissza.setEnabled(false);
            if(jatekos1){
                jatekos1=false;
                cimke.setText("2. játékos köre");
            }
            else{
                jatekos1=true;
                cimke.setText("1. játékos köre");
            }
        }
        
//játék feladása 
        if(button.getState()==4){
            if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Feladtad!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Feladtad!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
        }
        
//Első játékos kattintása        
        if(jatekos1 && button.getState()==0){
            
            kezdes_ikonok();
            button.setIcon(xIkon);
            cimke.setText("2. játékos köre");
            jatekos1=false;
            visszaGomb=button;
            button.setState(1);  
            holtverseny++;
            gyozelemellenorzes(button);
            vissza.setEnabled(true);
        }

//Második játékos kattintása        
        else if(jatekos1==false && button.getState()==0){
          kezdes_ikonok();
          button.setIcon(oIkon);
          cimke.setText("1. játékos köre");
          jatekos1=true;
          visszaGomb=button;
          button.setState(2); 
          holtverseny++;
          gyozelemellenorzes(button);
          vissza.setEnabled(true);
        }
    }

/**
 * Ellenőrzi minden lépés után, hogy van-e nyertes
 * @param button 
 */    
    public void gyozelemellenorzes(AmobaGombok button){
        int kordi=button.getKoordi();
        int kordj=button.getKoordj();
        int gyozott=1;
        
        
        //lefele-felfele
        for (int i = 1; i < gyozelemszam; i++) {
            if(kordi+i<palyaszam) {
            if(cella[kordi+i][kordj].getState()==button.getState()) {
                    gyozott++;
                if(gyozott==gyozelemszam) {
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                if(gyozott==gyozelemszam) 
                {
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                   NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
                    if(jatekos1){
                int elso=JOptionPane.showOptionDialog(this, "2. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(elso==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
            }
            else{
                int masodik=JOptionPane.showOptionDialog(this, "1. játékos nyert. Szeretnétek még játszani?", "Gratulálok!", WIDTH, WIDTH, null, ujJatek,"");
                if(masodik==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
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
        
//holtverseny megállapítása        
        if(holtverseny==palyaszam*palyaszam && gyozott<gyozelemszam) {
            int holt=JOptionPane.showOptionDialog(this, "Nincs győztes. Szeretnétek még játszani?", "Holtverseny!", WIDTH, WIDTH, null, ujJatek,"");
                if(holt==0){
                    NyitoAblak nyito= new NyitoAblak();                  
                    nyito.setVisible(true);
                    this.dispose();
                }
                else{
                    System.exit(0);
                }
        }
    }
}
