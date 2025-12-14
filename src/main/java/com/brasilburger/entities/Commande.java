package com.brasilburger.entities;

import com.brasilburger.entities.enums.EtatCommande;
import com.brasilburger.entities.enums.ModeConsommation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande extends BaseEntity {

    private Long clientId;
    private Long zoneId;

    private ModeConsommation modeConsommation;
    private EtatCommande etat = EtatCommande.EN_COURS;

    private Double total;
    private LocalDateTime dateCommande = LocalDateTime.now();

    private List<CommandeBurger> burgers = new ArrayList<>();
    private List<CommandeMenu> menus = new ArrayList<>();
    private List<CommandeComplement> complements = new ArrayList<>();




    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public ModeConsommation getModeConsommation() {
        return modeConsommation;
    }

    public void setModeConsommation(ModeConsommation modeConsommation) {
        this.modeConsommation = modeConsommation;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }



    public List<CommandeBurger> getBurgers() {
        return burgers;
    }

    public void setBurgers(List<CommandeBurger> burgers) {
        this.burgers = burgers;
    }

    public void addBurger(CommandeBurger cb) {
        this.burgers.add(cb);
    }



    public List<CommandeMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<CommandeMenu> menus) {
        this.menus = menus;
    }

    public void addMenu(CommandeMenu cm) {
        this.menus.add(cm);
    }




    public List<CommandeComplement> getComplements() {
        return complements;
    }

    public void setComplements(List<CommandeComplement> complements) {
        this.complements = complements;
    }

    public void addComplement(CommandeComplement cc) {
        this.complements.add(cc);
    }


  

    public boolean isLivraison() {
        return this.modeConsommation == ModeConsommation.LIVRAISON;
    }

    public boolean isSurPlace() {
        return this.modeConsommation == ModeConsommation.SUR_PLACE;
    }

    public boolean isAEmporter() {
        return this.modeConsommation == ModeConsommation.A_EMPORTER;
    }

}
