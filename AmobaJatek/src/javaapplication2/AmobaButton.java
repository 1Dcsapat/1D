/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 * Amőba játékhoz használt gomb, a sima gomb tulajdonságait hordozva
 * 3 plusz attribútummal: state, koordi és koordj
 * @author Felhasználó
 */
public class AmobaButton extends JButton{
    private int state;
    private int koordi;
    private int koordj; 

    public int getKoordi() {
        return koordi;
    }

    public void setKoordi(int koordi) {
        this.koordi = koordi;
    }

    public int getKoordj() {
        return koordj;
    }

    public void setKoordj(int koordj) {
        this.koordj = koordj;
    }
    
    public AmobaButton() {
    }

    public AmobaButton(Icon icon) {
        super(icon);
    }

    public AmobaButton(String string) {
        super(string);
    }

    public AmobaButton(int state) {
        this.state = state;
    }

    public AmobaButton(Action action) {
        super(action);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public ItemListener getItemListener() {
        return itemListener;
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ChangeEvent getChangeEvent() {
        return changeEvent;
    }

    public void setChangeEvent(ChangeEvent changeEvent) {
        this.changeEvent = changeEvent;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public AmobaButton(String string, Icon icon) {
        super(string, icon);
    }
    
    
}
